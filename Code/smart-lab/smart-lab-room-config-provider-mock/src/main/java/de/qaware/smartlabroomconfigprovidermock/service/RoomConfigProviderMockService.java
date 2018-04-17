package de.qaware.smartlabroomconfigprovidermock.service;

import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcore.data.sample.ISampleDataFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomConfigProviderMockService implements IRoomConfigProviderMockService {

    private List<IRoom> rooms;

    public RoomConfigProviderMockService(
            ISampleDataFactory coastGuardDataFactory,
            ISampleDataFactory forestRangersDataFactory,
            ISampleDataFactory fireFightersDataFactory) {
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

    private void sortRoomsById() {
        rooms.sort(Comparator.comparingLong(IRoom::getId));
    }
}
