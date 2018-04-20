package de.qaware.smartlabcore.trigger.service;

import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.Room;
import de.qaware.smartlabcommons.api.client.IRoomManagementApiClient;
import de.qaware.smartlabcommons.api.client.IWorkgroupManagementApiClient;
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
    public void setUpMeeting(IMeeting meeting) {
        val room = roomManagementApiClient.getRoom(meeting.getRoomId()).getBody();
        val workgroup = workgroupManagementApiClient.getWorkgroup(meeting.getWorkgroupId()).getBody();
        room.setUpMeeting(meeting, workgroup);
        log.info(String.format("Set up room \"%s\" (id: %d) for meeting (id: %d) of workgroup \"%s\" (id: %d)",
                room.getName(),
                room.getId(),
                meeting.getId(),
                workgroup.getName(),
                workgroup.getId()));
    }

    @Override
    public void setUpCurrentMeetingByRoomId(String roomId) {
        setUpMeeting(roomManagementApiClient.getCurrentMeeting(roomId).getBody());
    }

    @Override
    public void setUpCurrentMeeting(Room room) {
        setUpCurrentMeetingByRoomId(room.getId());
    }

    @Override
    public void setUpCurrentMeetingByWorkgroupId(String workgroupId) {
        setUpMeeting(workgroupManagementApiClient.getCurrentMeeting(workgroupId).getBody());
    }

    @Override
    public void setUpCurrentMeeting(Workgroup workgroup) {
        setUpCurrentMeetingByWorkgroupId(workgroup.getId());
    }

    @Override
    public void cleanUpMeeting(IMeeting meeting) {
        // meeting.triggerAssistances(new TriggerMeetingCleanUp());
        val room = roomManagementApiClient.getRoom(meeting.getRoomId()).getBody();
        val workgroup = workgroupManagementApiClient.getWorkgroup(meeting.getWorkgroupId()).getBody();
        room.cleanUpMeeting(meeting, workgroup);
        log.info(String.format("Clean up room \"%s\" (id: %d) for meeting (id: %d) of workgroup \"%s\" (id: %d)",
                room.getName(),
                room.getId(),
                meeting.getId(),
                workgroup.getName(),
                workgroup.getId()));
    }

    @Override
    public void cleanUpCurrentMeetingByRoomId(String roomId) {
        cleanUpMeeting(roomManagementApiClient.getCurrentMeeting(roomId).getBody());
    }

    @Override
    public void cleanUpCurrentMeeting(Room room) {
        cleanUpCurrentMeetingByRoomId(room.getId());
    }

    // TODO: Clean up LAST meeting einführen, da kein current meeting vorhanden nach dem Ende!!!

    @Override
    public void cleanUpCurrentMeetingByWorkgroupId(String workgroupId) {
        cleanUpMeeting(workgroupManagementApiClient.getCurrentMeeting(workgroupId).getBody());
    }

    @Override
    public void cleanUpCurrentMeeting(Workgroup workgroup) {
        cleanUpCurrentMeetingByWorkgroupId(workgroup.getId());
    }

    @Override
    public void startMeeting(IMeeting meeting) {
        val room = roomManagementApiClient.getRoom(meeting.getRoomId()).getBody();
        val workgroup = workgroupManagementApiClient.getWorkgroup(meeting.getWorkgroupId()).getBody();
        room.startMeeting(meeting, workgroup);
        log.info(String.format("Started meeting (id: %d) of workgroup \"%s\" (id: %d) in room \"%s\" (id: %d)",
                meeting.getId(),
                workgroup.getName(),
                workgroup.getId(),
                room.getName(),
                room.getId()));
    }

    @Override
    public void startCurrentMeetingByRoomId(String roomId){
        startMeeting(roomManagementApiClient.getCurrentMeeting(roomId).getBody());
    }

    @Override
    public void startCurrentMeeting(Room room) {
        startCurrentMeetingByRoomId(room.getId());
    }

    @Override
    public void startCurrentMeetingByWorkgroupId(String workgroupId) {
        startMeeting(workgroupManagementApiClient.getCurrentMeeting(workgroupId).getBody());
    }

    @Override
    public void startCurrentMeeting(Workgroup workgroup) {
        startCurrentMeetingByWorkgroupId(workgroup.getId());
    }

    @Override
    public void stopMeeting(IMeeting meeting) {
        val room = roomManagementApiClient.getRoom(meeting.getRoomId()).getBody();
        val workgroup = workgroupManagementApiClient.getWorkgroup(meeting.getWorkgroupId()).getBody();
        room.stopMeeting(meeting, workgroup);
        log.info(String.format("Stopped meeting (id: %d) of workgroup \"%s\" (id: %d) in room \"%s\" (id: %d)",
                meeting.getId(),
                workgroup.getName(),
                workgroup.getId(),
                room.getName(),
                room.getId()));
    }

    @Override
    public void stopCurrentMeetingByRoomId(String roomId) {
        stopMeeting(roomManagementApiClient.getCurrentMeeting(roomId).getBody());
    }

    @Override
    public void stopCurrentMeeting(Room room) {
        stopCurrentMeetingByRoomId(room.getId());
    }

    @Override
    public void stopCurrentMeetingByWorkgroupId(String workgroupId) {
        stopMeeting(workgroupManagementApiClient.getCurrentMeeting(workgroupId).getBody());
    }

    @Override
    public void stopCurrentMeeting(Workgroup workgroup) {
        stopCurrentMeetingByWorkgroupId(workgroup.getId());
    }
}
