package de.qaware.smartlabmeeting.business;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.room.RoomId;
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
public class MeetingManagementBusinessLogic implements IMeetingManagementBusinessLogic {

    private final IMeetingManagementRepository meetingManagementRepository;

    public MeetingManagementBusinessLogic(IMeetingManagementRepository meetingManagementRepository) {
        this.meetingManagementRepository = meetingManagementRepository;
    }

    @Override
    public Map<RoomId, Set<IMeeting>> findAll() {
        return this.meetingManagementRepository.findAll();
    }

    @Override
    public Set<IMeeting> findAll(RoomId roomId) {
        return this.meetingManagementRepository.findAll(roomId);
    }

    @Override
    public Optional<IMeeting> findOne(MeetingId meetingId, RoomId roomId) {
        return this.meetingManagementRepository.findOne(meetingId, roomId);
    }

    @Override
    public Map<MeetingId, Optional<IMeeting>> findMultiple(Set<MeetingId> meetingIds, RoomId roomId) {
        return this.meetingManagementRepository.findMultiple(meetingIds, roomId);
    }

    @Override
    public CreationResult create(IMeeting meeting) {
        return this.meetingManagementRepository.create(meeting);
    }

    @Override
    public DeletionResult delete(MeetingId meetingId, RoomId roomId) {
        return this.meetingManagementRepository.delete(meetingId, roomId);
    }

    @Override
    public ShorteningResult shortenMeeting(
            MeetingId meetingId,
            RoomId roomId,
            Duration shortening) {
        return findOne(meetingId, roomId).map(meeting -> {
            Duration shortenedDuration = meeting.getDuration().minus(shortening);
            if(shortenedDuration.isNegative() || shortenedDuration.isZero()) {
                return ShorteningResult.MINIMUM_REACHED;
            }
            return meetingManagementRepository.shortenMeeting(meeting, shortening);
        }).orElse(ShorteningResult.NOT_FOUND);
    }

    @Override
    public ExtensionResult extendMeeting(
            MeetingId meetingId,
            RoomId roomId,
            Duration extension) {
        return findOne(meetingId, roomId).map(meeting -> {
            if(meeting.getDuration().plus(extension).compareTo(Constants.MAXIMAL_MEETING_DURATION) > 0) {
                return ExtensionResult.MAXIMUM_REACHED_REACHED;
            }
            return meetingManagementRepository.extendMeeting(meeting, extension);
        }).orElse(ExtensionResult.NOT_FOUND);
    }

    @Override
    public ShiftResult shiftMeeting(
            MeetingId meetingId,
            RoomId roomId,
            Duration shift) {
        return findOne(meetingId, roomId)
                .map(meeting -> meetingManagementRepository.shiftMeeting(meeting, shift))
                .orElse(ShiftResult.NOT_FOUND);
    }
}
