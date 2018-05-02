package de.qaware.smartlabcore.meeting.business;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcore.generic.result.ExtensionResult;
import de.qaware.smartlabcore.generic.result.ShiftResult;
import de.qaware.smartlabcore.generic.result.ShorteningResult;
import de.qaware.smartlabcore.generic.business.AbstractEntityManagementBusinessLogic;
import de.qaware.smartlabcore.meeting.repository.IMeetingManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Slf4j
public class MeetingManagementBusinessLogic extends AbstractEntityManagementBusinessLogic<IMeeting> implements IMeetingManagementBusinessLogic {

    private static final String APP_PROPERTY_MAX_MEETING_DURATION_IN_MINUTES = "meeting.max.duration.in.minutes";
    public static Duration MAXIMAL_MEETING_DURATION;
    private final IMeetingManagementRepository meetingManagementRepository;

    public MeetingManagementBusinessLogic(
            @Value("${" + APP_PROPERTY_MAX_MEETING_DURATION_IN_MINUTES + "}") long maximalMeetingDurationInMinutes,
            IMeetingManagementRepository meetingManagementRepository) {
        super(meetingManagementRepository);
        MAXIMAL_MEETING_DURATION = Duration.ofMinutes(maximalMeetingDurationInMinutes);
        this.meetingManagementRepository = meetingManagementRepository;
    }

    @Override
    public ShorteningResult shortenMeeting(String meetingId, Duration shortening) {
        return findOne(meetingId).map(meeting -> {
            Duration shortenedDuration = meeting.getDuration().minus(shortening);
            if(shortenedDuration.isNegative() || shortenedDuration.isZero()) {
                return ShorteningResult.MINIMUM_REACHED;
            }
            return meetingManagementRepository.shortenMeeting(meeting, shortening);
        }).orElse(ShorteningResult.NOT_FOUND);
    }

    @Override
    public ExtensionResult extendMeeting(String meetingId, Duration extension) {
        return findOne(meetingId).map(meeting -> {
            if(meeting.getDuration().plus(extension).compareTo(MAXIMAL_MEETING_DURATION) > 0) {
                return ExtensionResult.MAXIMUM_REACHED_REACHED;
            }
            return meetingManagementRepository.extendMeeting(meeting, extension);
        }).orElse(ExtensionResult.NOT_FOUND);
    }

    @Override
    public ShiftResult shiftMeeting(String meetingId, Duration shift) {
        return findOne(meetingId)
                .map(meeting -> meetingManagementRepository.shiftMeeting(meeting, shift))
                .orElse(ShiftResult.NOT_FOUND);
    }
}
