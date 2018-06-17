package de.qaware.smartlabmeeting.repository.mock;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabcore.result.*;
import de.qaware.smartlabmeeting.repository.IMeetingManagementRepository;
import de.qaware.smartlabsampledata.provider.ISampleDataProvider;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@ConditionalOnProperty(
        prefix = Property.Prefix.MEETING_MANAGEMENT_REPOSITORY,
        name = Property.Name.MEETING_MANAGEMENT_REPOSITORY,
        havingValue = Property.Value.MeetingManagementRepository.MOCK)
@Slf4j
public class MeetingManagementRepositoryMock implements IMeetingManagementRepository {

    private Map<RoomId, Set<IMeeting>> meetingsByRoom;

    public MeetingManagementRepositoryMock(ISampleDataProvider sampleDataProvider) {
        this.meetingsByRoom = new HashMap<>(sampleDataProvider.getMeetingsByRoom());
    }

    private boolean exists(MeetingId meetingId, RoomId roomId) {
        Set<IMeeting> meetingsInRoom = this.meetingsByRoom.get(roomId);
        return meetingsInRoom != null && meetingsInRoom
                .stream()
                .anyMatch(meeting -> meeting.getId().equals(meetingId));
    }

    @Override
    public Map<RoomId, Set<IMeeting>> findAll() {
        return this.meetingsByRoom;
    }

    @Override
    public Set<IMeeting> findAll(RoomId roomId) {
        Set<IMeeting> meetings = this.meetingsByRoom.get(roomId);
        return meetings == null ? new HashSet<>() : meetings;
    }

    @Override
    public Optional<IMeeting> findOne(MeetingId meetingId, RoomId roomId) {
        Set<IMeeting> meetingsInRoom = this.meetingsByRoom.get(roomId);
        return meetingsInRoom == null ? Optional.empty() : meetingsInRoom.stream()
                .filter(entity -> entity.getId().equals(meetingId))
                .findFirst();
    }

    @Override
    public Map<MeetingId, Optional<IMeeting>> findMultiple(Set<MeetingId> meetingIds, RoomId roomId) {
        Map<MeetingId, Optional<IMeeting>> meetingsById = new HashMap<>();
        Set<IMeeting> meetingsInRoom = this.meetingsByRoom.get(roomId);
        meetingIds.forEach(meetingId -> meetingsById.put(
                meetingId, meetingsInRoom == null ? Optional.empty() : findOne(meetingId, roomId)));
        return meetingsById;
    }

    @Override
    public CreationResult create(IMeeting meeting) {
        boolean meetingCollision = findAll(meeting.getRoomId()).stream().anyMatch(m -> areMeetingsColliding(meeting, m));
        if(meetingCollision || exists(meeting.getId(), meeting.getRoomId())) {
            return CreationResult.CONFLICT;
        }
        Set<IMeeting> meetingsInRoom = this.meetingsByRoom.get(meeting.getRoomId());
        if(meetingsInRoom != null && meetingsInRoom.add(meeting)) {
            return CreationResult.SUCCESS;
        }
        return CreationResult.ERROR;
    }

    @Override
    public DeletionResult delete(MeetingId meetingId, RoomId roomId) {
        Set<IMeeting> meetingsInRoom = this.meetingsByRoom.get(roomId);
        List<IMeeting> meetingsToDelete = meetingsInRoom == null ? new ArrayList<>() : meetingsInRoom.stream()
                .filter(meeting -> meeting.getId().equals(meetingId))
                .collect(Collectors.toList());
        if(meetingsInRoom == null || meetingsToDelete.isEmpty()) {
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
        if(delete(meeting.getId(), meeting.getRoomId()) == DeletionResult.SUCCESS) {
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
        if(delete(meeting.getId(), meeting.getRoomId()) == DeletionResult.SUCCESS) {
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
        if(delete(meeting.getId(), meeting.getRoomId()) == DeletionResult.SUCCESS) {
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

    private void sortMeetingsByStart(List<IMeeting> meetings) {
        meetings.sort((m1, m2) -> {
            if(m1.getStart().isBefore(m2.getStart())) {
                return -1;
            }
            if(m1.getStart().isAfter(m2.getStart())) {
                return 1;
            }
            return 0;
        });
    }

    private boolean areMeetingsColliding(IMeeting m1, IMeeting m2) {
        return (m1.getRoomId().equals(m2.getRoomId())
                && (m1.getStart().equals(m2.getStart()) && m1.getEnd().equals(m2.getEnd())
                || m1.getStart().isAfter(m2.getStart()) && m1.getStart().isBefore(m2.getEnd())
                || m1.getEnd().isAfter(m2.getStart()) && m1.getEnd().isBefore(m2.getEnd())
                || m2.getStart().isAfter(m1.getStart()) && m2.getStart().isBefore(m1.getEnd())
                || m2.getEnd().isAfter(m1.getStart()) && m2.getEnd().isBefore(m1.getEnd())));
    }
}
