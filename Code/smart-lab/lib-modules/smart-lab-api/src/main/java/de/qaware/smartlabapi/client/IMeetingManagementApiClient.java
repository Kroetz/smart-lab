package de.qaware.smartlabapi.client;

import de.qaware.smartlabapi.MeetingManagementApiConstants;
import de.qaware.smartlabapi.client.generic.IEntityManagementApiClient;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@FeignClient(MeetingManagementApiConstants.FEIGN_CLIENT_VALUE)
@Component
public interface IMeetingManagementApiClient extends IEntityManagementApiClient<IMeeting> {

    @Override
    @GetMapping(MeetingManagementApiConstants.MAPPING_BASE + MeetingManagementApiConstants.MAPPING_FIND_ALL)
    Set<IMeeting> findAll();

    @Override
    @GetMapping(MeetingManagementApiConstants.MAPPING_BASE + MeetingManagementApiConstants.MAPPING_FIND_ONE)
    ResponseEntity<IMeeting> findOne(@PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId);

    @Override
    @GetMapping(MeetingManagementApiConstants.MAPPING_BASE + MeetingManagementApiConstants.MAPPING_FIND_MULTIPLE)
    ResponseEntity<Set<IMeeting>> findMultiple(@RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_IDS) String[] meetingIds);

    @Override
    @PostMapping(value = MeetingManagementApiConstants.MAPPING_BASE + MeetingManagementApiConstants.MAPPING_CREATE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> create(@RequestBody IMeeting meeting);

    @Override
    @DeleteMapping(MeetingManagementApiConstants.MAPPING_BASE + MeetingManagementApiConstants.MAPPING_DELETE)
    ResponseEntity<Void> delete(@PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId);

    @PutMapping(MeetingManagementApiConstants.MAPPING_BASE + MeetingManagementApiConstants.MAPPING_SHORTEN_MEETING)
    ResponseEntity<Void> shortenMeeting(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId,
            @RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_SHORTENING_IN_MINUTES) long shorteningInMinutes);

    @PutMapping(MeetingManagementApiConstants.MAPPING_BASE + MeetingManagementApiConstants.MAPPING_EXTEND_MEETING)
    ResponseEntity<Void> extendMeeting(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId,
            @RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_EXTENSION_IN_MINUTES) long extensionInMinutes);

    @PutMapping(MeetingManagementApiConstants.MAPPING_BASE + MeetingManagementApiConstants.MAPPING_SHIFT_MEETING)
    ResponseEntity<Void> shiftMeeting(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId,
            @RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_SHIFT_IN_MINUTES) long shiftInMinutes);
}
