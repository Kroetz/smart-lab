package de.qaware.smartlabroom.repository;

import de.qaware.smartlabapi.service.meeting.IMeetingManagementService;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.result.ExtensionResult;
import de.qaware.smartlabcore.generic.repository.AbstractBasicEntityManagementRepositoryMock;
import de.qaware.smartlabcore.data.ISampleDataProvider;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class RoomManagementRepositoryMock extends AbstractBasicEntityManagementRepositoryMock<IRoom, RoomId> implements IRoomManagementRepository {

    private final IMeetingManagementService meetingManagementService;

    public RoomManagementRepositoryMock(
            IMeetingManagementService meetingManagementService,
            Set<IRoom> initialRooms) {
        super(initialRooms);
        this.meetingManagementService = meetingManagementService;
        this.entities = new HashSet<>();
    }

    @Override
    public Set<IMeeting> getMeetingsInRoom(@NonNull IRoom room) {
        return this.meetingManagementService.findAll().stream()
                .filter(meeting -> meeting.getRoomId().equals(room.getId()))
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<IMeeting> getCurrentMeeting(@NonNull IRoom room) {
        return getMeetingsInRoom(room).stream()
                .filter(meeting -> meeting.getStart().isBefore(Instant.now()) && meeting.getEnd().isAfter(Instant.now()))
                .findFirst();
    }

    @Override
    public ExtensionResult extendCurrentMeeting(@NonNull IRoom room, Duration extension) {
        try {
            return getCurrentMeeting(room)
                    .map(meeting -> {
                        this.meetingManagementService.extendMeeting(meeting.getId(), extension);
                        return ExtensionResult.SUCCESS;})
                    .orElse(ExtensionResult.NOT_FOUND);
        }
        catch(Exception e) {
            return ExtensionResult.fromException(e);
        }
    }
}
