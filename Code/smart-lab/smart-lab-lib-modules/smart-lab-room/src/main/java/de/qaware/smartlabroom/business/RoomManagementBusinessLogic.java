package de.qaware.smartlabroom.business;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.generic.business.AbstractBasicEntityManagementBusinessLogic;
import de.qaware.smartlabcore.result.ExtensionResult;
import de.qaware.smartlabroom.repository.IRoomManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class RoomManagementBusinessLogic extends AbstractBasicEntityManagementBusinessLogic<IRoom, RoomId> implements IRoomManagementBusinessLogic {

    private final IRoomManagementRepository roomManagementRepository;

    public RoomManagementBusinessLogic(IRoomManagementRepository roomManagementRepository) {
        super(roomManagementRepository);
        this.roomManagementRepository = roomManagementRepository;
    }

    @Override
    public Optional<Set<IMeeting>> getMeetingsInRoom(RoomId roomId) {
        return this.roomManagementRepository.findOne(roomId)
                .map(room -> Optional.of(this.roomManagementRepository.getMeetingsInRoom(room)))
                .orElse(Optional.empty());
    }

    @Override
    public Optional<IMeeting> getCurrentMeeting(RoomId roomId) {
        return this.roomManagementRepository.findOne(roomId)
                .map(this.roomManagementRepository::getCurrentMeeting)
                .orElse(Optional.empty());
    }

    @Override
    public ExtensionResult extendCurrentMeeting(RoomId roomId, Duration extension) {
        return this.roomManagementRepository.findOne(roomId)
                .map(room -> this.roomManagementRepository.extendCurrentMeeting(room, extension))
                .orElse(ExtensionResult.NOT_FOUND);
    }
}
