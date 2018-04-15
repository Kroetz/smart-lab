package de.qaware.smartlabcore.room.controller;

import de.qaware.smartlabcore.entity.meeting.IMeeting;
import de.qaware.smartlabcore.entity.room.Room;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "room-management", url = "http://localhost:8080")
public interface IRoomManagementApiClient {

    @GetMapping(RoomManagementController.MAPPING_BASE + RoomManagementController.MAPPING_GET_ROOMS)
    @ResponseBody
    List<Room> getRooms();

    @GetMapping(RoomManagementController.MAPPING_BASE + RoomManagementController.MAPPING_GET_ROOM)
    @ResponseBody
    Optional<Room> getRoom(@PathVariable("roomId") long roomId);

    @PostMapping(value = RoomManagementController.MAPPING_BASE + RoomManagementController.MAPPING_CREATE_ROOM, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    boolean createRoom(@RequestBody Room room);

    @DeleteMapping(RoomManagementController.MAPPING_BASE + RoomManagementController.MAPPING_DELETE_ROOM)
    @ResponseBody
    void deleteRoom(@PathVariable("roomId") long roomId);

    @GetMapping(RoomManagementController.MAPPING_BASE + RoomManagementController.MAPPING_GET_CURRENT_MEETING)
    @ResponseBody
    Optional<IMeeting> getCurrentMeeting(@PathVariable("roomId") long roomId);

    @PostMapping(RoomManagementController.MAPPING_BASE + RoomManagementController.MAPPING_EXTEND_CURRENT_MEETING)
    @ResponseBody
    void extendCurrentMeeting(
            @PathVariable("roomId") long roomId,
            @RequestParam(value = "extension-in-minutes", defaultValue = "10") long extensionInMinutes);

    @GetMapping(RoomManagementController.MAPPING_BASE + RoomManagementController.MAPPING_GET_CURRENT_MEETING_STATUS_PAGE)
    String getCurrentMeetingStatusPage(@PathVariable("roomId") long roomId, Model model);
}