package de.qaware.smartlabcore.room.service;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcore.generic.result.CreationResult;
import de.qaware.smartlabcore.generic.result.DeletionResult;
import de.qaware.smartlabcore.generic.result.ExtensionResult;
import org.springframework.ui.Model;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface IRoomManagementService {

    Set<IRoom> getRooms();
    Optional<IRoom> getRoom(String roomId);

    CreationResult createRoom(IRoom room);

    DeletionResult deleteRoom(String roomId);

    Optional<Set<IMeeting>> getMeetingsInRoom(String roomId);

    Optional<IMeeting> getCurrentMeeting(String roomId);

    ExtensionResult extendCurrentMeeting(String roomId, Duration extension);

    String getCurrentMeetingStatusPage(String roomId, Model model);
}
