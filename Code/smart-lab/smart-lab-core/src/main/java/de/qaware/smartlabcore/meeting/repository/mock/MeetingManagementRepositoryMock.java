package de.qaware.smartlabcore.meeting.repository.mock;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcore.meeting.repository.IMeetingManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class MeetingManagementRepositoryMock implements IMeetingManagementRepository {

    private final IMeetingConfigProviderMockClient meetingConfigProviderMockClient;

    public MeetingManagementRepositoryMock(IMeetingConfigProviderMockClient meetingConfigProviderMockClient) {
        this.meetingConfigProviderMockClient = meetingConfigProviderMockClient;
    }

    @Override
    public List<IMeeting> getMeetings() {
        return meetingConfigProviderMockClient.getMeetings();
    }

    @Override
    public Optional<IMeeting> getMeeting(long meetingId) {
        return meetingConfigProviderMockClient.getMeeting(meetingId);
    }

    @Override
    public boolean createMeeting(IMeeting meeting) {
        return meetingConfigProviderMockClient.createMeeting(meeting);
    }

    @Override
    public boolean deleteMeeting(long meetingId) {
        return meetingConfigProviderMockClient.deleteMeeting(meetingId);
    }

    @Override
    public void shortenMeeting(long meetingId, Duration shortening) {
        meetingConfigProviderMockClient.shortenMeeting(meetingId, shortening.toMinutes());
    }

    @Override
    public boolean extendMeeting(long meetingId, Duration extension) {
        return meetingConfigProviderMockClient.extendMeeting(meetingId, extension.toMinutes());
    }

    @Override
    public boolean shiftMeeting(long meetingId, Duration shift) {
        return meetingConfigProviderMockClient.shiftMeeting(meetingId, shift.toMinutes());
    }
}
