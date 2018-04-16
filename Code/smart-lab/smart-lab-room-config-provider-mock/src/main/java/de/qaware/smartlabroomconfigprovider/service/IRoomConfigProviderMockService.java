package de.qaware.smartlabroomconfigprovider.service;

import de.qaware.smartlabcommons.data.room.Room;

import java.util.List;
import java.util.Optional;

public interface IRoomConfigProviderMockService {

    List<Room> getRooms();
    Optional<Room> getRoom(long roomId);
    boolean createRoom(Room room);
    boolean deleteRoom(long roomId);
}
