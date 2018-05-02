package de.qaware.smartlabtrigger.trigger.controller;

import de.qaware.smartlabcommons.api.TriggerApiConstants;
import de.qaware.smartlabtrigger.trigger.service.ITriggerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> setUpCurrentMeetingByRoomId(@PathVariable(TriggerApiConstants.PARAMETER_NAME_ROOM_ID) String roomId) {
        return triggerService.setUpCurrentMeetingByRoomId(roomId).toResponseEntity();
    }

    @PostMapping(TriggerApiConstants.MAPPING_SET_UP_CURRENT_MEETING_BY_WORKGROUP_ID)
    public ResponseEntity<Void> setUpCurrentMeetingByWorkgroupId(@PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return triggerService.setUpCurrentMeetingByWorkgroupId(workgroupId).toResponseEntity();
    }

    @PostMapping(TriggerApiConstants.MAPPING_CLEAN_UP_CURRENT_MEETING_BY_ROOM_ID)
    public ResponseEntity<Void> cleanUpCurrentMeetingByRoomId(@PathVariable(TriggerApiConstants.PARAMETER_NAME_ROOM_ID) String roomId) {
        return triggerService.cleanUpCurrentMeetingByRoomId(roomId).toResponseEntity();
    }

    @PostMapping(TriggerApiConstants.MAPPING_CLEAN_UP_CURRENT_MEETING_BY_WORKGROUP_ID)
    public ResponseEntity<Void> cleanUpCurrentMeetingByWorkgroupId(@PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return triggerService.cleanUpCurrentMeetingByWorkgroupId(workgroupId).toResponseEntity();
    }

    @PostMapping(TriggerApiConstants.MAPPING_START_CURRENT_MEETING_BY_ROOM_ID)
    public ResponseEntity<Void> startCurrentMeetingByRoomId(@PathVariable(TriggerApiConstants.PARAMETER_NAME_ROOM_ID) String roomId) {
        return triggerService.startCurrentMeetingByRoomId(roomId).toResponseEntity();
    }

    @PostMapping(TriggerApiConstants.MAPPING_START_CURRENT_MEETING_BY_WORKGROUP_ID)
    public ResponseEntity<Void> startCurrentMeetingByWorkgroupId(@PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return triggerService.startCurrentMeetingByWorkgroupId(workgroupId).toResponseEntity();
    }

    @PostMapping(TriggerApiConstants.MAPPING_STOP_CURRENT_MEETING_BY_ROOM_ID)
    public ResponseEntity<Void> stopCurrentMeetingByRoomId(@PathVariable(TriggerApiConstants.PARAMETER_NAME_ROOM_ID) String roomId) {
        return triggerService.stopCurrentMeetingByRoomId(roomId).toResponseEntity();
    }

    @PostMapping(TriggerApiConstants.MAPPING_STOP_CURRENT_MEETING_BY_WORKGROUP_ID)
    public ResponseEntity<Void> stopCurrentMeetingByWorkgroupId(@PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return triggerService.stopCurrentMeetingByWorkgroupId(workgroupId).toResponseEntity();
    }
}
