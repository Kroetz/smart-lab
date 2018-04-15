package de.qaware.smartlabcommons.api.client;

import de.qaware.smartlabcommons.api.RoomManagementApiConstants;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.Room;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "room-management", url = "http://localhost:8080")
public interface IRoomManagementApiClient {

    @GetMapping(RoomManagementApiConstants.MAPPING_BASE + RoomManagementApiConstants.MAPPING_GET_ROOMS)
    @ResponseBody
    List<Room> getRooms();

    @GetMapping(RoomManagementApiConstants.MAPPING_BASE + RoomManagementApiConstants.MAPPING_GET_ROOM)
    @ResponseBody
    Optional<Room> getRoom(@PathVariable("roomId") long roomId);

    @PostMapping(value = RoomManagementApiConstants.MAPPING_BASE + RoomManagementApiConstants.MAPPING_CREATE_ROOM, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    boolean createRoom(@RequestBody Room room);

    @DeleteMapping(RoomManagementApiConstants.MAPPING_BASE + RoomManagementApiConstants.MAPPING_DELETE_ROOM)
    @ResponseBody
    void deleteRoom(@PathVariable("roomId") long roomId);

    @GetMapping(RoomManagementApiConstants.MAPPING_BASE + RoomManagementApiConstants.MAPPING_GET_MEETINGS_IN_ROOM)
    List<IMeeting> getMeetingsInRoom(@PathVariable("roomId") long roomId);

    @GetMapping(RoomManagementApiConstants.MAPPING_BASE + RoomManagementApiConstants.MAPPING_GET_CURRENT_MEETING)
    @ResponseBody
    Optional<IMeeting> getCurrentMeeting(@PathVariable("roomId") long roomId);

    @PostMapping(RoomManagementApiConstants.MAPPING_BASE + RoomManagementApiConstants.MAPPING_EXTEND_CURRENT_MEETING)
    @ResponseBody
    boolean extendCurrentMeeting(
            @PathVariable("roomId") long roomId,
            @RequestParam(value = "extension-in-minutes", defaultValue = "10") long extensionInMinutes);

    @GetMapping(RoomManagementApiConstants.MAPPING_BASE + RoomManagementApiConstants.MAPPING_GET_CURRENT_MEETING_STATUS_PAGE)
    String getCurrentMeetingStatusPage(@PathVariable("roomId") long roomId, Model model);
}