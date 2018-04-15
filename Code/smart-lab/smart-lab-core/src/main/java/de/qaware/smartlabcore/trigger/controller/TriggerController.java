package de.qaware.smartlabcore.trigger.controller;

import de.qaware.smartlabcore.trigger.service.ITriggerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(TriggerController.MAPPING_BASE)
@Slf4j
public class TriggerController {

    public static final String MAPPING_BASE = "/smart-lab/api/trigger";

    public static final String DECIMAL_WILDCARD = "/%d";

    public static final String MAPPING_ROOM = "/room";
    public static final String MAPPING_ROOM_ID = "/{roomId}";
    public static final String MAPPING_SET_UP_CURRENT_MEETING_BY_ROOM_ID = MAPPING_ROOM + MAPPING_ROOM_ID + "/set-up-current-meeting";
    public static final String MAPPING_CLEAN_UP_CURRENT_MEETING_BY_ROOM_ID = MAPPING_ROOM + MAPPING_ROOM_ID + "/clean-up-current-meeting";


    public static final String URL_TEMPLATE_SET_UP_CURRENT_MEETING_BY_ROOM_ID = MAPPING_ROOM + DECIMAL_WILDCARD + "/set-up-current-meeting";
    public static final String URL_TEMPLATE_CLEAN_UP_CURRENT_MEETING_BY_ROOM_ID = MAPPING_ROOM + DECIMAL_WILDCARD + "/clean-up-current-meeting";


    private final ITriggerService triggerService;

    @Autowired
    public TriggerController(ITriggerService triggerService) {
        this.triggerService = triggerService;
    }

    @PostMapping(MAPPING_SET_UP_CURRENT_MEETING_BY_ROOM_ID)
    public void setUpCurrentMeetingByRoomId(@PathVariable("roomId") long roomId) {
        triggerService.setUpCurrentMeetingByRoomId(roomId);
    }

    @PostMapping(value = "/workgroup/{workgroupId}/set-up-current-meeting")
    public void setUpCurrentMeetingByWorkgroupId(@PathVariable("workgroupId") long workgroupId) {
        triggerService.setUpCurrentMeetingByWorkgroupId(workgroupId);
    }

    @PostMapping(MAPPING_CLEAN_UP_CURRENT_MEETING_BY_ROOM_ID)
    public void cleanUpCurrentMeetingByRoomId(@PathVariable("roomId") long roomId) {
        triggerService.cleanUpCurrentMeetingByRoomId(roomId);
    }

    @PostMapping(value = "/workgroup/{workgroupId}/clean-up-current-meeting")
    public void cleanUpCurrentMeetingByWorkgroupId(@PathVariable("workgroupId") long workgroupId) {
        triggerService.cleanUpCurrentMeetingByWorkgroupId(workgroupId);
    }

    @PostMapping(value = "/room/{roomId}/start-current-meeting")
    public void startCurrentMeetingByRoomId(@PathVariable("roomId") long roomId) {
        triggerService.startCurrentMeetingByRoomId(roomId);
    }

    @PostMapping(value = "/workgroup/{workgroupId}/start-current-meeting")
    public void startCurrentMeetingByWorkgroupId(@PathVariable("workgroupId") long workgroupId) {
        triggerService.startCurrentMeetingByWorkgroupId(workgroupId);
    }

    @PostMapping(value = "/room/{roomId}/stop-current-meeting")
    public void stopCurrentMeetingByRoomId(@PathVariable("roomId") long roomId) {
        triggerService.stopCurrentMeetingByRoomId(roomId);
    }

    @PostMapping(value = "/workgroup/{workgroupId}/stop-current-meeting")
    public void stopCurrentMeetingByWorkgroupId(@PathVariable("workgroupId") long workgroupId) {
        triggerService.stopCurrentMeetingByWorkgroupId(workgroupId);
    }
}


