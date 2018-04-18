package de.qaware.smartlabcore.room.repository;

import de.qaware.smartlabcommons.api.client.IMeetingManagementApiClient;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcore.data.sample.ISampleDataFactory;
import lombok.extern.slf4j.Slf4j;
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
            ISampleDataFactory coastGuardDataFactory,
            ISampleDataFactory forestRangersDataFactory,
            ISampleDataFactory fireFightersDataFactory) {
        this.meetingManagementApiClient = meetingManagementApiClient;
        this.rooms = new ArrayList<>();
        this.rooms.addAll(coastGuardDataFactory.createRooms());
        this.rooms.addAll(forestRangersDataFactory.createRooms());
        this.rooms.addAll(fireFightersDataFactory.createRooms());
        sortRoomsById();
    }

    private boolean exists(long roomId) {
        return rooms.stream()
                .anyMatch(room -> room.getId() == roomId);
    }

    @Override
    public List<IRoom> getRooms() {
        return this.rooms;
    }

    @Override
    public Optional<IRoom> getRoom(long roomId) {
        return rooms.stream()
                .filter(room -> room.getId() == roomId)
                .findFirst();
    }

    @Override
    public boolean createRoom(IRoom room) {
        return !exists(room.getId()) && rooms.add(room);
    }

    @Override
    public boolean deleteRoom(long roomId) {
        return rooms.removeAll(rooms.stream()
                .filter(room -> room.getId() == roomId)
                .collect(Collectors.toList()));
    }

    @Override
    public List<IMeeting> getMeetingsInRoom(long roomId) {
        return meetingManagementApiClient.getMeetings().stream()
                .filter(meeting -> meeting.getRoomId() == roomId)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<IMeeting> getCurrentMeeting(long roomId) {
        return getMeetingsInRoom(roomId).stream()
                .filter(meeting -> meeting.getStart().isBefore(Instant.now()) && meeting.getEnd().isAfter(Instant.now()))
                .findFirst();
    }

    @Override
    public boolean extendCurrentMeeting(long roomId, Duration extension) {
        return getCurrentMeeting(roomId)
                .map(meeting -> meetingManagementApiClient.extendMeeting(meeting.getId(), extension.toMinutes()))
                .orElse(false);
    }

    private void sortRoomsById() {
        rooms.sort(Comparator.comparingLong(IRoom::getId));
    }
}
