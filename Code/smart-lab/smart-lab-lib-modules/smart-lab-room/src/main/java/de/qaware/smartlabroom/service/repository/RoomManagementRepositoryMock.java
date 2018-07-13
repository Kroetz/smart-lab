package de.qaware.smartlabroom.service.repository;

import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.service.repository.AbstractBasicEntityManagementRepositoryMock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
@Slf4j
public class RoomManagementRepositoryMock extends AbstractBasicEntityManagementRepositoryMock<IRoom, RoomId> implements IRoomManagementRepository {

    public RoomManagementRepositoryMock(Set<IRoom> initialRooms) {
        super(initialRooms);
        this.entities = new HashSet<>();
    }
}
