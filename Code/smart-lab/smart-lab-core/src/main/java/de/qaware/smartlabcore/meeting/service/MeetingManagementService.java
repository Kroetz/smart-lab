package de.qaware.smartlabcore.meeting.service;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcore.generic.result.*;
import de.qaware.smartlabcore.meeting.repository.IMeetingManagementRepository;
import de.qaware.smartlabcommons.api.client.IWorkgroupManagementApiClient;
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
    public Optional<IMeeting> getMeeting(String meetingId) {
        return meetingManagementRepository.getMeeting(meetingId);
    }

    @Override
    public CreationResult createMeeting(IMeeting meeting) {
        return meetingManagementRepository.createMeeting(meeting);
    }

    @Override
    public DeletionResult deleteMeeting(String meetingId) {
        return meetingManagementRepository.deleteMeeting(meetingId);
    }

    @Override
    public ShorteningResult shortenMeeting(String meetingId, Duration shortening) {
        return meetingManagementRepository.shortenMeeting(meetingId, shortening);
    }

    @Override
    public ExtensionResult extendMeeting(String meetingId, Duration extension) {
        return meetingManagementRepository.extendMeeting(meetingId, extension);
    }

    @Override
    public ShiftResult shiftMeeting(String meetingId, Duration shift) {
        return meetingManagementRepository.shiftMeeting(meetingId, shift);
    }
}
