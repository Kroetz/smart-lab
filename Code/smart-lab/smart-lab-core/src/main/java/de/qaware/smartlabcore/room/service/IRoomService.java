package de.qaware.smartlabcore.room.service;

import de.qaware.smartlabcore.entity.meeting.IMeeting;
import de.qaware.smartlabcore.entity.room.Room;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

public interface IRoomService {

    List<Room> getRooms();
    Optional<Room> getRoom(long roomId);
    Optional<IMeeting> getCurrentMeeting(long roomId);

    boolean createRoom(Room room);

    void deleteRoom(long roomId);

    String getCurrentMeetingStatusPage(long roomId, Model model);

    void extendCurrentMeeting(long roomId, long extensionInMinutes);
}
