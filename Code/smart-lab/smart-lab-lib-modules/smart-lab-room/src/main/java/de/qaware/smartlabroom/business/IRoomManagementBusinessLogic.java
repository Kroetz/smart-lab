package de.qaware.smartlabroom.business;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.generic.business.IEntityManagementBusinessLogic;
import de.qaware.smartlabcore.result.ExtensionResult;
import org.springframework.ui.Model;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface IRoomManagementBusinessLogic extends IEntityManagementBusinessLogic<IRoom, RoomId> {

    Optional<Set<IMeeting>> getMeetingsInRoom(RoomId roomId);
    Optional<IMeeting> getCurrentMeeting(RoomId roomId);
    ExtensionResult extendCurrentMeeting(RoomId roomId, Duration extension);
    String getCurrentMeetingStatusPage(RoomId roomId, Model model);
}
