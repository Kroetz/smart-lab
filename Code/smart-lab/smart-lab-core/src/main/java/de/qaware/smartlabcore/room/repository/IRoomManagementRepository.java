package de.qaware.smartlabcore.room.repository;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.Room;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public interface IRoomManagementRepository {

    List<Room> getRooms();
    Optional<Room> getRoom(long roomId);

    boolean createRoom(Room room);

    void deleteRoom(long roomId);

    List<IMeeting> getMeetingsInRoom(long roomId);

    Optional<IMeeting> getCurrentMeeting(long roomId);

    boolean extendCurrentMeeting(long roomId, Duration extension);
}
