package de.qaware.smartlabroom.business;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.result.ExtensionResult;
import de.qaware.smartlabcore.generic.business.IEntityManagementBusinessLogic;
import org.springframework.ui.Model;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface IRoomManagementBusinessLogic extends IEntityManagementBusinessLogic<IRoom> {

    Optional<Set<IMeeting>> getMeetingsInRoom(String roomId);
    Optional<IMeeting> getCurrentMeeting(String roomId);
    ExtensionResult extendCurrentMeeting(String roomId, Duration extension);
    String getCurrentMeetingStatusPage(String roomId, Model model);
}
