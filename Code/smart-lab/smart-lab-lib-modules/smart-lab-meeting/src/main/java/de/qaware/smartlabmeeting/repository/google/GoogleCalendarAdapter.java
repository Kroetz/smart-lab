package de.qaware.smartlabmeeting.repository.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.calendar.Calendar;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabcore.result.*;
import de.qaware.smartlabmeeting.repository.IMeetingManagementRepository;
import de.qaware.smartlabsampledata.provider.ISampleDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
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

    public GoogleCalendarAdapter(
            ISampleDataProvider sampleDataProvider,
            Path googleCalendarCredentialFile,
            Collection<String> googleCalendarScopes,
            String googleCalendarApplicationName,
            HttpTransport googleCalendarHttpTransport,
            JsonFactory googleCalendarJsonFactory) throws IOException {
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

        // TODO
        //this.entities = new HashSet<>(sampleDataProvider.getMeetings());
    }

    @Override
    public Set<IMeeting> findAll() {
        return null;
    }

    @Override
    public Optional<IMeeting> findOne(MeetingId meetingId) {
        return Optional.empty();
    }

    @Override
    public Map<MeetingId, Optional<IMeeting>> findMultiple(Set<MeetingId> meetingIds) {
        return null;
    }

    @Override
    public CreationResult create(IMeeting meeting) {
        return null;
    }

    @Override
    public DeletionResult delete(MeetingId meetingId) {
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
}
