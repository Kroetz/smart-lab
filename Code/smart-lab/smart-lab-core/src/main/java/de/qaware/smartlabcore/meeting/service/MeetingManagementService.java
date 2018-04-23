package de.qaware.smartlabcore.meeting.service;

import de.qaware.smartlabcommons.api.client.IWorkgroupManagementApiClient;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcore.generic.result.*;
import de.qaware.smartlabcore.meeting.repository.IMeetingManagementRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MeetingManagementService implements IMeetingManagementService {

    public static final String APP_PROPERTY_MAX_MEETING_DURATION_IN_MINUTES = "meeting.max.duration.in.minutes";
    @Value("${" + APP_PROPERTY_MAX_MEETING_DURATION_IN_MINUTES + "}")
    private long MAXINMAL_MEETING_DURATION_IN_MINUTES;
    private Duration MAXIMAL_MEETING_DURATION = Duration.ofMinutes(MAXINMAL_MEETING_DURATION_IN_MINUTES);
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
        return getMeeting(meetingId).map(meeting -> {
            val shortenedDuration = meeting.getDuration().minus(shortening);
            if(shortenedDuration.isNegative() || shortenedDuration.isZero()) {
                return ShorteningResult.MINIMUM_REACHED;
            }
            return meetingManagementRepository.shortenMeeting(meetingId, shortening);
        }).orElse(ShorteningResult.NOT_FOUND);
    }

    @Override
    public ExtensionResult extendMeeting(String meetingId, Duration extension) {
        return getMeeting(meetingId).map(meeting -> {
            if(meeting.getDuration().plus(extension).compareTo(MAXIMAL_MEETING_DURATION) > 0) {
                return ExtensionResult.MAXIMUM_REACHED_REACHED;
            }
            return meetingManagementRepository.extendMeeting(meetingId, extension);
        }).orElse(ExtensionResult.NOT_FOUND);
    }

    @Override
    public ShiftResult shiftMeeting(String meetingId, Duration shift) {
        return getMeeting(meetingId).map(meeting -> {
            return meetingManagementRepository.shiftMeeting(meetingId, shift);
        }).orElse(ShiftResult.NOT_FOUND);
    }
}
