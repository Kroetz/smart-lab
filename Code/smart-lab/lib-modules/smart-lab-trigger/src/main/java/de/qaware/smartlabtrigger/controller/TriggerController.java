package de.qaware.smartlabtrigger.controller;

import de.qaware.smartlabapi.TriggerApiConstants;
import de.qaware.smartlabcore.data.job.IJobInfo;
import de.qaware.smartlabtrigger.business.ITriggerBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;

@RestController
@RequestMapping(TriggerApiConstants.MAPPING_BASE)
@Slf4j
public class TriggerController {

    private final ITriggerBusinessLogic triggerBusinessLogic;

    public TriggerController(ITriggerBusinessLogic triggerBusinessLogic) {
        this.triggerBusinessLogic = triggerBusinessLogic;
    }

    @PostMapping(TriggerApiConstants.MAPPING_SET_UP_CURRENT_MEETING_BY_ROOM_ID)
    public ResponseEntity<IJobInfo> setUpCurrentMeetingByRoomId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_ROOM_ID) String roomId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl) {
        log.info("Received call to set up the current meeting in the room with ID \"{}\"", roomId);
        ResponseEntity<IJobInfo> response;
        try {
            response = ResponseEntity.accepted().body(triggerBusinessLogic.setUpCurrentMeetingByRoomId(
                    roomId,
                    callbackUrl != null ? new URL(callbackUrl) : null));
        } catch (MalformedURLException e) {
            // TODO: Better exception and message
            throw new RuntimeException(e);
        }
        log.info("Returning response with HTTP status code {}", response.getStatusCodeValue());
        return response;
    }

    @PostMapping(TriggerApiConstants.MAPPING_SET_UP_CURRENT_MEETING_BY_WORKGROUP_ID)
    public ResponseEntity<IJobInfo> setUpCurrentMeetingByWorkgroupId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl) {
        try {
            return ResponseEntity.accepted().body(triggerBusinessLogic.setUpCurrentMeetingByWorkgroupId(
                    workgroupId,
                    callbackUrl != null ? new URL(callbackUrl) : null));
        } catch (MalformedURLException e) {
            // TODO: Better exception and message
            throw new RuntimeException(e);
        }
    }

    @PostMapping(TriggerApiConstants.MAPPING_CLEAN_UP_CURRENT_MEETING_BY_ROOM_ID)
    public ResponseEntity<IJobInfo> cleanUpCurrentMeetingByRoomId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_ROOM_ID) String roomId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl) {
        try {
            return ResponseEntity.accepted().body(triggerBusinessLogic.cleanUpCurrentMeetingByRoomId(
                    roomId,
                    callbackUrl != null ? new URL(callbackUrl) : null));
        } catch (MalformedURLException e) {
            // TODO: Better exception and message
            throw new RuntimeException(e);
        }
    }

    @PostMapping(TriggerApiConstants.MAPPING_CLEAN_UP_CURRENT_MEETING_BY_WORKGROUP_ID)
    public ResponseEntity<IJobInfo> cleanUpCurrentMeetingByWorkgroupId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl) {
        try {
            return ResponseEntity.accepted().body(triggerBusinessLogic.cleanUpCurrentMeetingByWorkgroupId(
                    workgroupId,
                    callbackUrl != null ? new URL(callbackUrl) : null));
        } catch (MalformedURLException e) {
            // TODO: Better exception and message
            throw new RuntimeException(e);
        }
    }

    @PostMapping(TriggerApiConstants.MAPPING_START_CURRENT_MEETING_BY_ROOM_ID)
    public ResponseEntity<IJobInfo> startCurrentMeetingByRoomId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_ROOM_ID) String roomId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl) {
        try {
            return ResponseEntity.accepted().body(triggerBusinessLogic.startCurrentMeetingByRoomId(
                    roomId,
                    callbackUrl != null ? new URL(callbackUrl) : null));
        } catch (MalformedURLException e) {
            // TODO: Better exception and message
            throw new RuntimeException(e);
        }
    }

    @PostMapping(TriggerApiConstants.MAPPING_START_CURRENT_MEETING_BY_WORKGROUP_ID)
    public ResponseEntity<IJobInfo> startCurrentMeetingByWorkgroupId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl) {
        try {
            return ResponseEntity.accepted().body(triggerBusinessLogic.startCurrentMeetingByWorkgroupId(
                    workgroupId,
                    callbackUrl != null ? new URL(callbackUrl) : null));
        } catch (MalformedURLException e) {
            // TODO: Better exception and message
            throw new RuntimeException(e);
        }
    }

    @PostMapping(TriggerApiConstants.MAPPING_STOP_CURRENT_MEETING_BY_ROOM_ID)
    public ResponseEntity<IJobInfo> stopCurrentMeetingByRoomId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_ROOM_ID) String roomId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl) {
        try {
            return ResponseEntity.accepted().body(triggerBusinessLogic.stopCurrentMeetingByRoomId(
                    roomId,
                    callbackUrl != null ? new URL(callbackUrl) : null));
        } catch (MalformedURLException e) {
            // TODO: Better exception and message
            throw new RuntimeException(e);
        }
    }

    @PostMapping(TriggerApiConstants.MAPPING_STOP_CURRENT_MEETING_BY_WORKGROUP_ID)
    public ResponseEntity<IJobInfo> stopCurrentMeetingByWorkgroupId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl) {
        try {
            return ResponseEntity.accepted().body(triggerBusinessLogic.stopCurrentMeetingByWorkgroupId(
                    workgroupId,
                    callbackUrl != null ? new URL(callbackUrl) : null));
        } catch (MalformedURLException e) {
            // TODO: Better exception and message
            throw new RuntimeException(e);
        }
    }
}
