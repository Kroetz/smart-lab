package de.qaware.smartlabcommons.api.client;

import de.qaware.smartlabcommons.api.RoomManagementApiConstants;
import de.qaware.smartlabcommons.api.client.generic.ICrudApiClient;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        value = RoomManagementApiConstants.FEIGN_CLIENT_VALUE,
        url = RoomManagementApiConstants.FEIGN_CLIENT_URL)
@Component
public interface IRoomManagementApiClient extends ICrudApiClient<IRoom> {

    @Override
    @GetMapping(RoomManagementApiConstants.MAPPING_BASE + RoomManagementApiConstants.MAPPING_GET_ROOMS)
    @ResponseBody
    List<IRoom> findAll();

    @Override
    @GetMapping(RoomManagementApiConstants.MAPPING_BASE + RoomManagementApiConstants.MAPPING_GET_ROOM)
    @ResponseBody
    ResponseEntity<IRoom> findOne(@PathVariable(RoomManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId);

    @Override
    @PostMapping(value = RoomManagementApiConstants.MAPPING_BASE + RoomManagementApiConstants.MAPPING_CREATE_ROOM, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<Void> create(@RequestBody IRoom room);

    @Override
    @DeleteMapping(RoomManagementApiConstants.MAPPING_BASE + RoomManagementApiConstants.MAPPING_DELETE_ROOM)
    @ResponseBody
    ResponseEntity<Void> delete(@PathVariable(RoomManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId);

    @GetMapping(RoomManagementApiConstants.MAPPING_BASE + RoomManagementApiConstants.MAPPING_GET_MEETINGS_IN_ROOM)
    ResponseEntity<List<IMeeting>> getMeetingsInRoom(@PathVariable(RoomManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId);

    @GetMapping(RoomManagementApiConstants.MAPPING_BASE + RoomManagementApiConstants.MAPPING_GET_CURRENT_MEETING)
    @ResponseBody
    ResponseEntity<IMeeting> getCurrentMeeting(@PathVariable(RoomManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId);

    @PostMapping(RoomManagementApiConstants.MAPPING_BASE + RoomManagementApiConstants.MAPPING_EXTEND_CURRENT_MEETING)
    @ResponseBody
    ResponseEntity<Void> extendCurrentMeeting(
            @PathVariable(RoomManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId,
            @RequestParam(RoomManagementApiConstants.PARAMETER_NAME_EXTENSION_IN_MINUTES) long extensionInMinutes);

    @GetMapping(RoomManagementApiConstants.MAPPING_BASE + RoomManagementApiConstants.MAPPING_GET_CURRENT_MEETING_STATUS_PAGE)
    String getCurrentMeetingStatusPage(@PathVariable(RoomManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId, Model model);
}