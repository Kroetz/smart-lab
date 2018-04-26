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
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class MeetingManagementService implements IMeetingManagementService {

    private static final String APP_PROPERTY_MAX_MEETING_DURATION_IN_MINUTES = "meeting.max.duration.in.minutes";
    public static Duration MAXIMAL_MEETING_DURATION;
    private final IMeetingManagementRepository meetingManagementRepository;
    private final IWorkgroupManagementApiClient workgroupManagementApiClient;

    public MeetingManagementService(
            @Value("${" + APP_PROPERTY_MAX_MEETING_DURATION_IN_MINUTES + "}") long maximalMeetingDurationInMinutes,
            IMeetingManagementRepository meetingManagementRepository,
            IWorkgroupManagementApiClient workgroupManagementApiClient) {
        this.MAXIMAL_MEETING_DURATION = Duration.ofMinutes(maximalMeetingDurationInMinutes);
        this.meetingManagementRepository = meetingManagementRepository;
        this.workgroupManagementApiClient = workgroupManagementApiClient;
    }

    @Override
    public Set<IMeeting> getMeetings() {
        return meetingManagementRepository.findAll();
    }

    @Override
    public Optional<IMeeting> getMeeting(String meetingId) {
        return meetingManagementRepository.findOne(meetingId);
    }

    @Override
    public CreationResult createMeeting(IMeeting meeting) {
        return meetingManagementRepository.create(meeting);
    }

    @Override
    public DeletionResult deleteMeeting(String meetingId) {
        return meetingManagementRepository.delete(meetingId);
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
