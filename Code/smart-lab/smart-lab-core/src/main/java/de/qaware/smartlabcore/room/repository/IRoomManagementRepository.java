package de.qaware.smartlabcore.room.repository;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcore.generic.repository.IEntityManagementRepository;
import de.qaware.smartlabcommons.result.ExtensionResult;
import lombok.NonNull;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface IRoomManagementRepository extends IEntityManagementRepository<IRoom> {

    Set<IMeeting> getMeetingsInRoom(@NonNull IRoom room);
    Optional<IMeeting> getCurrentMeeting(@NonNull IRoom room);
    ExtensionResult extendCurrentMeeting(@NonNull IRoom room, Duration extension);
}
