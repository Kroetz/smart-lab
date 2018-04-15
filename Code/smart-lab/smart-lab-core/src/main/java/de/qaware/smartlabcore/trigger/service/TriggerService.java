package de.qaware.smartlabcore.trigger.service;


import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import de.qaware.smartlabcore.entity.meeting.IMeeting;
import de.qaware.smartlabcore.entity.room.Room;
import de.qaware.smartlabcore.meeting.service.IMeetingService;
import de.qaware.smartlabcore.room.service.IRoomService;
import de.qaware.smartlabcore.workgroup.service.IWorkgroupService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TriggerService implements ITriggerService {

    @Autowired
    @Qualifier("mock")
    private IRoomService roomService;

    @Autowired
    @Qualifier("mock")
    private IMeetingService meetingService;

    @Autowired
    @Qualifier("mock")
    private IWorkgroupService workgroupService;

    @Override
    public void setUpMeeting(IMeeting meeting) {
        val room = roomService.getRoom(meeting.getRoomId()).orElseThrow(IllegalStateException::new);
        val workgroup = workgroupService.getWorkgroup(meeting.getWorkgroupId()).orElseThrow(IllegalStateException::new);
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
        roomService.getRoom(roomId).ifPresent(this::setUpCurrentMeeting);
    }

    @Override
    public void setUpCurrentMeeting(Room room) {
        meetingService.getCurrentMeeting(room).ifPresent(this::setUpMeeting);
    }

    @Override
    public void setUpCurrentMeetingByWorkgroupId(long workgroupId) {
        workgroupService.getWorkgroup(workgroupId).ifPresent(this::setUpCurrentMeeting);
    }

    @Override
    public void setUpCurrentMeeting(Workgroup workgroup) {
        meetingService.getCurrentMeeting(workgroup).ifPresent(this::setUpMeeting);
    }

    @Override
    public void cleanUpMeeting(IMeeting meeting) {
        // meeting.triggerAssistances(new TriggerMeetingCleanUp());
        val room = roomService.getRoom(meeting.getRoomId()).orElseThrow(IllegalStateException::new);
        val workgroup = workgroupService.getWorkgroup(meeting.getWorkgroupId()).orElseThrow(IllegalStateException::new);
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
        roomService.getRoom(roomId).ifPresent(this::cleanUpCurrentMeeting);
    }

    @Override
    public void cleanUpCurrentMeeting(Room room) {
        meetingService.getCurrentMeeting(room).ifPresent(this::cleanUpMeeting);
    }

    // TODO: Clean up LAST meeting einführen, da kein current meeting vorhanden nach dem Ende!!!

    @Override
    public void cleanUpCurrentMeetingByWorkgroupId(long workgroupId) {
        workgroupService.getWorkgroup(workgroupId).ifPresent(this::cleanUpCurrentMeeting);
    }

    @Override
    public void cleanUpCurrentMeeting(Workgroup workgroup) {
        meetingService.getCurrentMeeting(workgroup).ifPresent(this::cleanUpMeeting);
    }

    @Override
    public void startMeeting(IMeeting meeting) {
        val room = roomService.getRoom(meeting.getRoomId()).orElseThrow(IllegalStateException::new);
        val workgroup = workgroupService.getWorkgroup(meeting.getWorkgroupId()).orElseThrow(IllegalStateException::new);
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
        roomService.getRoom(roomId).ifPresent(this::startCurrentMeeting);
    }

    @Override
    public void startCurrentMeeting(Room room) {
        meetingService.getCurrentMeeting(room).ifPresent(this::startMeeting);
    }

    @Override
    public void startCurrentMeetingByWorkgroupId(long workgroupId) {
        workgroupService.getWorkgroup(workgroupId).ifPresent(this::startCurrentMeeting);
    }

    @Override
    public void startCurrentMeeting(Workgroup workgroup) {
        meetingService.getCurrentMeeting(workgroup).ifPresent(this::startMeeting);
    }

    @Override
    public void stopMeeting(IMeeting meeting) {
        val room = roomService.getRoom(meeting.getRoomId()).orElseThrow(IllegalStateException::new);
        val workgroup = workgroupService.getWorkgroup(meeting.getWorkgroupId()).orElseThrow(IllegalStateException::new);
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
        roomService.getRoom(roomId).ifPresent(this::stopCurrentMeeting);
    }

    @Override
    public void stopCurrentMeeting(Room room) {
        meetingService.getCurrentMeeting(room).ifPresent(this::stopMeeting);
    }

    @Override
    public void stopCurrentMeetingByWorkgroupId(long workgroupId) {
        workgroupService.getWorkgroup(workgroupId).ifPresent(this::stopCurrentMeeting);
    }

    @Override
    public void stopCurrentMeeting(Workgroup workgroup) {
        meetingService.getCurrentMeeting(workgroup).ifPresent(this::stopMeeting);
    }
}
