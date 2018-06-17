package de.qaware.smartlabapi.service.room;

import de.qaware.smartlabapi.service.generic.IBasicEntityManagementService;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.room.RoomId;

import java.time.Duration;
import java.util.Set;

public interface IRoomManagementService extends IBasicEntityManagementService<IRoom, RoomId> {

    Set<IMeeting> getMeetingsInRoom(RoomId roomId);
    IMeeting getCurrentMeeting(RoomId roomId);
    void extendCurrentMeeting(RoomId roomId, Duration extension);
}
