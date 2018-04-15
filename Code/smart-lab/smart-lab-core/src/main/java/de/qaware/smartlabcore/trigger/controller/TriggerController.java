package de.qaware.smartlabcore.trigger.controller;

import de.qaware.smartlabcore.trigger.service.ITriggerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(TriggerController.MAPPING_BASE)
@Slf4j
public class TriggerController {

    public static final String MAPPING_BASE = "/smart-lab/api/trigger";
    public static final String MAPPING_SET_UP_CURRENT_MEETING_BY_ROOM_ID = "/set-up-current-meeting/room/{roomId}";
    public static final String MAPPING_SET_UP_CURRENT_MEETING_BY_WORKGROUP_ID = "/set-up-current-meeting/workgroup/{workgroupId}";
    public static final String MAPPING_CLEAN_UP_CURRENT_MEETING_BY_ROOM_ID = "/clean-up-current-meeting/room/{roomId}";
    public static final String MAPPING_CLEAN_UP_CURRENT_MEETING_BY_WORKGROUP_ID = "/clean-up-current-meeting/workgroup/{workgroupId}/";
    public static final String MAPPING_START_CURRENT_MEETING_BY_ROOM_ID = "/start-current-meeting/room/{roomId}";
    public static final String MAPPING_START_CURRENT_MEETING_BY_WORKGROUP_ID = "/start-current-meeting/workgroup/{workgroupId}";
    public static final String MAPPING_STOP_CURRENT_MEETING_BY_ROOM_ID = "/stop-current-meeting/room/{roomId}";
    public static final String MAPPING_STOP_CURRENT_MEETING_BY_WORKGROUP_ID = "/stop-current-meeting/workgroup/{workgroupId}";

    private final ITriggerService triggerService;

    public TriggerController(ITriggerService triggerService) {
        this.triggerService = triggerService;
    }

    @PostMapping(MAPPING_SET_UP_CURRENT_MEETING_BY_ROOM_ID)
    public void setUpCurrentMeetingByRoomId(@PathVariable("roomId") long roomId) {
        triggerService.setUpCurrentMeetingByRoomId(roomId);
    }

    @PostMapping(MAPPING_SET_UP_CURRENT_MEETING_BY_WORKGROUP_ID)
    public void setUpCurrentMeetingByWorkgroupId(@PathVariable("workgroupId") long workgroupId) {
        triggerService.setUpCurrentMeetingByWorkgroupId(workgroupId);
    }

    @PostMapping(MAPPING_CLEAN_UP_CURRENT_MEETING_BY_ROOM_ID)
    public void cleanUpCurrentMeetingByRoomId(@PathVariable("roomId") long roomId) {
        triggerService.cleanUpCurrentMeetingByRoomId(roomId);
    }

    @PostMapping(MAPPING_CLEAN_UP_CURRENT_MEETING_BY_WORKGROUP_ID)
    public void cleanUpCurrentMeetingByWorkgroupId(@PathVariable("workgroupId") long workgroupId) {
        triggerService.cleanUpCurrentMeetingByWorkgroupId(workgroupId);
    }

    @PostMapping(MAPPING_START_CURRENT_MEETING_BY_ROOM_ID)
    public void startCurrentMeetingByRoomId(@PathVariable("roomId") long roomId) {
        triggerService.startCurrentMeetingByRoomId(roomId);
    }

    @PostMapping(MAPPING_START_CURRENT_MEETING_BY_WORKGROUP_ID)
    public void startCurrentMeetingByWorkgroupId(@PathVariable("workgroupId") long workgroupId) {
        triggerService.startCurrentMeetingByWorkgroupId(workgroupId);
    }

    @PostMapping(MAPPING_STOP_CURRENT_MEETING_BY_ROOM_ID)
    public void stopCurrentMeetingByRoomId(@PathVariable("roomId") long roomId) {
        triggerService.stopCurrentMeetingByRoomId(roomId);
    }

    @PostMapping(MAPPING_STOP_CURRENT_MEETING_BY_WORKGROUP_ID)
    public void stopCurrentMeetingByWorkgroupId(@PathVariable("workgroupId") long workgroupId) {
        triggerService.stopCurrentMeetingByWorkgroupId(workgroupId);
    }
}
