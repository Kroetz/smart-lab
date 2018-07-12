package de.qaware.smartlabroom.service.business;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.service.business.IBasicEntityManagementBusinessLogic;
import de.qaware.smartlabcore.result.ExtensionResult;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface IRoomManagementBusinessLogic extends IBasicEntityManagementBusinessLogic<IRoom, RoomId> {

    Optional<Set<IMeeting>> getMeetingsInRoom(RoomId roomId);
    Optional<IMeeting> getCurrentMeeting(RoomId roomId);
    ExtensionResult extendCurrentMeeting(RoomId roomId, Duration extension);
}
