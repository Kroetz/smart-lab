package de.qaware.smartlabmeeting.business;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.generic.business.AbstractBasicEntityManagementBusinessLogic;
import de.qaware.smartlabcore.miscellaneous.Constants;
import de.qaware.smartlabcore.result.*;
import de.qaware.smartlabmeeting.repository.IMeetingManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class MeetingManagementBusinessLogic extends AbstractBasicEntityManagementBusinessLogic<IMeeting, MeetingId> implements IMeetingManagementBusinessLogic {

    private final IMeetingManagementRepository meetingManagementRepository;

    public MeetingManagementBusinessLogic(IMeetingManagementRepository meetingManagementRepository) {
        super(meetingManagementRepository);
        this.meetingManagementRepository = meetingManagementRepository;
    }

    @Override
    public Set<IMeeting> findAll(RoomId roomId) {
        return this.meetingManagementRepository.findAll(roomId);
    }

    @Override
    public ShorteningResult shortenMeeting(
            MeetingId meetingId,
            Duration shortening) {
        return findOne(meetingId).map(meeting -> {
            Duration shortenedDuration = meeting.getDuration().minus(shortening);
            if(shortenedDuration.isNegative() || shortenedDuration.isZero()) {
                return ShorteningResult.MINIMUM_REACHED;
            }
            return this.meetingManagementRepository.shortenMeeting(meeting, shortening);
        }).orElse(ShorteningResult.NOT_FOUND);
    }

    @Override
    public ExtensionResult extendMeeting(
            MeetingId meetingId,
            Duration extension) {
        return findOne(meetingId).map(meeting -> {
            if(meeting.getDuration().plus(extension).compareTo(Constants.MAXIMAL_MEETING_DURATION) > 0) {
                return ExtensionResult.MAXIMUM_REACHED_REACHED;
            }
            return this.meetingManagementRepository.extendMeeting(meeting, extension);
        }).orElse(ExtensionResult.NOT_FOUND);
    }

    @Override
    public ShiftResult shiftMeeting(
            MeetingId meetingId,
            Duration shift) {
        return findOne(meetingId)
                .map(meeting -> this.meetingManagementRepository.shiftMeeting(meeting, shift))
                .orElse(ShiftResult.NOT_FOUND);
    }
}
