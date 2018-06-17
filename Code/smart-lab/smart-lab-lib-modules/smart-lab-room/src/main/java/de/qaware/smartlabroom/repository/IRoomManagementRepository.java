package de.qaware.smartlabroom.repository;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.result.ExtensionResult;
import de.qaware.smartlabcore.generic.repository.IBasicEntityManagementRepository;
import lombok.NonNull;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface IRoomManagementRepository extends IBasicEntityManagementRepository<IRoom, RoomId> {

    Set<IMeeting> getMeetingsInRoom(@NonNull IRoom room);
    Optional<IMeeting> getCurrentMeeting(@NonNull IRoom room);
    ExtensionResult extendCurrentMeeting(@NonNull IRoom room, Duration extension);
}
