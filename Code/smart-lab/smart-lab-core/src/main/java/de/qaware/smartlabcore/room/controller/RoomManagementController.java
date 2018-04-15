package de.qaware.smartlabcore.room.controller;

import de.qaware.smartlabcore.entity.meeting.IMeeting;
import de.qaware.smartlabcore.entity.room.Room;
import de.qaware.smartlabcore.room.service.IRoomManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(RoomManagementController.MAPPING_BASE)
@Slf4j
public class RoomManagementController {

    public static final String MAPPING_BASE = "/smart-lab/api/room";
    public static final String MAPPING_GET_ROOMS = "";
    public static final String MAPPING_GET_ROOM = "/{roomId}";
    public static final String MAPPING_CREATE_ROOM = "";
    public static final String MAPPING_DELETE_ROOM = "/{roomId}";
    public static final String MAPPING_GET_MEETINGS_IN_ROOM = "/{roomId}/meetings";
    public static final String MAPPING_GET_CURRENT_MEETING = "/{roomId}/current-meeting";
    public static final String MAPPING_EXTEND_CURRENT_MEETING = "/{roomId}/extend-current-meeting";
    public static final String MAPPING_GET_CURRENT_MEETING_STATUS_PAGE = "/{roomId}/current-meeting-status-page";

    private final IRoomManagementService roomManagementService;

    public RoomManagementController(@Qualifier("mock") IRoomManagementService roomManagementService) {
        this.roomManagementService = roomManagementService;
    }

    @GetMapping(MAPPING_GET_ROOMS)
    @ResponseBody
    public List<Room> getRooms() {
        return roomManagementService.getRooms();
    }

    @GetMapping(MAPPING_GET_ROOM)
    @ResponseBody
    public Optional<Room> getRoom(@PathVariable("roomId") long roomId) {
        return roomManagementService.getRoom(roomId);
    }

    @PostMapping(value = MAPPING_CREATE_ROOM,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public boolean createRoom(@RequestBody Room room) {
        return roomManagementService.createRoom(room);
    }

    @DeleteMapping(MAPPING_DELETE_ROOM)
    @ResponseBody
    public void deleteRoom(@PathVariable("roomId") long roomId) {
        roomManagementService.deleteRoom(roomId);
    }

    @GetMapping(MAPPING_GET_MEETINGS_IN_ROOM)
    List<IMeeting> getMeetingsInRoom(@PathVariable("roomId") long roomId) {
        return roomManagementService.getMeetingsInRoom(roomId);
    }

    @GetMapping(MAPPING_GET_CURRENT_MEETING)
    @ResponseBody
    public Optional<IMeeting> getCurrentMeeting(@PathVariable("roomId") long roomId) {
        return roomManagementService.getCurrentMeeting(roomId);
    }

    @PostMapping(MAPPING_EXTEND_CURRENT_MEETING)
    @ResponseBody
    public boolean extendCurrentMeeting(
            @PathVariable("roomId") long roomId,
            @RequestParam(value = "extension-in-minutes", defaultValue = "10") long extensionInMinutes) {
        return roomManagementService.extendCurrentMeeting(roomId, extensionInMinutes);
    }

    @GetMapping(MAPPING_GET_CURRENT_MEETING_STATUS_PAGE)
    public String getCurrentMeetingStatusPage(@PathVariable("roomId") long roomId, Model model) {
        return roomManagementService.getCurrentMeetingStatusPage(roomId, model);
    }
}
