package de.qaware.smartlabmeeting.service.repository.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.common.collect.BiMap;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.Meeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.exception.EntityConflictException;
import de.qaware.smartlabcore.exception.EntityCreationException;
import de.qaware.smartlabcore.exception.InvalidSyntaxException;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabcore.result.DeletionResult;
import de.qaware.smartlabcore.result.ExtensionResult;
import de.qaware.smartlabcore.result.ShiftResult;
import de.qaware.smartlabcore.result.ShorteningResult;
import de.qaware.smartlabmeeting.service.repository.generic.AbstractMeetingManagementRepository;
import de.qaware.smartlabmeeting.service.repository.parser.IMeetingParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.qaware.smartlabcore.miscellaneous.TimeUtils.isNowInProgress;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Repository
@ConditionalOnProperty(
        prefix = Property.Prefix.MEETING_MANAGEMENT_REPOSITORY,
        name = Property.Name.MEETING_MANAGEMENT_REPOSITORY,
        havingValue = Property.Value.MeetingManagementRepository.GOOGLE_CALENDAR)
@Slf4j
public class GoogleCalendarAdapter extends AbstractMeetingManagementRepository {

    private final Calendar service;
    private final BiMap<RoomId, String> calendarIdsByRoomId;
    private final IMeetingParser meetingParser;

    public GoogleCalendarAdapter(
            Path googleCalendarCredentialFile,
            // TODO: String literal
            @Qualifier("googleCalendarScopes") Collection<String> googleCalendarScopes,
            String googleCalendarApplicationName,
            HttpTransport googleCalendarHttpTransport,
            JsonFactory googleCalendarJsonFactory,
            @Qualifier("googleCalendarRoomMapping") BiMap<RoomId, String> googleCalendarRoomMapping,
            IMeetingParser meetingParser,
            Set<IMeeting> initialMeetings) throws IOException {
        super(initialMeetings);
        GoogleCredential credentials = GoogleCredential.fromStream(
                Files.newInputStream(googleCalendarCredentialFile),
                googleCalendarHttpTransport,
                googleCalendarJsonFactory).createScoped(googleCalendarScopes);
        this.service = new Calendar.Builder(
                googleCalendarHttpTransport,
                googleCalendarJsonFactory,
                credentials)
                .setApplicationName(googleCalendarApplicationName)
                .build();
        this.calendarIdsByRoomId = googleCalendarRoomMapping;
        this.meetingParser = meetingParser;
    }

    private String resolveCalendarId(RoomId roomId) throws IllegalArgumentException {
        String calendarId = this.calendarIdsByRoomId.get(roomId);
        if(nonNull(calendarId)) return calendarId;
        throw new IllegalArgumentException(format("The room \"%s\" has no mapped calendar", roomId));
    }

    private RoomId resolveRoomId(String calendarId) throws IllegalArgumentException {
        RoomId roomId = this.calendarIdsByRoomId.inverse().get(calendarId);
        if(nonNull(roomId)) return roomId;
        throw new IllegalArgumentException(format("The calendar \"%s\" has no mapped room", calendarId));
    }

    private boolean hasMappedCalendar(RoomId roomId) {
        try {
            resolveCalendarId(roomId);
            return true;
        }
        catch(IllegalArgumentException e) {
            return false;
        }
    }

    private boolean hasMappedRoom(String calendarId) {
        try {
            resolveRoomId(calendarId);
            return true;
        }
        catch(IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public Set<IMeeting> findAll() {
        return eventsToMeetings(findAllGoogleCalEvents());
    }

    private Set<Event> findAllGoogleCalEvents() {
        Set<Event> foundEvents = new HashSet<>();
        List<CalendarListEntry> calendars = findAllCalendars();
        for(CalendarListEntry calendar : calendars) {
            String calendarId = calendar.getId();
            if(hasMappedRoom(calendarId)) {
                foundEvents.addAll(findAllGoogleCalEvents(calendarId));
            }
            else {
                log.warn("Ignoring events from calendar \"{}\" since it has no mapped room ID", calendarId);
            }
        }
        return foundEvents;
    }

    @Override
    public Set<IMeeting> findAll(RoomId roomId) {
        return eventsToMeetings(findAllGoogleCalEvents(roomId));
    }

    private Set<Event> findAllGoogleCalEvents(RoomId roomId) {
        try {
            return findAllGoogleCalEvents(resolveCalendarId(roomId));
        }
        catch(IllegalArgumentException e) {
            log.warn("Cannot find events in room \"{}\" since it has no mapped calendar ID", roomId, e);
            return new HashSet<>();
        }
    }

    @Override
    public Set<IMeeting> findAll(WorkgroupId workgroupId) {
        return findAll().stream()
                .filter(meeting -> meeting.getWorkgroupId().equals(workgroupId))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<IMeeting> findAllCurrent() {
        return eventsToMeetings(findAllCurrentGoogleCalEvents());
    }

    private Set<Event> findAllCurrentGoogleCalEvents() {
        Set<Event> events = findAllGoogleCalEvents();
        return events.stream()
                .filter(this::isEventInProgress)
                .collect(Collectors.toSet());
    }

    private Set<IMeeting> findAll(String calendarId) {
        return eventsToMeetings(findAllGoogleCalEvents(calendarId));
    }

    private Set<Event> findAllGoogleCalEvents(String calendarId) {
        return findAllGoogleCalEvents(calendarId, null, null);
    }

    private Set<IMeeting> findAll(String calendarId, @Nullable Instant from, @Nullable Instant until) {
        return eventsToMeetings(findAllGoogleCalEvents(calendarId, from, until));
    }

    private Set<Event> findAllGoogleCalEvents(String calendarId, @Nullable Instant from, @Nullable Instant until) {
        try {
            Calendar.Events.List listRequest = this.service.events().list(calendarId);
            if(nonNull(from)) listRequest.setTimeMin(instantToDateTime(from));
            if(nonNull(until)) listRequest.setTimeMax(instantToDateTime(until));
            return new HashSet<>(listRequest
                    .execute()
                    .getItems());
        }
        catch(GoogleJsonResponseException e) {
            log.info("Cannot find calendar \"{}\"", calendarId, e);
            return new HashSet<>();
        }
        catch (IOException e) {
            log.error("I/O error while querying events from calendar \"{}\"", calendarId, e);
            return new HashSet<>();
        }
    }

    @Override
    public Optional<IMeeting> findOne(MeetingId meetingId) {
        try {
            String calendarId = resolveCalendarId(meetingId.getLocationIdPart());
            return findOne(meetingId.getNameIdPart(), calendarId);
        }
        catch(IllegalArgumentException e) {
            log.warn("Cannot find meeting \"{}\" in room \"{}\" since it has no mapped calendar ID", meetingId, meetingId.getLocationIdPart(), e);
            return Optional.empty();
        }
    }

    private Optional<IMeeting> findOne(String eventId, String calendarId) {
        try {
            Event event = this.service.events()
                    .get(calendarId, eventId)
                    .execute();
            log.info("Found event \"{}\" in calendar \"{}\"", eventId, calendarId);
            return eventToMeeting(event);
        }
        catch(GoogleJsonResponseException e) {
            log.info("Cannot find event \"{}\" in calendar \"{}\"", eventId, calendarId, e);
            return Optional.empty();
        }
        catch(IOException e) {
            log.error("I/O error while querying event \"{}\" from calendar \"{}\"", eventId, calendarId, e);
            return Optional.empty();
        }
    }

    @Override
    public Map<MeetingId, Optional<IMeeting>> findMultiple(Set<MeetingId> meetingIds) {
        Map<MeetingId, Optional<IMeeting>> meetings = new HashMap<>();
        for(MeetingId meetingId : meetingIds) {
            meetings.put(meetingId, findOne(meetingId));
        }
        return meetings;
    }

    @Override
    public Optional<IMeeting> findCurrent(RoomId roomId) {
        return findCurrentGoogleCalEvent(roomId)
                .map(this::eventToMeeting)
                .orElse(Optional.empty());
    }

    private Optional<Event> findCurrentGoogleCalEvent(RoomId roomId) {
        Set<Event> events = findAllGoogleCalEvents(roomId);
        return events.stream()
                .filter(this::isEventInProgress)
                .findFirst();
    }

    @Override
    public IMeeting create(IMeeting meeting) {
        try {
            // TODO: Meaningful exception message
            if(isCollidingWithOtherMeetings(meeting)) throw new EntityConflictException();
            String calendarId = resolveCalendarId(meeting.getId().getLocationIdPart());
            Event createdEvent = this.service.events().insert(calendarId, meetingToEvent(meeting)).execute();
            log.info("Created meeting \"{}\"", meeting);
            // TODO: Meaningful exception message
            return eventToMeeting(createdEvent).orElseThrow(EntityCreationException::new);
        }
        catch(IllegalArgumentException e) {
            log.error("Cannot create meeting \"{}\" in room \"{}\" since it has no mapped calendar ID", meeting, meeting.getRoomId());
            // TODO: Meaningful exception message
            throw new EntityCreationException(e);
        }
        catch(IOException e) {
            log.error("I/O error while creating meeting \"{}\"", meeting);
            // TODO: Meaningful exception message
            throw new EntityCreationException(e);
        }
    }

    private boolean isCollidingWithOtherMeetings(IMeeting meetingToCheck) throws IllegalArgumentException {
        String calendarId = resolveCalendarId(meetingToCheck.getId().getLocationIdPart());
        Set<IMeeting> collidingMeetings = findAll(calendarId, meetingToCheck.getStart(), meetingToCheck.getEnd())
                .stream()
                .filter(meeting -> !meeting.getId().equals(meetingToCheck.getId()))
                .collect(Collectors.toSet());
        return !collidingMeetings.isEmpty();
    }

    @Override
    public Set<IMeeting> create(Set<IMeeting> meetings) {
        Set<IMeeting> createdMeetings = new HashSet<>();
        for(IMeeting meeting : meetings) {
            createdMeetings.add(create(meeting));
        }
        return createdMeetings;
    }

    @Override
    public DeletionResult delete(MeetingId meetingId) {
        try {
            String calendarId = resolveCalendarId(meetingId.getLocationIdPart());
            this.service.events().delete(calendarId, meetingId.getNameIdPart()).execute();
            log.info("Deleted meeting \"{}\"", meetingId);
            return DeletionResult.SUCCESS;
        }
        catch(IllegalArgumentException e) {
            log.error("Cannot delete meeting \"{}\" in room \"{}\" since it has no mapped calendar ID", meetingId, meetingId.getLocationIdPart(), e);
            return DeletionResult.ERROR;
        }
        catch(GoogleJsonResponseException e) {
            log.error("Cannot delete meeting \"{}\" in room \"{}\" since it does not exist", meetingId, meetingId.getLocationIdPart(), e);
            return DeletionResult.ERROR;
        }
        catch(IOException e) {
            log.error("I/O error while deleting meeting \"{}\"", meetingId, e);
            return DeletionResult.ERROR;
        }
    }

    @Override
    public ShorteningResult shortenMeeting(IMeeting meeting, Duration shortening) {
        meeting.setEnd(meeting.getEnd().minus(shortening));
        String calendarId = resolveCalendarId(meeting.getId().getLocationIdPart());
        try {
            this.service.events().update(
                    calendarId,
                    meeting.getId().getNameIdPart(),
                    meetingToEvent(meeting)).execute();
        } catch (IOException e) {
            log.error("I/O error while shortening meeting \"{}\"", meeting, e);
            return ShorteningResult.ERROR;
        }
        return ShorteningResult.SUCCESS;
    }

    @Override
    public ExtensionResult extendMeeting(IMeeting meeting, Duration extension) {
        meeting.setEnd(meeting.getEnd().plus(extension));
        if(isCollidingWithOtherMeetings(meeting)) return ExtensionResult.CONFLICT;
        String calendarId = resolveCalendarId(meeting.getId().getLocationIdPart());
        try {
            this.service.events().update(
                    calendarId,
                    meeting.getId().getNameIdPart(),
                    meetingToEvent(meeting)).execute();
        } catch (IOException e) {
            log.error("I/O error while extending meeting \"{}\"", meeting, e);
            return ExtensionResult.ERROR;
        }
        return ExtensionResult.SUCCESS;
    }

    @Override
    public ShiftResult shiftMeeting(IMeeting meeting, Duration shift) {
        meeting.setStart(meeting.getStart().plus(shift));
        meeting.setEnd(meeting.getEnd().plus(shift));
        if(isCollidingWithOtherMeetings(meeting)) return ShiftResult.CONFLICT;
        String calendarId = resolveCalendarId(meeting.getId().getLocationIdPart());
        try {
            this.service.events().update(
                    calendarId,
                    meeting.getId().getNameIdPart(),
                    meetingToEvent(meeting)).execute();
        } catch (IOException e) {
            log.error("I/O error while shifting meeting \"{}\"", meeting, e);
            return ShiftResult.ERROR;
        }
        return ShiftResult.SUCCESS;
    }

    private List<CalendarListEntry> findAllCalendars() {
        try {
            return this.service.calendarList().list().execute().getItems();
        }
        catch(IOException e) {
            log.error("I/O error while querying available calendars", e);
            return new ArrayList<>();
        }
    }

    private CalendarListEntry findSpecificCalendar(String calendarId) throws IOException {
        return this.service.calendarList().get(calendarId).execute();
    }

    private Set<IMeeting> eventsToMeetings(Set<Event> events) {
        return events
                .stream()
                .map(this::eventToMeeting)
                .flatMap(optional -> optional.map(Stream::of).orElseGet(Stream::empty)) // Filter out empty optionals
                .collect(Collectors.toSet());
    }

    private Optional<IMeeting> eventToMeeting(Event event) {
        String description = event.getDescription();
        description = isNull(description) ? StringUtils.EMPTY : description;
        IMeeting parsedMeeting;
        try {
            parsedMeeting = this.meetingParser.parse(description);
        }
        catch(InvalidSyntaxException e) {
            log.error("Failed to create Smart Lab meeting from Google calendar event {} because of invalid configuration syntax", event, e);
            return Optional.empty();
        }
        return Optional.of(Meeting.builder()
                .id(MeetingId.of(event.getId(), RoomId.of(event.getLocation())))
                .title(event.getSummary())
                .start(eventDateTimeToInstant(event.getStart()))
                .end(eventDateTimeToInstant(event.getEnd()))
                .build()
                .merge(parsedMeeting));
    }

    private Event meetingToEvent(IMeeting meeting) {
        String meetingConfigString = this.meetingParser.unparse(meeting);
        return new Event()
            .setSummary(meeting.getTitle())
            .setStart(instantToEventDateTime(meeting.getStart()))
            .setEnd(instantToEventDateTime(meeting.getEnd()))
            .setLocation(meeting.getRoomId().getIdValue())
            .setDescription(meetingConfigString);
    }

    private boolean isEventInProgress(Event event) {
        return isNowInProgress(
                eventDateTimeToInstant(event.getStart()),
                eventDateTimeToInstant(event.getEnd()));
    }

    private Instant dateTimeToInstant(DateTime dateTime) {
        return Instant.ofEpochMilli(dateTime.getValue());
    }

    private Instant eventDateTimeToInstant(EventDateTime eventDateTime) {
        return dateTimeToInstant(eventDateTime.getDateTime());
    }

    private DateTime instantToDateTime(Instant instant) {
        return new DateTime(instant.toEpochMilli());
    }

    private EventDateTime instantToEventDateTime(Instant instant) {
        return new EventDateTime().setDateTime(instantToDateTime(instant));
    }
}
