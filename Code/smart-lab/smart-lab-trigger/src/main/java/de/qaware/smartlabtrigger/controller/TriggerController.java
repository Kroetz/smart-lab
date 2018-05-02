package de.qaware.smartlabtrigger.controller;

import de.qaware.smartlabcommons.api.TriggerApiConstants;
import de.qaware.smartlabtrigger.business.ITriggerBusinessLogic;
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

    private final ITriggerBusinessLogic triggerBusinessLogic;

    public TriggerController(ITriggerBusinessLogic triggerBusinessLogic) {
        this.triggerBusinessLogic = triggerBusinessLogic;
    }

    @PostMapping(TriggerApiConstants.MAPPING_SET_UP_CURRENT_MEETING_BY_ROOM_ID)
    public ResponseEntity<Void> setUpCurrentMeetingByRoomId(@PathVariable(TriggerApiConstants.PARAMETER_NAME_ROOM_ID) String roomId) {
        return triggerBusinessLogic.setUpCurrentMeetingByRoomId(roomId).toResponseEntity();
    }

    @PostMapping(TriggerApiConstants.MAPPING_SET_UP_CURRENT_MEETING_BY_WORKGROUP_ID)
    public ResponseEntity<Void> setUpCurrentMeetingByWorkgroupId(@PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return triggerBusinessLogic.setUpCurrentMeetingByWorkgroupId(workgroupId).toResponseEntity();
    }

    @PostMapping(TriggerApiConstants.MAPPING_CLEAN_UP_CURRENT_MEETING_BY_ROOM_ID)
    public ResponseEntity<Void> cleanUpCurrentMeetingByRoomId(@PathVariable(TriggerApiConstants.PARAMETER_NAME_ROOM_ID) String roomId) {
        return triggerBusinessLogic.cleanUpCurrentMeetingByRoomId(roomId).toResponseEntity();
    }

    @PostMapping(TriggerApiConstants.MAPPING_CLEAN_UP_CURRENT_MEETING_BY_WORKGROUP_ID)
    public ResponseEntity<Void> cleanUpCurrentMeetingByWorkgroupId(@PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return triggerBusinessLogic.cleanUpCurrentMeetingByWorkgroupId(workgroupId).toResponseEntity();
    }

    @PostMapping(TriggerApiConstants.MAPPING_START_CURRENT_MEETING_BY_ROOM_ID)
    public ResponseEntity<Void> startCurrentMeetingByRoomId(@PathVariable(TriggerApiConstants.PARAMETER_NAME_ROOM_ID) String roomId) {
        return triggerBusinessLogic.startCurrentMeetingByRoomId(roomId).toResponseEntity();
    }

    @PostMapping(TriggerApiConstants.MAPPING_START_CURRENT_MEETING_BY_WORKGROUP_ID)
    public ResponseEntity<Void> startCurrentMeetingByWorkgroupId(@PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return triggerBusinessLogic.startCurrentMeetingByWorkgroupId(workgroupId).toResponseEntity();
    }

    @PostMapping(TriggerApiConstants.MAPPING_STOP_CURRENT_MEETING_BY_ROOM_ID)
    public ResponseEntity<Void> stopCurrentMeetingByRoomId(@PathVariable(TriggerApiConstants.PARAMETER_NAME_ROOM_ID) String roomId) {
        return triggerBusinessLogic.stopCurrentMeetingByRoomId(roomId).toResponseEntity();
    }

    @PostMapping(TriggerApiConstants.MAPPING_STOP_CURRENT_MEETING_BY_WORKGROUP_ID)
    public ResponseEntity<Void> stopCurrentMeetingByWorkgroupId(@PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return triggerBusinessLogic.stopCurrentMeetingByWorkgroupId(workgroupId).toResponseEntity();
    }
}
