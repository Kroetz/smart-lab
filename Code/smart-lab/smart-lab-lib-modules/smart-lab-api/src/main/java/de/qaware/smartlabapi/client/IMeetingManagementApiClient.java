package de.qaware.smartlabapi.client;

import de.qaware.smartlabapi.MeetingManagementApiConstants;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.room.RoomId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@FeignClient(name = MeetingManagementApiConstants.FEIGN_CLIENT_VALUE, path = MeetingManagementApiConstants.MAPPING_BASE)
public interface IMeetingManagementApiClient {

    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_ALL)
    Map<RoomId, Set<IMeeting>> findAll();

    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_ALL_BY_ROOM_ID)
    Set<IMeeting> findAll(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId);

    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_ONE)
    ResponseEntity<IMeeting> findOne(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId,
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId);

    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_MULTIPLE)
    ResponseEntity<Set<IMeeting>> findMultiple(
            @RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_IDS) String[] meetingIds,
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId);

    @PostMapping(value = MeetingManagementApiConstants.MAPPING_CREATE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> create(@RequestBody IMeeting meeting);

    @DeleteMapping(MeetingManagementApiConstants.MAPPING_DELETE)
    ResponseEntity<Void> delete(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId,
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId);

    @PutMapping(MeetingManagementApiConstants.MAPPING_SHORTEN_MEETING)
    ResponseEntity<Void> shortenMeeting(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId,
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId,
            @RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_SHORTENING_IN_MINUTES) long shorteningInMinutes);

    @PutMapping(MeetingManagementApiConstants.MAPPING_EXTEND_MEETING)
    ResponseEntity<Void> extendMeeting(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId,
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId,
            @RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_EXTENSION_IN_MINUTES) long extensionInMinutes);

    @PutMapping(MeetingManagementApiConstants.MAPPING_SHIFT_MEETING)
    ResponseEntity<Void> shiftMeeting(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId,
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId,
            @RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_SHIFT_IN_MINUTES) long shiftInMinutes);
}
