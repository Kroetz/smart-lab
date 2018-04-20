package de.qaware.smartlabcommons.api.client;

import de.qaware.smartlabcommons.api.RoomManagementApiConstants;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "room-management", url = "http://localhost:8080")
@Component
public interface IRoomManagementApiClient {

    @GetMapping(RoomManagementApiConstants.MAPPING_BASE + RoomManagementApiConstants.MAPPING_GET_ROOMS)
    @ResponseBody
    List<IRoom> getRooms();

    @GetMapping(RoomManagementApiConstants.MAPPING_BASE + RoomManagementApiConstants.MAPPING_GET_ROOM)
    @ResponseBody
    ResponseEntity<IRoom> getRoom(@PathVariable("roomId") String roomId);

    @PostMapping(value = RoomManagementApiConstants.MAPPING_BASE + RoomManagementApiConstants.MAPPING_CREATE_ROOM, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    boolean createRoom(@RequestBody IRoom room);

    @DeleteMapping(RoomManagementApiConstants.MAPPING_BASE + RoomManagementApiConstants.MAPPING_DELETE_ROOM)
    @ResponseBody
    boolean deleteRoom(@PathVariable("roomId") String roomId);

    @GetMapping(RoomManagementApiConstants.MAPPING_BASE + RoomManagementApiConstants.MAPPING_GET_MEETINGS_IN_ROOM)
    List<IMeeting> getMeetingsInRoom(@PathVariable("roomId") String roomId);

    @GetMapping(RoomManagementApiConstants.MAPPING_BASE + RoomManagementApiConstants.MAPPING_GET_CURRENT_MEETING)
    @ResponseBody
    ResponseEntity<IMeeting> getCurrentMeeting(@PathVariable("roomId") String roomId);

    @PostMapping(RoomManagementApiConstants.MAPPING_BASE + RoomManagementApiConstants.MAPPING_EXTEND_CURRENT_MEETING)
    @ResponseBody
    boolean extendCurrentMeeting(
            @PathVariable("roomId") String roomId,
            @RequestParam(value = "extension-in-minutes", defaultValue = "10") long extensionInMinutes);

    @GetMapping(RoomManagementApiConstants.MAPPING_BASE + RoomManagementApiConstants.MAPPING_GET_CURRENT_MEETING_STATUS_PAGE)
    String getCurrentMeetingStatusPage(@PathVariable("roomId") String roomId, Model model);
}