package de.qaware.smartlabcore.room.repository;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public interface IRoomManagementRepository {

    List<IRoom> getRooms();
    Optional<IRoom> getRoom(long roomId);

    boolean createRoom(IRoom room);

    boolean deleteRoom(long roomId);

    List<IMeeting> getMeetingsInRoom(long roomId);

    Optional<IMeeting> getCurrentMeeting(long roomId);

    boolean extendCurrentMeeting(long roomId, Duration extension);
}
