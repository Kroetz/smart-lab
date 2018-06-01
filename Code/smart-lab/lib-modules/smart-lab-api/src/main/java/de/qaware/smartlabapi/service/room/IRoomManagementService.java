package de.qaware.smartlabapi.service.room;

import de.qaware.smartlabapi.service.generic.IEntityManagementService;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;

import java.time.Duration;
import java.util.Set;

public interface IRoomManagementService extends IEntityManagementService<IRoom> {

    Set<IMeeting> getMeetingsInRoom(String roomId);
    IMeeting getCurrentMeeting(String roomId);
    void extendCurrentMeeting(String roomId, Duration extension);
}