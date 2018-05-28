package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabcommons.api.internal.service.room.IRoomManagementService;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.miscellaneous.Property;
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
public class RoomManagementServiceMonolith extends AbstractEntityManagementServiceMonolith<IRoom> implements IRoomManagementService {

    private final RoomManagementController roomManagementController;

    public RoomManagementServiceMonolith(RoomManagementController roomManagementController) {
        super(roomManagementController);
        this.roomManagementController = roomManagementController;
    }

    @Override
    public Set<IMeeting> getMeetingsInRoom(String roomId) {
        return this.roomManagementController.getMeetingsInRoom(roomId).getBody();
    }

    @Override
    public IMeeting getCurrentMeeting(String roomId) {
        return this.roomManagementController.getCurrentMeeting(roomId).getBody();
    }

    @Override
    public void extendCurrentMeeting(String roomId, Duration extension) {
        this.roomManagementController.extendCurrentMeeting(roomId, extension.toMinutes());
    }
}
