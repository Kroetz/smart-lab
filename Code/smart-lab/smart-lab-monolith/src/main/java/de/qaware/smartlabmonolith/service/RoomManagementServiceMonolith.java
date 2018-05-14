package de.qaware.smartlabmonolith.service;

import de.qaware.smartlabcommons.api.service.room.IRoomManagementService;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.miscellaneous.Constants;
import de.qaware.smartlabroom.controller.RoomManagementController;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

@Component
@Profile(Constants.PROFILE_NAME_MONOLITH)
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
