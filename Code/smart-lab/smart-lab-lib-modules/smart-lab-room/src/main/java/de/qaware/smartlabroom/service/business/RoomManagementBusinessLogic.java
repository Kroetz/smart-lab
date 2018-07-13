package de.qaware.smartlabroom.service.business;

import de.qaware.smartlabapi.service.connector.meeting.IMeetingManagementService;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.service.business.AbstractBasicEntityManagementBusinessLogic;
import de.qaware.smartlabcore.result.ExtensionResult;
import de.qaware.smartlabroom.service.repository.IRoomManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class RoomManagementBusinessLogic extends AbstractBasicEntityManagementBusinessLogic<IRoom, RoomId> implements IRoomManagementBusinessLogic {

    private final IRoomManagementRepository roomManagementRepository;
    private final IMeetingManagementService meetingManagementService;

    public RoomManagementBusinessLogic(
            IRoomManagementRepository roomManagementRepository,
            IMeetingManagementService meetingManagementService) {
        super(roomManagementRepository);
        this.roomManagementRepository = roomManagementRepository;
        this.meetingManagementService = meetingManagementService;
    }

    @Override
    public Optional<Set<IMeeting>> getMeetingsInRoom(RoomId roomId) {
        return this.roomManagementRepository.findOne(roomId)
                .map(room -> Optional.of(this.meetingManagementService.findAll(roomId)))
                .orElse(Optional.empty());
    }

    @Override
    public Optional<IMeeting> getCurrentMeeting(RoomId roomId) {
        return this.roomManagementRepository.findOne(roomId)
                .map(room -> Optional.of(this.meetingManagementService.findCurrent(roomId)))
                .orElse(Optional.empty());
    }

    @Override
    public ExtensionResult extendCurrentMeeting(RoomId roomId, Duration extension) {
        return this.roomManagementRepository.findOne(roomId)
                .map(room -> {
                    try {
                        return getCurrentMeeting(room.getId())
                                .map(meeting -> {
                                    this.meetingManagementService.extendMeeting(meeting.getId(), extension);
                                    return ExtensionResult.SUCCESS;})
                                .orElse(ExtensionResult.NOT_FOUND);
                    }
                    catch(Exception e) {
                        return ExtensionResult.fromException(e);
                    }
                })
                .orElse(ExtensionResult.NOT_FOUND);
    }
}
