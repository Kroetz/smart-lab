package de.qaware.smartlabcore.room.business;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcore.generic.result.ExtensionResult;
import de.qaware.smartlabcore.generic.service.IEntityManagementService;
import org.springframework.ui.Model;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface IRoomManagementBusinessLogic extends IEntityManagementService<IRoom> {

    Optional<Set<IMeeting>> getMeetingsInRoom(String roomId);
    Optional<IMeeting> getCurrentMeeting(String roomId);
    ExtensionResult extendCurrentMeeting(String roomId, Duration extension);
    String getCurrentMeetingStatusPage(String roomId, Model model);
}
