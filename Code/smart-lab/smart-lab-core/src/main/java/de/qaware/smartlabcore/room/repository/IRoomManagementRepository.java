package de.qaware.smartlabcore.room.repository;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public interface IRoomManagementRepository {

    List<IRoom> getRooms();
    Optional<IRoom> getRoom(String roomId);

    boolean createRoom(IRoom room);

    boolean deleteRoom(String roomId);

    List<IMeeting> getMeetingsInRoom(String roomId);

    Optional<IMeeting> getCurrentMeeting(String roomId);

    boolean extendCurrentMeeting(String roomId, Duration extension);
}
