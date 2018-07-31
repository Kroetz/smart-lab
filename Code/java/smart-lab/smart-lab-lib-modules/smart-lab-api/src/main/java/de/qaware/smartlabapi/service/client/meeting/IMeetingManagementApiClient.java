package de.qaware.smartlabapi.service.client.meeting;

import de.qaware.smartlabapi.service.constant.meeting.MeetingManagementApiConstants;
import de.qaware.smartlabapi.service.client.generic.IBasicEntityManagementApiClient;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.Set;

@FeignClient(name = MeetingManagementApiConstants.FEIGN_CLIENT_NAME, path = MeetingManagementApiConstants.MAPPING_BASE)
public interface IMeetingManagementApiClient extends IBasicEntityManagementApiClient<IMeeting> {

    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_ALL)
    @ResponseBody
    Set<IMeeting> findAll();

    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_ALL_BY_LOCATION_ID)
    @ResponseBody
    Set<IMeeting> findAllByLocationId(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId);

    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_ALL_BY_WORKGROUP_ID)
    @ResponseBody
    Set<IMeeting> findAllByWorkgroupId(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId);

    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_ALL_CURRENT)
    @ResponseBody
    Set<IMeeting> findAllCurrent();

    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_ONE)
    @ResponseBody
    ResponseEntity<IMeeting> findOne(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId);

    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_MULTIPLE)
    @ResponseBody
    ResponseEntity<Set<IMeeting>> findMultiple(
            @RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_IDS) String[] meetingIds);

    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_CURRENT_BY_LOCATION_ID)
    @ResponseBody
    ResponseEntity<IMeeting> findCurrentByLocationId(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId);

    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_CURRENT_BY_WORKGROUP_ID)
    @ResponseBody
    ResponseEntity<IMeeting> findCurrentByWorkgroupId(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId);

    @PostMapping(value = MeetingManagementApiConstants.MAPPING_CREATE_SINGLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<IMeeting> create(@RequestBody IMeeting meeting);

    @PostMapping(value = MeetingManagementApiConstants.MAPPING_CREATE_MULTIPLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<Set<IMeeting>> create(@RequestBody Set<IMeeting> meetings);

    @DeleteMapping(MeetingManagementApiConstants.MAPPING_DELETE)
    @ResponseBody
    ResponseEntity<Void> delete(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId);

    @PutMapping(MeetingManagementApiConstants.MAPPING_SHORTEN_MEETING)
    @ResponseBody
    ResponseEntity<Void> shortenMeeting(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId,
            @RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_SHORTENING_IN_MINUTES) long shorteningInMinutes);

    @PutMapping(MeetingManagementApiConstants.MAPPING_EXTEND_MEETING)
    @ResponseBody
    ResponseEntity<Void> extendMeeting(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId,
            @RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_EXTENSION_IN_MINUTES) long extensionInMinutes);

    @PutMapping(MeetingManagementApiConstants.MAPPING_SHIFT_MEETING)
    @ResponseBody
    ResponseEntity<Void> shiftMeeting(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId,
            @RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_SHIFT_IN_MINUTES) long shiftInMinutes);

    @GetMapping(MeetingManagementApiConstants.MAPPING_GET_BASE_URL)
    ResponseEntity<URL> getBaseUrl();
}
