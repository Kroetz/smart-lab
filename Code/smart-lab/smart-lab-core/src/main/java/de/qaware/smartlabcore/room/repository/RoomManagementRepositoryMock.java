package de.qaware.smartlabcore.room.repository;

import de.qaware.smartlabcommons.api.client.IMeetingManagementApiClient;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcore.data.sample.provider.ISampleDataProvider;
import de.qaware.smartlabcore.generic.result.CreationResult;
import de.qaware.smartlabcore.generic.result.DeletionResult;
import de.qaware.smartlabcore.generic.result.ExtensionResult;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class RoomManagementRepositoryMock implements IRoomManagementRepository {

    private final IMeetingManagementApiClient meetingManagementApiClient;
    private List<IRoom> rooms;

    public RoomManagementRepositoryMock(
            IMeetingManagementApiClient meetingManagementApiClient,
            ISampleDataProvider sampleDataProvider) {
        this.meetingManagementApiClient = meetingManagementApiClient;
        this.rooms = new ArrayList<>(sampleDataProvider.getRooms());
        sortRoomsById();
    }

    private boolean exists(String roomId) {
        return rooms.stream()
                .anyMatch(room -> room.getId().equals(roomId));
    }

    @Override
    public List<IRoom> getRooms() {
        return this.rooms;
    }

    @Override
    public Optional<IRoom> getRoom(String roomId) {
        return rooms.stream()
                .filter(room -> room.getId().equals(roomId))
                .findFirst();
    }

    @Override
    public CreationResult createRoom(IRoom room) {
        if (exists(room.getId())) {
            return CreationResult.CONFLICT;
        }
        if(rooms.add(room)) {
            return CreationResult.SUCCESS;
        }
        return CreationResult.ERROR;
    }

    @Override
    public DeletionResult deleteRoom(String roomId) {
        val roomsToDelete = rooms.stream()
                .filter(room -> room.getId().equals(roomId))
                .collect(Collectors.toList());
        if(roomsToDelete.isEmpty()) {
            return DeletionResult.NOT_FOUND;
        }
        val deleted = rooms.removeAll(roomsToDelete);
        if(deleted) {
            return DeletionResult.SUCCESS;
        }
        return DeletionResult.ERROR;
    }

    @Override
    public List<IMeeting> getMeetingsInRoom(String roomId) {
        return meetingManagementApiClient.getMeetings().stream()
                .filter(meeting -> meeting.getRoomId().equals(roomId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<IMeeting> getCurrentMeeting(String roomId) {
        return getMeetingsInRoom(roomId).stream()
                .filter(meeting -> meeting.getStart().isBefore(Instant.now()) && meeting.getEnd().isAfter(Instant.now()))
                .findFirst();
    }

    @Override
    public ExtensionResult extendCurrentMeeting(String roomId, Duration extension) {
        return getCurrentMeeting(roomId)
                .map(meeting -> ExtensionResult.fromHttpStatus(meetingManagementApiClient.extendMeeting(meeting.getId(), extension.toMinutes()).getStatusCode()))
                .orElse(ExtensionResult.NOT_FOUND);
    }

    private void sortRoomsById() {
        rooms.sort(Comparator.comparing(IRoom::getId));
    }
}
