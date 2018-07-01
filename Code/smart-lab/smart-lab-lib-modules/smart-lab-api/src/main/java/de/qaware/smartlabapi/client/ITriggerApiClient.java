package de.qaware.smartlabapi.client;

import de.qaware.smartlabapi.TriggerApiConstants;
import de.qaware.smartlabcore.data.job.IJobInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URL;

@FeignClient(name = TriggerApiConstants.FEIGN_CLIENT_NAME,path = TriggerApiConstants.MAPPING_BASE)
public interface ITriggerApiClient {

    @PostMapping(TriggerApiConstants.MAPPING_SET_UP_CURRENT_MEETING_BY_ROOM_ID)
    ResponseEntity<IJobInfo> setUpCurrentMeetingByRoomId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_ROOM_ID) String roomId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl);

    @PostMapping(TriggerApiConstants.MAPPING_SET_UP_CURRENT_MEETING_BY_WORKGROUP_ID)
    ResponseEntity<IJobInfo> setUpCurrentMeetingByWorkgroupId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl);

    @PostMapping(TriggerApiConstants.MAPPING_CLEAN_UP_CURRENT_MEETING_BY_ROOM_ID)
    ResponseEntity<IJobInfo> cleanUpCurrentMeetingByRoomId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_ROOM_ID) String roomId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl);

    @PostMapping(TriggerApiConstants.MAPPING_CLEAN_UP_CURRENT_MEETING_BY_WORKGROUP_ID)
    ResponseEntity<IJobInfo> cleanUpCurrentMeetingByWorkgroupId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl);

    @PostMapping(TriggerApiConstants.MAPPING_START_CURRENT_MEETING_BY_ROOM_ID)
    ResponseEntity<IJobInfo> startCurrentMeetingByRoomId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_ROOM_ID) String roomId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl);

    @PostMapping(TriggerApiConstants.MAPPING_START_CURRENT_MEETING_BY_WORKGROUP_ID)
    ResponseEntity<IJobInfo> startCurrentMeetingByWorkgroupId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl);

    @PostMapping(TriggerApiConstants.MAPPING_STOP_CURRENT_MEETING_BY_ROOM_ID)
    ResponseEntity<IJobInfo> stopCurrentMeetingByRoomId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_ROOM_ID) String roomId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl);

    @PostMapping(TriggerApiConstants.MAPPING_STOP_CURRENT_MEETING_BY_WORKGROUP_ID)
    ResponseEntity<IJobInfo> stopCurrentMeetingByWorkgroupId(
            @PathVariable(TriggerApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(value = TriggerApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl);

    @GetMapping(TriggerApiConstants.MAPPING_GET_BASE_URL)
    ResponseEntity<URL> getBaseUrl();
}
