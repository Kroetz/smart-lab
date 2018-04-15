package de.qaware.smartlabcore.room.service;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.Room;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

public interface IRoomManagementService {

    List<Room> getRooms();
    Optional<Room> getRoom(long roomId);
    Optional<IMeeting> getCurrentMeeting(long roomId);

    boolean createRoom(Room room);

    void deleteRoom(long roomId);

    List<IMeeting> getMeetingsInRoom(long roomId);

    String getCurrentMeetingStatusPage(long roomId, Model model);

    boolean extendCurrentMeeting(long roomId, long extensionInMinutes);
}
