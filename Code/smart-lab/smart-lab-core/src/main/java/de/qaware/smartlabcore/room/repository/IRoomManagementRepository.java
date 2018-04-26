package de.qaware.smartlabcore.room.repository;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcore.generic.repository.IEntityManagementRepository;
import de.qaware.smartlabcore.generic.result.ExtensionResult;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface IRoomManagementRepository extends IEntityManagementRepository<IRoom> {

    Optional<Set<IMeeting>> getMeetingsInRoom(String roomId);
    Optional<IMeeting> getCurrentMeeting(String roomId);
    ExtensionResult extendCurrentMeeting(String roomId, Duration extension);
}
