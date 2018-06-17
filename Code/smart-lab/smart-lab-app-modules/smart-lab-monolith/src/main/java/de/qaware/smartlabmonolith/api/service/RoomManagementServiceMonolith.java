package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabapi.service.room.IRoomManagementService;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabroom.controller.RoomManagementController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class RoomManagementServiceMonolith extends AbstractEntityManagementServiceMonolith<IRoom, RoomId> implements IRoomManagementService {

    private final RoomManagementController roomManagementController;

    public RoomManagementServiceMonolith(RoomManagementController roomManagementController) {
        super(roomManagementController);
        this.roomManagementController = roomManagementController;
    }

    @Override
    public Set<IMeeting> getMeetingsInRoom(RoomId roomId) {
        return this.roomManagementController.getMeetingsInRoom(roomId.getIdValue()).getBody();
    }

    @Override
    public IMeeting getCurrentMeeting(RoomId roomId) {
        return this.roomManagementController.getCurrentMeeting(roomId.getIdValue()).getBody();
    }

    @Override
    public void extendCurrentMeeting(RoomId roomId, Duration extension) {
        this.roomManagementController.extendCurrentMeeting(roomId.getIdValue(), extension.toMinutes());
    }
}
