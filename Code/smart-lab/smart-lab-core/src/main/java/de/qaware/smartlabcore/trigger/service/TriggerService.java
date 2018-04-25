package de.qaware.smartlabcore.trigger.service;

import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.Room;
import de.qaware.smartlabcommons.api.client.IRoomManagementApiClient;
import de.qaware.smartlabcommons.api.client.IWorkgroupManagementApiClient;
import de.qaware.smartlabcore.generic.result.CleanUpMeetingResult;
import de.qaware.smartlabcore.generic.result.SetUpMeetingResult;
import de.qaware.smartlabcore.generic.result.StartMeetingResult;
import de.qaware.smartlabcore.generic.result.StopMeetingResult;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TriggerService implements ITriggerService {

    private final IRoomManagementApiClient roomManagementApiClient;
    private final IWorkgroupManagementApiClient workgroupManagementApiClient;

    public TriggerService(
            IRoomManagementApiClient roomManagementApiClient,
            IWorkgroupManagementApiClient workgroupManagementApiClient) {
        this.roomManagementApiClient = roomManagementApiClient;
        this.workgroupManagementApiClient = workgroupManagementApiClient;
    }

    @Override
    public SetUpMeetingResult setUpMeeting(IMeeting meeting) {
        val room = roomManagementApiClient.findOne(meeting.getRoomId()).getBody();
        val workgroup = workgroupManagementApiClient.findOne(meeting.getWorkgroupId()).getBody();
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
        return setUpMeeting(roomManagementApiClient.getCurrentMeeting(roomId).getBody());
    }

    @Override
    public SetUpMeetingResult setUpCurrentMeeting(Room room) {
        return setUpCurrentMeetingByRoomId(room.getId());
    }

    @Override
    public SetUpMeetingResult setUpCurrentMeetingByWorkgroupId(String workgroupId) {
        return setUpMeeting(workgroupManagementApiClient.getCurrentMeeting(workgroupId).getBody());
    }

    @Override
    public SetUpMeetingResult setUpCurrentMeeting(Workgroup workgroup) {
        return setUpCurrentMeetingByWorkgroupId(workgroup.getId());
    }

    @Override
    public CleanUpMeetingResult cleanUpMeeting(IMeeting meeting) {
        // meeting.triggerAssistances(new TriggerMeetingCleanUp());
        val room = roomManagementApiClient.findOne(meeting.getRoomId()).getBody();
        val workgroup = workgroupManagementApiClient.findOne(meeting.getWorkgroupId()).getBody();
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
        return cleanUpMeeting(roomManagementApiClient.getCurrentMeeting(roomId).getBody());
    }

    @Override
    public CleanUpMeetingResult cleanUpCurrentMeeting(Room room) {
        return cleanUpCurrentMeetingByRoomId(room.getId());
    }

    // TODO: Clean up LAST meeting einführen, da kein current meeting vorhanden nach dem Ende!!!

    @Override
    public CleanUpMeetingResult cleanUpCurrentMeetingByWorkgroupId(String workgroupId) {
        return cleanUpMeeting(workgroupManagementApiClient.getCurrentMeeting(workgroupId).getBody());
    }

    @Override
    public CleanUpMeetingResult cleanUpCurrentMeeting(Workgroup workgroup) {
        return cleanUpCurrentMeetingByWorkgroupId(workgroup.getId());
    }

    @Override
    public StartMeetingResult startMeeting(IMeeting meeting) {
        val room = roomManagementApiClient.findOne(meeting.getRoomId()).getBody();
        val workgroup = workgroupManagementApiClient.findOne(meeting.getWorkgroupId()).getBody();
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
        return startMeeting(roomManagementApiClient.getCurrentMeeting(roomId).getBody());
    }

    @Override
    public StartMeetingResult startCurrentMeeting(Room room) {
        return startCurrentMeetingByRoomId(room.getId());
    }

    @Override
    public StartMeetingResult startCurrentMeetingByWorkgroupId(String workgroupId) {
        return startMeeting(workgroupManagementApiClient.getCurrentMeeting(workgroupId).getBody());
    }

    @Override
    public StartMeetingResult startCurrentMeeting(Workgroup workgroup) {
        return startCurrentMeetingByWorkgroupId(workgroup.getId());
    }

    @Override
    public StopMeetingResult stopMeeting(IMeeting meeting) {
        val room = roomManagementApiClient.findOne(meeting.getRoomId()).getBody();
        val workgroup = workgroupManagementApiClient.findOne(meeting.getWorkgroupId()).getBody();
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
        return stopMeeting(roomManagementApiClient.getCurrentMeeting(roomId).getBody());
    }

    @Override
    public StopMeetingResult stopCurrentMeeting(Room room) {
        return stopCurrentMeetingByRoomId(room.getId());
    }

    @Override
    public StopMeetingResult stopCurrentMeetingByWorkgroupId(String workgroupId) {
        return stopMeeting(workgroupManagementApiClient.getCurrentMeeting(workgroupId).getBody());
    }

    @Override
    public StopMeetingResult stopCurrentMeeting(Workgroup workgroup) {
        return stopCurrentMeetingByWorkgroupId(workgroup.getId());
    }
}
