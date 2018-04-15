package de.qaware.smartlabcommons.api.client;

import de.qaware.smartlabcommons.api.TriggerApiConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "trigger", url = "http://localhost:8080")
public interface ITriggerApiClient {

    @PostMapping(TriggerApiConstants.MAPPING_BASE + TriggerApiConstants.MAPPING_SET_UP_CURRENT_MEETING_BY_ROOM_ID)
    void setUpCurrentMeetingByRoomId(@PathVariable("roomId") long roomId);

    @PostMapping(TriggerApiConstants.MAPPING_BASE + TriggerApiConstants.MAPPING_SET_UP_CURRENT_MEETING_BY_WORKGROUP_ID)
    void setUpCurrentMeetingByWorkgroupId(@PathVariable("workgroupId") long workgroupId);

    @PostMapping(TriggerApiConstants.MAPPING_BASE + TriggerApiConstants.MAPPING_CLEAN_UP_CURRENT_MEETING_BY_ROOM_ID)
    void cleanUpCurrentMeetingByRoomId(@PathVariable("roomId") long roomId);

    @PostMapping(TriggerApiConstants.MAPPING_BASE + TriggerApiConstants.MAPPING_CLEAN_UP_CURRENT_MEETING_BY_WORKGROUP_ID)
    void cleanUpCurrentMeetingByWorkgroupId(@PathVariable("workgroupId") long workgroupId);

    @PostMapping(TriggerApiConstants.MAPPING_BASE + TriggerApiConstants.MAPPING_START_CURRENT_MEETING_BY_ROOM_ID)
    void startCurrentMeetingByRoomId(@PathVariable("roomId") long roomId);

    @PostMapping(TriggerApiConstants.MAPPING_BASE + TriggerApiConstants.MAPPING_START_CURRENT_MEETING_BY_WORKGROUP_ID)
    void startCurrentMeetingByWorkgroupId(@PathVariable("workgroupId") long workgroupId);

    @PostMapping(TriggerApiConstants.MAPPING_BASE + TriggerApiConstants.MAPPING_STOP_CURRENT_MEETING_BY_ROOM_ID)
    void stopCurrentMeetingByRoomId(@PathVariable("roomId") long roomId);

    @PostMapping(TriggerApiConstants.MAPPING_BASE + TriggerApiConstants.MAPPING_STOP_CURRENT_MEETING_BY_WORKGROUP_ID)
    void stopCurrentMeetingByWorkgroupId(@PathVariable("workgroupId") long workgroupId);
}
