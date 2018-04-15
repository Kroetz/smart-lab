package de.qaware.smartlabcore.room.controller;

import de.qaware.smartlabcore.entity.meeting.IMeeting;
import de.qaware.smartlabcore.entity.room.Room;
import de.qaware.smartlabcore.room.service.IRoomManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public static final String MAPPING_GET_CURRENT_MEETING = "/{roomId}/current-meeting";
    public static final String MAPPING_EXTEND_CURRENT_MEETING = "/{roomId}/extend-current-meeting";
    public static final String MAPPING_GET_CURRENT_MEETING_STATUS_PAGE = "/{roomId}/current-meeting-status-page";

    private final IRoomManagementService roomService;

    @Autowired
    public RoomManagementController(@Qualifier("mock") IRoomManagementService roomService) {
        this.roomService = roomService;
    }

    @GetMapping(MAPPING_GET_ROOMS)
    @ResponseBody
    public List<Room> getRooms() {
        return roomService.getRooms();
    }

    @GetMapping(MAPPING_GET_ROOM)
    @ResponseBody
    public Optional<Room> getRoom(@PathVariable("roomId") long roomId) {
        return roomService.getRoom(roomId);
    }

    @PostMapping(value = MAPPING_CREATE_ROOM,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public boolean createRoom(@RequestBody Room room) {
        return roomService.createRoom(room);
    }

    @DeleteMapping(MAPPING_DELETE_ROOM)
    @ResponseBody
    public void deleteRoom(@PathVariable("roomId") long roomId) {
        roomService.deleteRoom(roomId);
    }

    @GetMapping(MAPPING_GET_CURRENT_MEETING)
    @ResponseBody
    public Optional<IMeeting> getCurrentMeeting(@PathVariable("roomId") long roomId) {
        return roomService.getCurrentMeeting(roomId);
    }

    @PostMapping(MAPPING_EXTEND_CURRENT_MEETING)
    @ResponseBody
    public void extendCurrentMeeting(
            @PathVariable("roomId") long roomId,
            @RequestParam(value = "extension-in-minutes", defaultValue = "10") long extensionInMinutes) {
        roomService.extendCurrentMeeting(roomId, extensionInMinutes);
    }

    @GetMapping(MAPPING_GET_CURRENT_MEETING_STATUS_PAGE)
    public String getCurrentMeetingStatusPage(@PathVariable("roomId") long roomId, Model model) {
        return roomService.getCurrentMeetingStatusPage(roomId, model);
    }
}
