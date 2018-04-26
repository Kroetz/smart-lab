package de.qaware.smartlabcore.meeting.service;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcore.generic.result.ExtensionResult;
import de.qaware.smartlabcore.generic.result.ShiftResult;
import de.qaware.smartlabcore.generic.result.ShorteningResult;
import de.qaware.smartlabcore.generic.service.AbstractEntityManagementService;
import de.qaware.smartlabcore.meeting.repository.IMeetingManagementRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Slf4j
public class MeetingManagementService extends AbstractEntityManagementService<IMeeting> implements IMeetingManagementService {

    private static final String APP_PROPERTY_MAX_MEETING_DURATION_IN_MINUTES = "meeting.max.duration.in.minutes";
    public static Duration MAXIMAL_MEETING_DURATION;
    private final IMeetingManagementRepository meetingManagementRepository;

    public MeetingManagementService(
            @Value("${" + APP_PROPERTY_MAX_MEETING_DURATION_IN_MINUTES + "}") long maximalMeetingDurationInMinutes,
            IMeetingManagementRepository meetingManagementRepository) {
        super(meetingManagementRepository);
        MAXIMAL_MEETING_DURATION = Duration.ofMinutes(maximalMeetingDurationInMinutes);
        this.meetingManagementRepository = meetingManagementRepository;
    }

    @Override
    public ShorteningResult shortenMeeting(String meetingId, Duration shortening) {
        return findOne(meetingId).map(meeting -> {
            val shortenedDuration = meeting.getDuration().minus(shortening);
            if(shortenedDuration.isNegative() || shortenedDuration.isZero()) {
                return ShorteningResult.MINIMUM_REACHED;
            }
            return meetingManagementRepository.shortenMeeting(meetingId, shortening);
        }).orElse(ShorteningResult.NOT_FOUND);
    }

    @Override
    public ExtensionResult extendMeeting(String meetingId, Duration extension) {
        return findOne(meetingId).map(meeting -> {
            if(meeting.getDuration().plus(extension).compareTo(MAXIMAL_MEETING_DURATION) > 0) {
                return ExtensionResult.MAXIMUM_REACHED_REACHED;
            }
            return meetingManagementRepository.extendMeeting(meetingId, extension);
        }).orElse(ExtensionResult.NOT_FOUND);
    }

    @Override
    public ShiftResult shiftMeeting(String meetingId, Duration shift) {
        return findOne(meetingId)
                .map(meeting -> meetingManagementRepository.shiftMeeting(meetingId, shift))
                .orElse(ShiftResult.NOT_FOUND);
    }
}
