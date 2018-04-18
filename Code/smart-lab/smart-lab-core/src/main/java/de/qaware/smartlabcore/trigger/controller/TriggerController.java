package de.qaware.smartlabcore.trigger.controller;

import de.qaware.smartlabcommons.api.TriggerApiConstants;
import de.qaware.smartlabcore.trigger.service.ITriggerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(TriggerApiConstants.MAPPING_BASE)
@Slf4j
public class TriggerController {

    private final ITriggerService triggerService;

    public TriggerController(ITriggerService triggerService) {
        this.triggerService = triggerService;
    }

    @PostMapping(TriggerApiConstants.MAPPING_SET_UP_CURRENT_MEETING_BY_ROOM_ID)
    public void setUpCurrentMeetingByRoomId(@PathVariable("roomId") long roomId) {
        triggerService.setUpCurrentMeetingByRoomId(roomId);
    }

    @PostMapping(TriggerApiConstants.MAPPING_SET_UP_CURRENT_MEETING_BY_WORKGROUP_ID)
    public void setUpCurrentMeetingByWorkgroupId(@PathVariable("workgroupId") long workgroupId) {
        triggerService.setUpCurrentMeetingByWorkgroupId(workgroupId);
    }

    @PostMapping(TriggerApiConstants.MAPPING_CLEAN_UP_CURRENT_MEETING_BY_ROOM_ID)
    public void cleanUpCurrentMeetingByRoomId(@PathVariable("roomId") long roomId) {
        triggerService.cleanUpCurrentMeetingByRoomId(roomId);
    }

    @PostMapping(TriggerApiConstants.MAPPING_CLEAN_UP_CURRENT_MEETING_BY_WORKGROUP_ID)
    public void cleanUpCurrentMeetingByWorkgroupId(@PathVariable("workgroupId") long workgroupId) {
        triggerService.cleanUpCurrentMeetingByWorkgroupId(workgroupId);
    }

    @PostMapping(TriggerApiConstants.MAPPING_START_CURRENT_MEETING_BY_ROOM_ID)
    public void startCurrentMeetingByRoomId(@PathVariable("roomId") long roomId) {
        triggerService.startCurrentMeetingByRoomId(roomId);
    }

    @PostMapping(TriggerApiConstants.MAPPING_START_CURRENT_MEETING_BY_WORKGROUP_ID)
    public void startCurrentMeetingByWorkgroupId(@PathVariable("workgroupId") long workgroupId) {
        triggerService.startCurrentMeetingByWorkgroupId(workgroupId);
    }

    @PostMapping(TriggerApiConstants.MAPPING_STOP_CURRENT_MEETING_BY_ROOM_ID)
    public void stopCurrentMeetingByRoomId(@PathVariable("roomId") long roomId) {
        triggerService.stopCurrentMeetingByRoomId(roomId);
    }

    @PostMapping(TriggerApiConstants.MAPPING_STOP_CURRENT_MEETING_BY_WORKGROUP_ID)
    public void stopCurrentMeetingByWorkgroupId(@PathVariable("workgroupId") long workgroupId) {
        triggerService.stopCurrentMeetingByWorkgroupId(workgroupId);
    }
}
