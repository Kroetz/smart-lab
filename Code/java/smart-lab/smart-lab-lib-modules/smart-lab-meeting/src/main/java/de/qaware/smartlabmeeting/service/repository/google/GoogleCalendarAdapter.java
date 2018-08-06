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
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.Meeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.exception.EntityConflictException;
import de.qaware.smartlabcore.exception.EntityCreationException;
import de.qaware.smartlabcore.exception.InvalidSyntaxException;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabcore.result.DeletionResult;
import de.qaware.smartlabcore.result.ExtensionResult;
import de.qaware.smartlabcore.result.ShiftResult;
import de.qaware.smartlabcore.result.ShorteningResult;
import de.qaware.smartlabcore.service.repository.AbstractBasicEntityManagementRepository;
import de.qaware.smartlabmeeting.service.repository.IMeetingManagementRepository;
import de.qaware.smartlabmeeting.service.repository.parser.IMeetingParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

import static de.qaware.smartlabcore.miscellaneous.TimeUtils.isNowInProgress;
import static java.lang.String.format;
import static java.nio.file.Files.newInputStream;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Repository
@ConditionalOnProperty(
        prefix = Property.Prefix.MEETING_MANAGEMENT_REPOSITORY,
        name = Property.Name.MEETING_MANAGEMENT_REPOSITORY,
        havingValue = Property.Value.MeetingManagementRepository.GOOGLE_CALENDAR)
@Slf4j
public class GoogleCalendarAdapter extends AbstractBasicEntityManagementRepository<IMeeting, MeetingId> implements IMeetingManagementRepository {

    private final Calendar service;
    private final BiMap<LocationId, String> calendarIdsByLocationId;
    private final IMeetingParser meetingParser;

    public GoogleCalendarAdapter(
            Path googleCalendarCredentialFile,
            // TODO: String literal
            @Qualifier("googleCalendarScopes") Collection<String> googleCalendarScopes,
            String googleCalendarApplicationName,
            HttpTransport googleCalendarHttpTransport,
            JsonFactory googleCalendarJsonFactory,
            @Qualifier("googleCalendarLocationMapping") BiMap<LocationId, String> googleCalendarLocationMapping,
            IMeetingParser meetingParser,
            Set<IMeeting> initialMeetings) throws IOException {
        super(initialMeetings);
        GoogleCredential credentials = GoogleCredential.fromStream(
                newInputStream(googleCalendarCredentialFile),
                googleCalendarHttpTransport,
                googleCalendarJsonFactory).createScoped(googleCalendarScopes);
        this.service = new Calendar.Builder(
                googleCalendarHttpTransport,
                googleCalendarJsonFactory,
                credentials)
                .setApplicationName(googleCalendarApplicationName)
                .build();
        this.calendarIdsByLocationId = googleCalendarLocationMapping;
        this.meetingParser = meetingParser;
    }

    private String resolveCalendarId(LocationId locationId) throws IllegalArgumentException {
        String calendarId = this.calendarIdsByLocationId.get(locationId);
        if(nonNull(calendarId)) return calendarId;
        throw new IllegalArgumentException(format("The location \"%s\" has no mapped calendar", locationId));
    }

    private LocationId resolveLocationId(String calendarId) throws IllegalArgumentException {
        LocationId locationId = this.calendarIdsByLocationId.inverse().get(calendarId);
        if(nonNull(locationId)) return locationId;
        throw new IllegalArgumentException(format("The calendar \"%s\" has no mapped location", calendarId));
    }

    private boolean hasMappedCalendar(LocationId locationId) {
        try {
            resolveCalendarId(locationId);
            return true;
        }
        catch(IllegalArgumentException e) {
            return false;
        }
    }

    private boolean hasMappedLocation(String calendarId) {
        try {
            resolveLocationId(calendarId);
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
            if(hasMappedLocation(calendarId)) {
                foundEvents.addAll(findAllGoogleCalEvents(calendarId));
            }
            else {
                log.warn("Ignoring events from calendar \"{}\" since it has no mapped location ID", calendarId);
            }
        }
        return foundEvents;
    }

    @Override
    public Set<IMeeting> findAll(LocationId locationId) {
        return eventsToMeetings(findAllGoogleCalEvents(locationId));
    }

    private Set<Event> findAllGoogleCalEvents(LocationId locationId) {
        try {
            return findAllGoogleCalEvents(resolveCalendarId(locationId));
        }
        catch(IllegalArgumentException e) {
            log.warn("Cannot find events at location \"{}\" since it has no mapped calendar ID", locationId, e);
            return emptySet();
        }
    }

    @Override
    public Set<IMeeting> findAll(WorkgroupId workgroupId) {
        return findAll().stream()
                .filter(meeting -> meeting.getWorkgroupId().equals(workgroupId))
                .collect(toSet());
    }

    @Override
    public Set<IMeeting> findAllCurrent() {
        return eventsToMeetings(findAllCurrentGoogleCalEvents());
    }

    private Set<Event> findAllCurrentGoogleCalEvents() {
        Set<Event> events = findAllGoogleCalEvents();
        return events.stream()
                .filter(this::isEventInProgress)
                .collect(toSet());
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
            return emptySet();
        }
        catch (IOException e) {
            log.error("I/O error while querying events from calendar \"{}\"", calendarId, e);
            return emptySet();
        }
    }

    @Override
    public Optional<IMeeting> findOne(MeetingId meetingId) {
        try {
            String calendarId = resolveCalendarId(meetingId.getLocationIdPart());
            return findOne(meetingId.getNameIdPart(), calendarId);
        }
        catch(IllegalArgumentException e) {
            log.warn("Cannot find meeting \"{}\" at location \"{}\" since it has no mapped calendar ID", meetingId, meetingId.getLocationIdPart(), e);
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
    public Optional<IMeeting> findCurrent(LocationId locationId) {
        return findCurrentGoogleCalEvent(locationId)
                .map(this::eventToMeeting)
                .orElse(Optional.empty());
    }

    private Optional<Event> findCurrentGoogleCalEvent(LocationId locationId) {
        Set<Event> events = findAllGoogleCalEvents(locationId);
        return events.stream()
                .filter(this::isEventInProgress)
                .findFirst();
    }

    @Override
    public Optional<IMeeting> findCurrent(WorkgroupId workgroupId) {
        return findAllCurrent().stream()
                .filter(meeting -> meeting.getWorkgroupId().equals(workgroupId))
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
            log.error("Cannot create meeting \"{}\" at location \"{}\" since it has no mapped calendar ID", meeting, meeting.getLocationId());
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
                .collect(toSet());
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
            log.error("Cannot delete meeting \"{}\" at location \"{}\" since it has no mapped calendar ID", meetingId, meetingId.getLocationIdPart(), e);
            return DeletionResult.ERROR;
        }
        catch(GoogleJsonResponseException e) {
            log.error("Cannot delete meeting \"{}\" at location \"{}\" since it does not exist", meetingId, meetingId.getLocationIdPart(), e);
            return DeletionResult.ERROR;
        }
        catch(IOException e) {
            log.error("I/O error while deleting meeting \"{}\"", meetingId, e);
            return DeletionResult.ERROR;
        }
    }

    @Override
    public ShorteningResult shortenMeeting(IMeeting meeting, Duration shortening) {
        IMeeting shortenedMeeting = meeting.withEnd(meeting.getEnd().minus(shortening));
        String calendarId = resolveCalendarId(shortenedMeeting.getId().getLocationIdPart());
        try {
            this.service.events().update(
                    calendarId,
                    shortenedMeeting.getId().getNameIdPart(),
                    meetingToEvent(shortenedMeeting)).execute();
        } catch (IOException e) {
            log.error("I/O error while shortening meeting \"{}\"", meeting, e);
            return ShorteningResult.ERROR;
        }
        return ShorteningResult.SUCCESS;
    }

    @Override
    public ExtensionResult extendMeeting(IMeeting meeting, Duration extension) {
        IMeeting extendedMeeting = meeting.withEnd(meeting.getEnd().plus(extension));
        if(isCollidingWithOtherMeetings(extendedMeeting)) return ExtensionResult.CONFLICT;
        String calendarId = resolveCalendarId(extendedMeeting.getId().getLocationIdPart());
        try {
            this.service.events().update(
                    calendarId,
                    extendedMeeting.getId().getNameIdPart(),
                    meetingToEvent(extendedMeeting)).execute();
        } catch (IOException e) {
            log.error("I/O error while extending meeting \"{}\"", meeting, e);
            return ExtensionResult.ERROR;
        }
        return ExtensionResult.SUCCESS;
    }

    @Override
    public ShiftResult shiftMeeting(IMeeting meeting, Duration shift) {
        IMeeting shiftedMeeting = meeting.withStartAndEnd(
                meeting.getStart().plus(shift),
                meeting.getEnd().plus(shift));
        if(isCollidingWithOtherMeetings(shiftedMeeting)) return ShiftResult.CONFLICT;
        String calendarId = resolveCalendarId(shiftedMeeting.getId().getLocationIdPart());
        try {
            this.service.events().update(
                    calendarId,
                    shiftedMeeting.getId().getNameIdPart(),
                    meetingToEvent(shiftedMeeting)).execute();
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
            return emptyList();
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
                .collect(toSet());
    }

    private Optional<IMeeting> eventToMeeting(Event event) {
        String description = event.getDescription();
        description = isNull(description) ? EMPTY : description;
        IMeeting parsedMeeting;
        try {
            parsedMeeting = this.meetingParser.parse(description);
        }
        catch(InvalidSyntaxException e) {
            log.error("Failed to create Smart Lab meeting from Google calendar event {} because of invalid configuration syntax", event, e);
            return Optional.empty();
        }
        return Optional.of(Meeting.builder()
                .id(MeetingId.of(event.getId(), LocationId.of(event.getLocation())))
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
            .setLocation(meeting.getLocationId().getIdValue())
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
