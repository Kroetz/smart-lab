package de.qaware.smartlabroomconfigprovidermock.service;

import de.qaware.smartlabcommons.data.room.IRoom;

import java.util.List;
import java.util.Optional;

public interface IRoomConfigProviderMockService {

    List<IRoom> getRooms();
    Optional<IRoom> getRoom(long roomId);
    boolean createRoom(IRoom room);
    boolean deleteRoom(long roomId);
}
