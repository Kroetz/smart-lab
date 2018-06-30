package de.qaware.smartlabmeeting.repository.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.common.collect.BiMap;
import de.qaware.smartlabcore.data.generic.IBiResolver;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.Meeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.exception.EntityConflictException;
import de.qaware.smartlabcore.exception.EntityCreationException;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabcore.result.*;
import de.qaware.smartlabmeeting.repository.generic.AbstractMeetingManagementRepository;
import de.qaware.smartlabmeeting.repository.parser.IMeetingParser;
import de.qaware.smartlabsampledata.provider.ISampleDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Repository
@ConditionalOnProperty(
        prefix = Property.Prefix.MEETING_MANAGEMENT_REPOSITORY,
        name = Property.Name.MEETING_MANAGEMENT_REPOSITORY,
        havingValue = Property.Value.MeetingManagementRepository.GOOGLE_CALENDAR)
@Slf4j
public class GoogleCalendarAdapter extends AbstractMeetingManagementRepository {

    private final Calendar service;
    private final IBiResolver<RoomId, String> calendarIdResolver;
    private final IMeetingParser meetingParser;

    public GoogleCalendarAdapter(
            ISampleDataProvider sampleDataProvider,
            Path googleCalendarCredentialFile,
            // TODO: String literal
            @Qualifier("googleCalendarScopes") Collection<String> googleCalendarScopes,
            String googleCalendarApplicationName,
            HttpTransport googleCalendarHttpTransport,
            JsonFactory googleCalendarJsonFactory,
            IBiResolver<RoomId, String> calendarIdResolver,
            IMeetingParser meetingParser) throws IOException {
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
        this.calendarIdResolver = calendarIdResolver;
        this.meetingParser = meetingParser;
        create(sampleDataProvider.getMeetings());
    }

    @Override
    public Set<IMeeting> findAll() {
        Set<IMeeting> allMeetings = new HashSet<>();
        try {
            List<CalendarListEntry> calendars = findAllCalendars();
            for(CalendarListEntry calendar : calendars) {
                Set<IMeeting> meetings = this.service
                        .events()
                        .list(calendar.getId())
                        .execute()
                        .getItems()
                        .stream()
                        .map(this::eventToMeeting)
                        .collect(Collectors.toSet());
                // TODO: Cleaner with "ifPresentOrElse" from Java 9 (See https://stackoverflow.com/questions/23773024/functional-style-of-java-8s-optional-ifpresent-and-if-not-present)
                Optional<RoomId> roomId = this.calendarIdResolver.inverseResolve(calendar.getId());
                if(roomId.isPresent()) {
                    allMeetings.addAll(meetings);
                }
                else {
                    log.warn("Ignoring events from calendar {} since it has no mapped room ID", calendar.getId());
                }
            }
            return allMeetings;
        } catch (IOException e) {
            log.error("I/O error while querying events from Google calendar");
            return new HashSet<>();
        }
    }

    @Override
    public Set<IMeeting> findAll(RoomId roomId) {
        // TODO: Cleaner with "ifPresentOrElse" from Java 9 (See https://stackoverflow.com/questions/23773024/functional-style-of-java-8s-optional-ifpresent-and-if-not-present)
        Optional<String> calendarId = this.calendarIdResolver.resolve(roomId);
        if(calendarId.isPresent()) {
            try {
                return this.service.events()
                        .list(calendarId.get())
                        .execute()
                        .getItems()
                        .stream()
                        .map(this::eventToMeeting)
                        .collect(Collectors.toSet());
            } catch (IOException e) {
                log.error("I/O error while querying events from Google calendar");
                return new HashSet<>();
            }
        }
        log.warn("Cannot find meetings in room {} since it has no mapped calendar ID", roomId);
        return new HashSet<>();
    }

    @Override
    public Optional<IMeeting> findOne(MeetingId meetingId) {
        // TODO: Cleaner with "ifPresentOrElse" from Java 9 (See https://stackoverflow.com/questions/23773024/functional-style-of-java-8s-optional-ifpresent-and-if-not-present)
        Optional<String> calendarId = this.calendarIdResolver.resolve(meetingId.getLocationIdPart());
        if(calendarId.isPresent()) {
            try {
                Event event = this.service.events().get(
                        calendarId.get(),
                        meetingId.getIdValue())
                        .execute();
                return Optional.of(eventToMeeting(event));
            } catch (IOException e) {
                log.error("I/O error while querying event from Google calendar");
                return Optional.empty();
            }
        }
        log.warn("Cannot find meeting {} in room {} since it has no mapped calendar ID", meetingId, meetingId.getLocationIdPart());
        return Optional.empty();
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
    public IMeeting create(IMeeting meeting) {
        boolean meetingCollision = findAll(meeting.getRoomId()).stream().anyMatch(m -> areMeetingsColliding(meeting, m));
        if(meetingCollision || exists(meeting.getId())) {
            log.error("Cannot create meeting {} because a meeting with that ID already exists", meeting);
            // TODO: Meaningful exception message
            throw new EntityConflictException();
        }
        // TODO: Cleaner with "ifPresentOrElse" from Java 9 (See https://stackoverflow.com/questions/23773024/functional-style-of-java-8s-optional-ifpresent-and-if-not-present)
        Optional<String> calendarId = this.calendarIdResolver.resolve(meeting.getRoomId());
        if(calendarId.isPresent()) {
            Event createdEvent;
            try {
                createdEvent = this.service.events().insert(calendarId.get(), meetingToEvent(meeting)).execute();
            } catch (IOException e) {
                log.error("I/O error while creating event in Google calendar");
                // TODO: Meaningful exception message
                throw new EntityCreationException(e);
            }
            return eventToMeeting(createdEvent);
        }
        log.error("Cannot create meeting {} in room {} since it has no mapped calendar ID", meeting, meeting.getRoomId());
        // TODO: Meaningful exception message
        throw new EntityCreationException();
    }

    private Set<IMeeting> create(Set<IMeeting> meetings) {
        Set<IMeeting> createdMeetings = new HashSet<>();
        for(IMeeting meeting : meetings) {
            createdMeetings.add(create(meeting));
        }
        return createdMeetings;
    }

    @Override
    public DeletionResult delete(MeetingId meetingId) {
        // TODO: Cleaner with "ifPresentOrElse" from Java 9 (See https://stackoverflow.com/questions/23773024/functional-style-of-java-8s-optional-ifpresent-and-if-not-present)
        Optional<String> calendarId = this.calendarIdResolver.resolve(meetingId.getLocationIdPart());
        if(calendarId.isPresent()) {
            try {
                this.service.events().delete(calendarId.get(), meetingId.getIdValue());
            } catch (IOException e) {
                log.error("I/O error while deleting event in Google calendar");
                return DeletionResult.ERROR;
            }
            return DeletionResult.SUCCESS;
        }
        log.warn("Cannot delete meeting {} in room {} since it has no mapped calendar ID", meetingId, meetingId.getLocationIdPart());
        return DeletionResult.ERROR;
    }

    @Override
    public ShorteningResult shortenMeeting(IMeeting meeting, Duration shortening) {
        // TODO
        throw new NotYetImplementedException();
    }

    @Override
    public ExtensionResult extendMeeting(IMeeting meeting, Duration extension) {
        // TODO
        throw new NotYetImplementedException();
    }

    @Override
    public ShiftResult shiftMeeting(IMeeting meeting, Duration shift) {
        // TODO
        throw new NotYetImplementedException();
    }

    private List<CalendarListEntry> findAllCalendars() throws IOException {
        return this.service.calendarList().list().execute().getItems();
    }

    private CalendarListEntry findSpecificCalendar(String calendarId) throws IOException {
        return this.service.calendarList().get(calendarId).execute();
    }

    private IMeeting eventToMeeting(Event event) {
        String description = event.getDescription();
        description = isNull(description) ? StringUtils.EMPTY : description;
        IMeeting parsedMeeting = this.meetingParser.parse(description);
        return Meeting.builder()
                .id(MeetingId.of(event.getId(), RoomId.of(event.getLocation())))
                .title(event.getSummary())
                .start(eventDateTimeToInstant(event.getStart()))
                .end(eventDateTimeToInstant(event.getEnd()))
                .build()
                .merge(parsedMeeting);
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

    public static Instant dateTimeToInstant(DateTime dateTime) {
        return Instant.ofEpochMilli(dateTime.getValue());
    }

    public static Instant eventDateTimeToInstant(EventDateTime eventDateTime) {
        return dateTimeToInstant(eventDateTime.getDateTime());
    }

    public static DateTime instantToDateTime(Instant instant) {
        return new DateTime(instant.toEpochMilli());
    }

    public static EventDateTime instantToEventDateTime(Instant instant) {
        return new EventDateTime().setDateTime(instantToDateTime(instant));
    }

    @Component
    @ConditionalOnProperty(
            prefix = Property.Prefix.MEETING_MANAGEMENT_REPOSITORY,
            name = Property.Name.MEETING_MANAGEMENT_REPOSITORY,
            havingValue = Property.Value.MeetingManagementRepository.GOOGLE_CALENDAR)
    @Slf4j
    private static class CalendarIdResolver implements IBiResolver<RoomId, String> {

        private final BiMap<RoomId, String> calendarIdsByRoomId;

        // TODO: String literal
        public CalendarIdResolver(@Qualifier("googleCalendarRoomMapping") BiMap<RoomId, String> googleCalendarRoomMapping) {
            this.calendarIdsByRoomId = googleCalendarRoomMapping;
        }

        @Override
        public Optional<String> resolve(RoomId roomId) {
            return Optional.ofNullable(this.calendarIdsByRoomId.get(roomId));
        }

        @Override
        public Optional<RoomId> inverseResolve(String calendarId) {
            return Optional.ofNullable(this.calendarIdsByRoomId.inverse().get(calendarId));
        }
    }
}
