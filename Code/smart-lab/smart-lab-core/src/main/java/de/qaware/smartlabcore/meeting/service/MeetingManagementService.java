package de.qaware.smartlabcore.meeting.service;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcore.meeting.repository.IMeetingManagementRepository;
import de.qaware.smartlabcommons.api.management.client.IWorkgroupManagementApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MeetingManagementService implements IMeetingManagementService {

    private final IMeetingManagementRepository meetingManagementRepository;
    private final IWorkgroupManagementApiClient workgroupManagementApiClient;

    public MeetingManagementService(
            IMeetingManagementRepository meetingManagementRepository,
            IWorkgroupManagementApiClient workgroupManagementApiClient) {
        this.meetingManagementRepository = meetingManagementRepository;
        this.workgroupManagementApiClient = workgroupManagementApiClient;
    }

    @Override
    public List<IMeeting> getMeetings() {
        return meetingManagementRepository.getMeetings();
    }

    @Override
    public Optional<IMeeting> getMeeting(long meetingId) {
        return meetingManagementRepository.getMeeting(meetingId);
    }

    @Override
    public boolean createMeeting(IMeeting meeting) {
        return meetingManagementRepository.createMeeting(meeting);
    }

    @Override
    public boolean deleteMeeting(long meetingId) {
        return meetingManagementRepository.deleteMeeting(meetingId);
    }

    @Override
    public void shortenMeeting(long meetingId, Duration shortening) {
        meetingManagementRepository.shortenMeeting(meetingId, shortening);
    }

    @Override
    public boolean extendMeeting(long meetingId, Duration extension) {
        return meetingManagementRepository.extendMeeting(meetingId, extension);
    }

    @Override
    public boolean shiftMeeting(long meetingId, Duration shift) {
        return meetingManagementRepository.shiftMeeting(meetingId, shift);
    }
}
