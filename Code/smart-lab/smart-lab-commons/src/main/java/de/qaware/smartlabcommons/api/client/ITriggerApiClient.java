package de.qaware.smartlabcommons.api.client;

import de.qaware.smartlabcommons.api.TriggerApiConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        value = TriggerApiConstants.FEIGN_CLIENT_VALUE,
        url = TriggerApiConstants.FEIGN_CLIENT_URL)
@Component
public interface ITriggerApiClient {

    @PostMapping(TriggerApiConstants.MAPPING_BASE + TriggerApiConstants.MAPPING_SET_UP_CURRENT_MEETING_BY_ROOM_ID)
    ResponseEntity<Void> setUpCurrentMeetingByRoomId(@PathVariable(TriggerApiConstants.PARAMETER_NAME_ROOM_ID) String roomId);

    @PostMapping(TriggerApiConstants.MAPPING_BASE + TriggerApiConstants.MAPPING_SET_UP_CURRENT_MEETING_BY_WORKGROUP_ID)
    ResponseEntity<Void> setUpCurrentMeetingByWorkgroupId(@PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId);

    @PostMapping(TriggerApiConstants.MAPPING_BASE + TriggerApiConstants.MAPPING_CLEAN_UP_CURRENT_MEETING_BY_ROOM_ID)
    ResponseEntity<Void> cleanUpCurrentMeetingByRoomId(@PathVariable(TriggerApiConstants.PARAMETER_NAME_ROOM_ID) String roomId);

    @PostMapping(TriggerApiConstants.MAPPING_BASE + TriggerApiConstants.MAPPING_CLEAN_UP_CURRENT_MEETING_BY_WORKGROUP_ID)
    ResponseEntity<Void> cleanUpCurrentMeetingByWorkgroupId(@PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId);

    @PostMapping(TriggerApiConstants.MAPPING_BASE + TriggerApiConstants.MAPPING_START_CURRENT_MEETING_BY_ROOM_ID)
    ResponseEntity<Void> startCurrentMeetingByRoomId(@PathVariable(TriggerApiConstants.PARAMETER_NAME_ROOM_ID) String roomId);

    @PostMapping(TriggerApiConstants.MAPPING_BASE + TriggerApiConstants.MAPPING_START_CURRENT_MEETING_BY_WORKGROUP_ID)
    ResponseEntity<Void> startCurrentMeetingByWorkgroupId(@PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId);

    @PostMapping(TriggerApiConstants.MAPPING_BASE + TriggerApiConstants.MAPPING_STOP_CURRENT_MEETING_BY_ROOM_ID)
    ResponseEntity<Void> stopCurrentMeetingByRoomId(@PathVariable(TriggerApiConstants.PARAMETER_NAME_ROOM_ID) String roomId);

    @PostMapping(TriggerApiConstants.MAPPING_BASE + TriggerApiConstants.MAPPING_STOP_CURRENT_MEETING_BY_WORKGROUP_ID)
    ResponseEntity<Void> stopCurrentMeetingByWorkgroupId(@PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId);
}
