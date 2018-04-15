package de.qaware.smartlabcore.trigger.service;

import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import de.qaware.smartlabcore.entity.meeting.IMeeting;
import de.qaware.smartlabcore.entity.room.Room;
import de.qaware.smartlabcore.room.controller.IRoomManagementApiClient;
import de.qaware.smartlabcore.workgroup.controller.IWorkgroupManagementApiClient;
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
        val room = roomManagementApiClient.getRoom(meeting.getRoomId()).orElseThrow(IllegalStateException::new);
        val workgroup = workgroupManagementApiClient.getWorkgroup(meeting.getWorkgroupId()).orElseThrow(IllegalStateException::new);
        room.setUpMeeting(meeting, workgroup);
        log.info(String.format("Set up room \"%s\" (id: %d) for meeting (id: %d) of workgroup \"%s\" (id: %d)",
                room.getName(),
                room.getId(),
                meeting.getId(),
                workgroup.getName(),
                workgroup.getId()));
    }

    @Override
    public void setUpCurrentMeetingByRoomId(long roomId) {
        roomManagementApiClient.getCurrentMeeting(roomId).ifPresent(this::setUpMeeting);
    }

    @Override
    public void setUpCurrentMeeting(Room room) {
        setUpCurrentMeetingByRoomId(room.getId());
    }

    @Override
    public void setUpCurrentMeetingByWorkgroupId(long workgroupId) {
        workgroupManagementApiClient.getCurrentMeeting(workgroupId).ifPresent(this::setUpMeeting);
    }

    @Override
    public void setUpCurrentMeeting(Workgroup workgroup) {
        setUpCurrentMeetingByWorkgroupId(workgroup.getId());
    }

    @Override
    public void cleanUpMeeting(IMeeting meeting) {
        // meeting.triggerAssistances(new TriggerMeetingCleanUp());
        val room = roomManagementApiClient.getRoom(meeting.getRoomId()).orElseThrow(IllegalStateException::new);
        val workgroup = workgroupManagementApiClient.getWorkgroup(meeting.getWorkgroupId()).orElseThrow(IllegalStateException::new);
        room.cleanUpMeeting(meeting, workgroup);
        log.info(String.format("Clean up room \"%s\" (id: %d) for meeting (id: %d) of workgroup \"%s\" (id: %d)",
                room.getName(),
                room.getId(),
                meeting.getId(),
                workgroup.getName(),
                workgroup.getId()));
    }

    @Override
    public void cleanUpCurrentMeetingByRoomId(long roomId) {
        roomManagementApiClient.getCurrentMeeting(roomId).ifPresent(this::cleanUpMeeting);
    }

    @Override
    public void cleanUpCurrentMeeting(Room room) {
        cleanUpCurrentMeetingByRoomId(room.getId());
    }

    // TODO: Clean up LAST meeting einführen, da kein current meeting vorhanden nach dem Ende!!!

    @Override
    public void cleanUpCurrentMeetingByWorkgroupId(long workgroupId) {
        workgroupManagementApiClient.getCurrentMeeting(workgroupId).ifPresent(this::cleanUpMeeting);
    }

    @Override
    public void cleanUpCurrentMeeting(Workgroup workgroup) {
        cleanUpCurrentMeetingByWorkgroupId(workgroup.getId());
    }

    @Override
    public void startMeeting(IMeeting meeting) {
        val room = roomManagementApiClient.getRoom(meeting.getRoomId()).orElseThrow(IllegalStateException::new);
        val workgroup = workgroupManagementApiClient.getWorkgroup(meeting.getWorkgroupId()).orElseThrow(IllegalStateException::new);
        room.startMeeting(meeting, workgroup);
        log.info(String.format("Started meeting (id: %d) of workgroup \"%s\" (id: %d) in room \"%s\" (id: %d)",
                meeting.getId(),
                workgroup.getName(),
                workgroup.getId(),
                room.getName(),
                room.getId()));
    }

    @Override
    public void startCurrentMeetingByRoomId(long roomId){
        roomManagementApiClient.getCurrentMeeting(roomId).ifPresent(this::startMeeting);
    }

    @Override
    public void startCurrentMeeting(Room room) {
        startCurrentMeetingByRoomId(room.getId());
    }

    @Override
    public void startCurrentMeetingByWorkgroupId(long workgroupId) {
        workgroupManagementApiClient.getCurrentMeeting(workgroupId).ifPresent(this::startMeeting);
    }

    @Override
    public void startCurrentMeeting(Workgroup workgroup) {
        startCurrentMeetingByWorkgroupId(workgroup.getId());
    }

    @Override
    public void stopMeeting(IMeeting meeting) {
        val room = roomManagementApiClient.getRoom(meeting.getRoomId()).orElseThrow(IllegalStateException::new);
        val workgroup = workgroupManagementApiClient.getWorkgroup(meeting.getWorkgroupId()).orElseThrow(IllegalStateException::new);
        room.stopMeeting(meeting, workgroup);
        log.info(String.format("Stopped meeting (id: %d) of workgroup \"%s\" (id: %d) in room \"%s\" (id: %d)",
                meeting.getId(),
                workgroup.getName(),
                workgroup.getId(),
                room.getName(),
                room.getId()));
    }

    @Override
    public void stopCurrentMeetingByRoomId(long roomId) {
        roomManagementApiClient.getCurrentMeeting(roomId).ifPresent(this::stopMeeting);
    }

    @Override
    public void stopCurrentMeeting(Room room) {
        stopCurrentMeetingByRoomId(room.getId());
    }

    @Override
    public void stopCurrentMeetingByWorkgroupId(long workgroupId) {
        workgroupManagementApiClient.getCurrentMeeting(workgroupId).ifPresent(this::stopMeeting);
    }

    @Override
    public void stopCurrentMeeting(Workgroup workgroup) {
        stopCurrentMeetingByWorkgroupId(workgroup.getId());
    }
}
