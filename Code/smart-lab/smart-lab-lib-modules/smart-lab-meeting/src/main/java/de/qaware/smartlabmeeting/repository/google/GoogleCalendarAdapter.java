package de.qaware.smartlabmeeting.repository.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
@ConditionalOnProperty(
        prefix = Property.Prefix.MEETING_MANAGEMENT_REPOSITORY,
        name = Property.Name.MEETING_MANAGEMENT_REPOSITORY,
        havingValue = Property.Value.MeetingManagementRepository.GOOGLE_CALENDAR)
@Slf4j
public class GoogleCalendarAdapter implements IMeetingManagementRepository {

    private final Calendar service;
    private final IMeetingParser meetingParser;

    public GoogleCalendarAdapter(
            IMeetingParser meetingParser,
            ISampleDataProvider sampleDataProvider,
            Path googleCalendarCredentialFile,
            Collection<String> googleCalendarScopes,
            String googleCalendarApplicationName,
            HttpTransport googleCalendarHttpTransport,
            JsonFactory googleCalendarJsonFactory) throws IOException {
        this.meetingParser = meetingParser;
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

        // TODO: fill with sample data
        //this.entities = new HashSet<>(sampleDataProvider.getMeetings());
    }

    @Override
    public Map<RoomId, Set<IMeeting>> findAll() {
        return null;
    }

    @Override
    public Set<IMeeting> findAll(RoomId roomId) {
        return null;
    }

    @Override
    public Optional<IMeeting> findOne(MeetingId meetingId, RoomId roomId) {
        return Optional.empty();
    }

    @Override
    public Map<MeetingId, Optional<IMeeting>> findMultiple(Set<MeetingId> meetingIds, RoomId roomId) {
        return null;
    }

    @Override
    public CreationResult create(IMeeting meeting) {
        return null;
    }

    @Override
    public DeletionResult delete(MeetingId meetingId, RoomId roomId) {
        return null;
    }

    @Override
    public ShorteningResult shortenMeeting(IMeeting meeting, Duration shortening) {
        return null;
    }

    @Override
    public ExtensionResult extendMeeting(IMeeting meeting, Duration extension) {
        return null;
    }

    @Override
    public ShiftResult shiftMeeting(IMeeting meeting, Duration shift) {
        return null;
    }

    private IMeeting eventToMeeting(Event event) {
        String description = event.getDescription();
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
