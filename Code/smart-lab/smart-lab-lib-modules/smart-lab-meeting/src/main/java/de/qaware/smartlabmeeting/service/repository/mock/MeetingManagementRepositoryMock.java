package de.qaware.smartlabmeeting.service.repository.mock;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.exception.EntityConflictException;
import de.qaware.smartlabcore.exception.EntityCreationException;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabcore.result.DeletionResult;
import de.qaware.smartlabcore.result.ExtensionResult;
import de.qaware.smartlabcore.result.ShiftResult;
import de.qaware.smartlabcore.result.ShorteningResult;
import de.qaware.smartlabmeeting.service.repository.generic.AbstractMeetingManagementRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Repository
@ConditionalOnProperty(
        prefix = Property.Prefix.MEETING_MANAGEMENT_REPOSITORY,
        name = Property.Name.MEETING_MANAGEMENT_REPOSITORY,
        havingValue = Property.Value.MeetingManagementRepository.MOCK)
@Slf4j
public class MeetingManagementRepositoryMock extends AbstractMeetingManagementRepository {

    private Map<RoomId, Set<IMeeting>> meetingsByRoom;

    public MeetingManagementRepositoryMock(Set<IMeeting> initialMeetings) {
        super(initialMeetings);
        this.meetingsByRoom = new HashMap<>();
    }

    @Override
    public Set<IMeeting> findAll() {
        Set<IMeeting> allMeetings = new HashSet<>();
        this.meetingsByRoom.keySet()
                .forEach(roomId -> allMeetings.addAll(this.meetingsByRoom.get(roomId)));
        return allMeetings;
    }

    @Override
    public Set<IMeeting> findAll(RoomId roomId) {
        Set<IMeeting> meetings = this.meetingsByRoom.get(roomId);
        return isNull(meetings) ? new HashSet<>() : meetings;
    }

    @Override
    public Optional<IMeeting> findOne(MeetingId meetingId) {
        Set<IMeeting> meetingsInRoom = this.meetingsByRoom.get(meetingId.getLocationIdPart());
        return isNull(meetingsInRoom) ? Optional.empty() : meetingsInRoom.stream()
                .filter(entity -> entity.getId().equals(meetingId))
                .findFirst();
    }

    @Override
    public Map<MeetingId, Optional<IMeeting>> findMultiple(Set<MeetingId> meetingIds) {
        Map<MeetingId, Optional<IMeeting>> meetingsById = new HashMap<>();
        meetingIds.forEach(meetingId -> meetingsById.put(meetingId, findOne(meetingId)));
        return meetingsById;
    }

    @Override
    public synchronized IMeeting create(IMeeting meeting) {
        boolean meetingCollision = findAll(meeting.getRoomId()).stream().anyMatch(m -> areMeetingsColliding(meeting, m));
        if(meetingCollision || exists(meeting.getId())) {
            log.error("Cannot create meeting {} because a meeting with that ID already exists", meeting);
            // TODO: Meaningful exception message
            throw new EntityConflictException();
        }
        Set<IMeeting> meetingsInRoom = this.meetingsByRoom.get(meeting.getRoomId());
        if(isNull(meetingsInRoom)) {
            meetingsInRoom = new HashSet<>();
            this.meetingsByRoom.put(meeting.getRoomId(), meetingsInRoom);
        }
        if(meetingsInRoom.add(meeting)) {
            return meeting;
        }
        log.error("Cannot create meeting {} because of unknown reason", meeting);
        // TODO: Meaningful exception message
        throw new EntityCreationException();
    }

    @Override
    public synchronized DeletionResult delete(MeetingId meetingId) {
        Set<IMeeting> meetingsInRoom = this.meetingsByRoom.get(meetingId.getLocationIdPart());
        List<IMeeting> meetingsToDelete = isNull(meetingsInRoom) ? new ArrayList<>() : meetingsInRoom.stream()
                .filter(meeting -> meeting.getId().equals(meetingId))
                .collect(Collectors.toList());
        if(isNull(meetingsInRoom) || meetingsToDelete.isEmpty()) {
            return DeletionResult.NOT_FOUND;
        }
        boolean deleted =  meetingsInRoom.removeAll(meetingsToDelete);
        if(deleted) {
            return DeletionResult.SUCCESS;
        }
        return DeletionResult.ERROR;
    }

    @Override
    public synchronized ShorteningResult shortenMeeting(@NonNull IMeeting meeting, Duration shortening) {
        if(delete(meeting.getId()) == DeletionResult.SUCCESS) {
            IMeeting shortenedMeeting = meeting.copy();
            shortenedMeeting.setEnd(meeting.getEnd().minus(shortening));
            try {
                create(shortenedMeeting);
                return ShorteningResult.SUCCESS;
            }
            catch(Exception e) {
                return ShorteningResult.ERROR;
            }
        }
        return ShorteningResult.ERROR;
    }

    @Override
    public synchronized ExtensionResult extendMeeting(@NonNull IMeeting meeting, Duration extension) {
        IMeeting extendedMeeting = meeting.copy();
        extendedMeeting.setEnd(meeting.getEnd().plus(extension));
        if(delete(meeting.getId()) == DeletionResult.SUCCESS) {
            try {
                create(extendedMeeting);
                return ExtensionResult.SUCCESS;
            }
            catch(EntityConflictException e) {
                create(meeting);
                return ExtensionResult.CONFLICT;
            }
            catch(Exception e) {
                create(meeting);
                return ExtensionResult.ERROR;
            }
        }
        return ExtensionResult.ERROR;
    }

    @Override
    public synchronized ShiftResult shiftMeeting(@NonNull IMeeting meeting, Duration shift) {
        IMeeting shiftedMeeting = meeting.copy();
        shiftedMeeting.setStart(meeting.getStart().plus(shift));
        shiftedMeeting.setEnd(meeting.getEnd().plus(shift));
        if(delete(meeting.getId()) == DeletionResult.SUCCESS) {
            try {
                create(shiftedMeeting);
                return ShiftResult.SUCCESS;
            }
            catch(EntityConflictException e) {
                create(meeting);
                return ShiftResult.CONFLICT;
            }
            catch(Exception e) {
                create(meeting);
                return ShiftResult.ERROR;
            }
        }
        return ShiftResult.ERROR;
    }
}
