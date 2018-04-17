package de.qaware.smartlabcore.room.controller;

import de.qaware.smartlabcommons.api.management.RoomManagementApiConstants;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcore.generic.controller.AbstractSmartLabController;
import de.qaware.smartlabcore.room.service.IRoomManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@Controller
@RequestMapping(RoomManagementApiConstants.MAPPING_BASE)
@Slf4j
public class RoomManagementController extends AbstractSmartLabController {

    private final IRoomManagementService roomManagementService;

    public RoomManagementController(IRoomManagementService roomManagementService) {
        this.roomManagementService = roomManagementService;
    }

    @GetMapping(RoomManagementApiConstants.MAPPING_GET_ROOMS)
    @ResponseBody
    public List<IRoom> getRooms() {
        return roomManagementService.getRooms();
    }

    @GetMapping(RoomManagementApiConstants.MAPPING_GET_ROOM)
    @ResponseBody
    public ResponseEntity<IRoom> getRoom(@PathVariable("roomId") long roomId) {
        return responseFromOptional(roomManagementService.getRoom(roomId));
    }

    @PostMapping(value = RoomManagementApiConstants.MAPPING_CREATE_ROOM,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public boolean createRoom(@RequestBody IRoom room) {
        return roomManagementService.createRoom(room);
    }

    @DeleteMapping(RoomManagementApiConstants.MAPPING_DELETE_ROOM)
    @ResponseBody
    public void deleteRoom(@PathVariable("roomId") long roomId) {
        roomManagementService.deleteRoom(roomId);
    }

    @GetMapping(RoomManagementApiConstants.MAPPING_GET_MEETINGS_IN_ROOM)
    List<IMeeting> getMeetingsInRoom(@PathVariable("roomId") long roomId) {
        return roomManagementService.getMeetingsInRoom(roomId);
    }

    @GetMapping(RoomManagementApiConstants.MAPPING_GET_CURRENT_MEETING)
    @ResponseBody
    public ResponseEntity<IMeeting> getCurrentMeeting(@PathVariable("roomId") long roomId) {
        return responseFromOptional(roomManagementService.getCurrentMeeting(roomId));
    }

    @PostMapping(RoomManagementApiConstants.MAPPING_EXTEND_CURRENT_MEETING)
    @ResponseBody
    public boolean extendCurrentMeeting(
            @PathVariable("roomId") long roomId,
            @RequestParam(value = "extension-in-minutes", defaultValue = "10") long extensionInMinutes) {
        return roomManagementService.extendCurrentMeeting(roomId, Duration.ofMinutes(extensionInMinutes));
    }

    @GetMapping(RoomManagementApiConstants.MAPPING_GET_CURRENT_MEETING_STATUS_PAGE)
    public String getCurrentMeetingStatusPage(@PathVariable("roomId") long roomId, Model model) {
        return roomManagementService.getCurrentMeetingStatusPage(roomId, model);
    }
}
