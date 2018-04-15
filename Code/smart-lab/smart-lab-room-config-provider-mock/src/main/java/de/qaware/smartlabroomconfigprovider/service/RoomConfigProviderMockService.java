package de.qaware.smartlabroomconfigprovider.service;

import de.qaware.smartlabcommons.data.room.Room;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomConfigProviderMockService implements IRoomConfigProviderMockService {

    private List<Room> rooms;

    public RoomConfigProviderMockService() {
        this.rooms = new ArrayList<>();

        val blueRoomDevices = new ArrayList<Long>();
        blueRoomDevices.add(0L);
        rooms.add(Room.builder()
                .id(0)
                .name("Room Blue")
                .deviceIds(blueRoomDevices)
                .build());

        val greenRoomDevices = new ArrayList<Long>();
        greenRoomDevices.add(1L);
        rooms.add(Room.builder()
                .id(1)
                .name("Room Green")
                .deviceIds(greenRoomDevices)
                .build());

        val redRoomDevices = new ArrayList<Long>();
        redRoomDevices.add(2L);
        redRoomDevices.add(3L);
        rooms.add(Room.builder()
                .id(2)
                .name("Room Red")
                .deviceIds(redRoomDevices)
                .build());

        sortRoomsById();
    }

    @Override
    public boolean exists(long roomId) {
        return rooms.stream()
                .anyMatch(room -> room.getId() == roomId);
    }

    @Override
    public List<Room> getRooms() {
        return this.rooms;
    }

    @Override
    public Optional<Room> getRoom(long roomId) {
        return rooms.stream()
                .filter(room -> room.getId() == roomId)
                .findFirst();
    }

    @Override
    public boolean createRoom(Room room) {
        return !exists(room.getId()) && rooms.add(room);
    }

    @Override
    public boolean deleteRoom(long roomId) {
        return rooms.removeAll(rooms.stream()
                .filter(room -> room.getId() == roomId)
                .collect(Collectors.toList()));
    }

    private void sortRoomsById() {
        rooms.sort(Comparator.comparingLong(Room::getId));
    }
}
