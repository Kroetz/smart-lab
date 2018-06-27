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
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.Meeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabcore.result.*;
import de.qaware.smartlabmeeting.repository.IMeetingManagementRepository;
import de.qaware.smartlabmeeting.repository.parser.IMeetingParser;
import de.qaware.smartlabsampledata.provider.ISampleDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import sun.plugin.dom.exception.InvalidStateException;

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
public class GoogleCalendarAdapter implements IMeetingManagementRepository {

    // TODO: Null checks everywhere after uses of this map?
    private final BiMap<RoomId, String> calendarIdsByRoomId;
    private final Calendar service;
    private final IMeetingParser meetingParser;

    public GoogleCalendarAdapter(
            IMeetingParser meetingParser,
            ISampleDataProvider sampleDataProvider,
            Path googleCalendarCredentialFile,
            // TODO: String literal
            @Qualifier("googleCalendarScopes") Collection<String> googleCalendarScopes,
            String googleCalendarApplicationName,
            HttpTransport googleCalendarHttpTransport,
            JsonFactory googleCalendarJsonFactory,
            // TODO: String literal
            @Qualifier("googleCalendarRoomMapping") BiMap<RoomId, String> googleCalendarRoomMapping) throws IOException {
	    this.meetingParser = meetingParser;
	    this.calendarIdsByRoomId = googleCalendarRoomMapping;
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
        create(sampleDataProvider.getMeetings());
    }

    @Override
    public Map<RoomId, Set<IMeeting>> findAll() {
        Map<RoomId, Set<IMeeting>> meetingsByRoom = new HashMap<>();
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
                if(this.calendarIdsByRoomId.inverse().containsKey(calendar.getId())) {
                    RoomId roomId = this.calendarIdsByRoomId.inverse().get(calendar.getId());
                    meetingsByRoom.put(roomId, meetings);
                }
                else {
                    log.error("A calendar ID must be mapped to a room ID");
                    throw new InvalidStateException("A calendar ID must be mapped to a room ID");
                }
            }
            return meetingsByRoom;
        } catch (IOException e) {
            log.error("I/O error while querying events from Google calendar");
            return new HashMap<>();
        }
    }

    @Override
    public Set<IMeeting> findAll(RoomId roomId) {
        if(!this.calendarIdsByRoomId.containsKey(roomId)) return new HashSet<>();
        try {
            return this.service.events()
                    .list(this.calendarIdsByRoomId.get(roomId))
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

    @Override
    public Optional<IMeeting> findOne(MeetingId meetingId, RoomId roomId) {
        if(!this.calendarIdsByRoomId.containsKey(roomId)) return Optional.empty();
        try {
            Event event = this.service.events().get(
                    this.calendarIdsByRoomId.get(roomId),
                    meetingId.getIdValue())
                    .execute();
            return Optional.of(eventToMeeting(event));
        } catch (IOException e) {
            log.error("I/O error while querying event from Google calendar");
            return Optional.empty();
        }
    }

    @Override
    public Map<MeetingId, Optional<IMeeting>> findMultiple(Set<MeetingId> meetingIds, RoomId roomId) {
        Map<MeetingId, Optional<IMeeting>> meetings = new HashMap<>();
        for(MeetingId meetingId : meetingIds) {
            meetings.put(meetingId, findOne(meetingId, roomId));
        }
        return meetings;
    }

    @Override
    public CreationResult create(IMeeting meeting) {
        if(!this.calendarIdsByRoomId.containsKey(meeting.getRoomId())) {
            log.error("There must be a calendar for the specified room");
            return CreationResult.ERROR;
        }
        String calendarId = this.calendarIdsByRoomId.get(meeting.getRoomId());
        Event event = meetingToEvent(meeting);
        try {
            this.service.events().insert(calendarId, event).execute();
        } catch (IOException e) {
            log.error("I/O error while creating event in Google calendar");
            return CreationResult.ERROR;
        }
        return CreationResult.SUCCESS;
        //  TODO: What happens if the is an event conflict in the Google calendar?
    }

    private CreationResult create(Set<IMeeting> meetings) {
        for(IMeeting meeting : meetings) {
            CreationResult creationResult = create(meeting);
            if(creationResult != CreationResult.SUCCESS) return creationResult;
        }
        return CreationResult.SUCCESS;
    }

    @Override
    public DeletionResult delete(MeetingId meetingId, RoomId roomId) {
        String calendarId = this.calendarIdsByRoomId.get(roomId);
        try {
            this.service.events().delete(calendarId, meetingId.getIdValue());
        } catch (IOException e) {
            log.error("I/O error while deleting event in Google calendar");
            return DeletionResult.ERROR;
        }
        return DeletionResult.SUCCESS;
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
                .id(MeetingId.of(event.getId()))
                .title(event.getSummary())
                .start(eventDateTimeToInstant(event.getStart()))
                .end(eventDateTimeToInstant(event.getEnd()))
                .roomId(RoomId.of(event.getLocation()))
                .build()
                .merge(parsedMeeting);
    }

    private Event meetingToEvent(IMeeting meeting) {
	//String meetingConfigurationAsString =
    //    	this.meetingConfigurationParser.parse(meeting.getMeetingConfiguration());
	return new Event()        
		// TODO
		//.setId(meeting.getId().getIdValue())
		.setSummary(meeting.getTitle())
		.setStart(instantToEventDateTime(meeting.getStart()))
		.setEnd(instantToEventDateTime(meeting.getEnd()))
		.setLocation(meeting.getRoomId().getIdValue())
		//.setDescription(meetingConfigurationAsString);
	    .setDescription(
	            "@@@smart-lab-config-begin\n" +
                "    @@@workgroup = \"workgroupId\"\n" +
                "    @@@agenda-begin\n" +
                "        \"Take a nap\"\n" +
                "        \"Drink coffee\"\n" +
                "        \"Go home\"\n" +
                "    @@@agenda-end\n" +
                "    @@@assistances-begin\n" +
                "        minuteTaking(\n" +
                "            language: \"english\",\n" +
                "            uploadDir: \"folder/in/repo/minutes.txt\",\n" +
                "            commitMessage: \"commitMessage\",\n" +
                "            microphoneId: \"microphoneId\")\n" +
                "        agendaShowing(displayId: \"displayId\")\n" +
                //"        displayWebsite(\n" +
                //"            url: \"https://github.com/Kroetz/coastGuardRepo\",\n" +
                //"            displayId: \"displayId1\")\n" +
                //"        displayWebsite(\n" +
                //"            url: \"https://github.com/Kroetz/forestRangersRepo\",\n" +
                //"            displayId: \"displayId2\")\n" +
                //"        displayFile(\n" +
                //"            file: \"folder/in/repo/file.pptx\",\n" +
                //"            displayId: \"displayId\")\n" +
                "    @@@assistances-end\n" +
                "@@@smart-lab-config-end");
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
}
