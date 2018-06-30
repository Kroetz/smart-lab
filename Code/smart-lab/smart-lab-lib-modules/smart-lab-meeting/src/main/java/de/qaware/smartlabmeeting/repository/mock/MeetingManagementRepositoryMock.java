package de.qaware.smartlabmeeting.repository.mock;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabcore.result.*;
import de.qaware.smartlabmeeting.repository.generic.AbstractMeetingManagementRepository;
import de.qaware.smartlabsampledata.provider.ISampleDataProvider;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Repository
@ConditionalOnProperty(
        prefix = Property.Prefix.MEETING_MANAGEMENT_REPOSITORY,
        name = Property.Name.MEETING_MANAGEMENT_REPOSITORY,
        havingValue = Property.Value.MeetingManagementRepository.MOCK)
@Slf4j
public class MeetingManagementRepositoryMock extends AbstractMeetingManagementRepository {

    private Map<RoomId, Set<IMeeting>> meetingsByRoom;

    public MeetingManagementRepositoryMock(ISampleDataProvider sampleDataProvider) {
        this.meetingsByRoom = new HashMap<>(sampleDataProvider.getMeetingsByRoom());
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
    public CreationResult create(IMeeting meeting) {
        boolean meetingCollision = findAll(meeting.getRoomId()).stream().anyMatch(m -> areMeetingsColliding(meeting, m));
        if(meetingCollision || exists(meeting.getId())) {
            return CreationResult.CONFLICT;
        }
        Set<IMeeting> meetingsInRoom = this.meetingsByRoom.get(meeting.getRoomId());
        if(nonNull(meetingsInRoom) && meetingsInRoom.add(meeting)) {
            return CreationResult.SUCCESS;
        }
        return CreationResult.ERROR;
    }

    @Override
    public DeletionResult delete(MeetingId meetingId) {
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
    public ShorteningResult shortenMeeting(@NonNull IMeeting meeting, Duration shortening) {
        if(delete(meeting.getId()) == DeletionResult.SUCCESS) {
            IMeeting shortenedMeeting = meeting.copy();
            shortenedMeeting.setEnd(meeting.getEnd().minus(shortening));
            CreationResult shortenedMeetingCreated = create(shortenedMeeting);
            if(shortenedMeetingCreated == CreationResult.SUCCESS) {
                return ShorteningResult.SUCCESS;
            }
        }
        return ShorteningResult.ERROR;
    }

    @Override
    public ExtensionResult extendMeeting(@NonNull IMeeting meeting, Duration extension) {
        IMeeting extendedMeeting = meeting.copy();
        extendedMeeting.setEnd(meeting.getEnd().plus(extension));
        if(delete(meeting.getId()) == DeletionResult.SUCCESS) {
            CreationResult extendedMeetingCreated = create(extendedMeeting);
            if(extendedMeetingCreated == CreationResult.CONFLICT) {
                create(meeting);
                return ExtensionResult.CONFLICT;
            }
            else if(extendedMeetingCreated == CreationResult.SUCCESS) {
                return ExtensionResult.SUCCESS;
            }
            else {
                create(meeting);
            }
        }
        return ExtensionResult.ERROR;
    }

    @Override
    public ShiftResult shiftMeeting(@NonNull IMeeting meeting, Duration shift) {
        IMeeting shiftedMeeting = meeting.copy();
        shiftedMeeting.setStart(meeting.getStart().plus(shift));
        shiftedMeeting.setEnd(meeting.getEnd().plus(shift));
        if(delete(meeting.getId()) == DeletionResult.SUCCESS) {
            CreationResult shiftedMeetingCreated = create(shiftedMeeting);
            if(shiftedMeetingCreated == CreationResult.CONFLICT) {
                create(meeting);
                return ShiftResult.CONFLICT;
            }
            else if(shiftedMeetingCreated == CreationResult.SUCCESS) {
                return ShiftResult.SUCCESS;
            }
            else {
                create(meeting);
            }
        }
        return ShiftResult.ERROR;
    }
}
