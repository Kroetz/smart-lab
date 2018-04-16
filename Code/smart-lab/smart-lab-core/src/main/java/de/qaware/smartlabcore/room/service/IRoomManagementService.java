package de.qaware.smartlabcore.room.service;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.Room;
import org.springframework.ui.Model;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public interface IRoomManagementService {

    List<Room> getRooms();
    Optional<Room> getRoom(long roomId);

    boolean createRoom(Room room);

    void deleteRoom(long roomId);

    List<IMeeting> getMeetingsInRoom(long roomId);

    Optional<IMeeting> getCurrentMeeting(long roomId);

    boolean extendCurrentMeeting(long roomId, Duration extension);

    String getCurrentMeetingStatusPage(long roomId, Model model);
}
