package de.qaware.smartlabtrigger.business;

import de.qaware.smartlabcommons.api.service.room.IRoomManagementService;
import de.qaware.smartlabcommons.api.service.workgroup.IWorkgroupManagementService;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.data.room.Room;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import de.qaware.smartlabcommons.result.CleanUpMeetingResult;
import de.qaware.smartlabcommons.result.SetUpMeetingResult;
import de.qaware.smartlabcommons.result.StartMeetingResult;
import de.qaware.smartlabcommons.result.StopMeetingResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TriggerBusinessLogic implements ITriggerBusinessLogic {

    private final IRoomManagementService roomManagementService;
    private final IWorkgroupManagementService workgroupManagementService;

    public TriggerBusinessLogic(
            IRoomManagementService roomManagementService,
            IWorkgroupManagementService workgroupManagementService) {
        this.roomManagementService = roomManagementService;
        this.workgroupManagementService = workgroupManagementService;
    }

    @Override
    public SetUpMeetingResult setUpMeeting(IMeeting meeting) {
        IRoom room = roomManagementService.findOne(meeting.getRoomId());
        IWorkgroup workgroup = workgroupManagementService.findOne(meeting.getWorkgroupId());
        room.setUpMeeting(meeting, workgroup);
        log.info(String.format("Set up room \"%s\" (id: %s) for meeting (id: %s) of workgroup \"%s\" (id: %s)",
                room.getName(),
                room.getId(),
                meeting.getId(),
                workgroup.getName(),
                workgroup.getId()));
        return SetUpMeetingResult.SUCCESS;
    }

    @Override
    public SetUpMeetingResult setUpCurrentMeetingByRoomId(String roomId) {
        return setUpMeeting(roomManagementService.getCurrentMeeting(roomId));
    }

    @Override
    public SetUpMeetingResult setUpCurrentMeeting(Room room) {
        return setUpCurrentMeetingByRoomId(room.getId());
    }

    @Override
    public SetUpMeetingResult setUpCurrentMeetingByWorkgroupId(String workgroupId) {
        return setUpMeeting(workgroupManagementService.getCurrentMeeting(workgroupId));
    }

    @Override
    public SetUpMeetingResult setUpCurrentMeeting(Workgroup workgroup) {
        return setUpCurrentMeetingByWorkgroupId(workgroup.getId());
    }

    @Override
    public CleanUpMeetingResult cleanUpMeeting(IMeeting meeting) {
        // meeting.triggerAssistances(new TriggerMeetingCleanUp());
        IRoom room = roomManagementService.findOne(meeting.getRoomId());
        IWorkgroup workgroup = workgroupManagementService.findOne(meeting.getWorkgroupId());
        room.cleanUpMeeting(meeting, workgroup);
        log.info(String.format("Clean up room \"%s\" (id: %s) for meeting (id: %s) of workgroup \"%s\" (id: %s)",
                room.getName(),
                room.getId(),
                meeting.getId(),
                workgroup.getName(),
                workgroup.getId()));
        return CleanUpMeetingResult.SUCCESS;
    }

    @Override
    public CleanUpMeetingResult cleanUpCurrentMeetingByRoomId(String roomId) {
        return cleanUpMeeting(roomManagementService.getCurrentMeeting(roomId));
    }

    @Override
    public CleanUpMeetingResult cleanUpCurrentMeeting(Room room) {
        return cleanUpCurrentMeetingByRoomId(room.getId());
    }

    // TODO: Clean up LAST meeting einführen, da kein current meeting vorhanden nach dem Ende!!!

    @Override
    public CleanUpMeetingResult cleanUpCurrentMeetingByWorkgroupId(String workgroupId) {
        return cleanUpMeeting(workgroupManagementService.getCurrentMeeting(workgroupId));
    }

    @Override
    public CleanUpMeetingResult cleanUpCurrentMeeting(Workgroup workgroup) {
        return cleanUpCurrentMeetingByWorkgroupId(workgroup.getId());
    }

    @Override
    public StartMeetingResult startMeeting(IMeeting meeting) {
        IRoom room = roomManagementService.findOne(meeting.getRoomId());
        IWorkgroup workgroup = workgroupManagementService.findOne(meeting.getWorkgroupId());
        room.startMeeting(meeting, workgroup);
        log.info(String.format("Started meeting (id: %s) of workgroup \"%s\" (id: %s) in room \"%s\" (id: %s)",
                meeting.getId(),
                workgroup.getName(),
                workgroup.getId(),
                room.getName(),
                room.getId()));
        return StartMeetingResult.SUCCESS;
    }

    @Override
    public StartMeetingResult startCurrentMeetingByRoomId(String roomId){
        return startMeeting(roomManagementService.getCurrentMeeting(roomId));
    }

    @Override
    public StartMeetingResult startCurrentMeeting(Room room) {
        return startCurrentMeetingByRoomId(room.getId());
    }

    @Override
    public StartMeetingResult startCurrentMeetingByWorkgroupId(String workgroupId) {
        return startMeeting(workgroupManagementService.getCurrentMeeting(workgroupId));
    }

    @Override
    public StartMeetingResult startCurrentMeeting(Workgroup workgroup) {
        return startCurrentMeetingByWorkgroupId(workgroup.getId());
    }

    @Override
    public StopMeetingResult stopMeeting(IMeeting meeting) {
        IRoom room = roomManagementService.findOne(meeting.getRoomId());
        IWorkgroup workgroup = workgroupManagementService.findOne(meeting.getWorkgroupId());
        room.stopMeeting(meeting, workgroup);
        log.info(String.format("Stopped meeting (id: %s) of workgroup \"%s\" (id: %s) in room \"%s\" (id: %s)",
                meeting.getId(),
                workgroup.getName(),
                workgroup.getId(),
                room.getName(),
                room.getId()));
        return StopMeetingResult.SUCCESS;
    }

    @Override
    public StopMeetingResult stopCurrentMeetingByRoomId(String roomId) {
        return stopMeeting(roomManagementService.getCurrentMeeting(roomId));
    }

    @Override
    public StopMeetingResult stopCurrentMeeting(Room room) {
        return stopCurrentMeetingByRoomId(room.getId());
    }

    @Override
    public StopMeetingResult stopCurrentMeetingByWorkgroupId(String workgroupId) {
        return stopMeeting(workgroupManagementService.getCurrentMeeting(workgroupId));
    }

    @Override
    public StopMeetingResult stopCurrentMeeting(Workgroup workgroup) {
        return stopCurrentMeetingByWorkgroupId(workgroup.getId());
    }
}
