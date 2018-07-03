package de.qaware.smartlabapi.client;

import de.qaware.smartlabapi.RoomManagementApiConstants;
import de.qaware.smartlabapi.client.generic.IBasicEntityManagementApiClient;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.room.IRoom;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@FeignClient(name = RoomManagementApiConstants.FEIGN_CLIENT_NAME, path = RoomManagementApiConstants.MAPPING_BASE)
public interface IRoomManagementApiClient extends IBasicEntityManagementApiClient<IRoom> {

    @Override
    @GetMapping(RoomManagementApiConstants.MAPPING_FIND_ALL)
    @ResponseBody
    Set<IRoom> findAll();

    @Override
    @GetMapping(RoomManagementApiConstants.MAPPING_FIND_ONE)
    @ResponseBody
    ResponseEntity<IRoom> findOne(@PathVariable(RoomManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId);

    @Override
    @GetMapping(RoomManagementApiConstants.MAPPING_FIND_MULTIPLE)
    @ResponseBody
    ResponseEntity<Set<IRoom>> findMultiple(@RequestParam(RoomManagementApiConstants.PARAMETER_NAME_ROOM_IDS) String[] roomIds);

    @Override
    @PostMapping(value = RoomManagementApiConstants.MAPPING_CREATE_SINGLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<IRoom> create(@RequestBody IRoom room);

    @Override
    @PostMapping(value = RoomManagementApiConstants.MAPPING_CREATE_MULTIPLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<Set<IRoom>> create(@RequestBody Set<IRoom> rooms);

    @Override
    @DeleteMapping(RoomManagementApiConstants.MAPPING_DELETE)
    @ResponseBody
    ResponseEntity<Void> delete(@PathVariable(RoomManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId);

    @GetMapping(RoomManagementApiConstants.MAPPING_GET_MEETINGS_IN_ROOM)
    @ResponseBody
    ResponseEntity<Set<IMeeting>> getMeetingsInRoom(@PathVariable(RoomManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId);

    @GetMapping(RoomManagementApiConstants.MAPPING_GET_CURRENT_MEETING)
    @ResponseBody
    ResponseEntity<IMeeting> getCurrentMeeting(@PathVariable(RoomManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId);

    @PostMapping(RoomManagementApiConstants.MAPPING_EXTEND_CURRENT_MEETING)
    @ResponseBody
    ResponseEntity<Void> extendCurrentMeeting(
            @PathVariable(RoomManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId,
            @RequestParam(RoomManagementApiConstants.PARAMETER_NAME_EXTENSION_IN_MINUTES) long extensionInMinutes);
}