package de.qaware.smartlabcore.room.controller;

import de.qaware.smartlabcommons.api.RoomManagementApiConstants;
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
    public ResponseEntity<IRoom> getRoom(@PathVariable(RoomManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId) {
        return responseFromOptional(roomManagementService.getRoom(roomId));
    }

    @PostMapping(value = RoomManagementApiConstants.MAPPING_CREATE_ROOM,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> createRoom(@RequestBody IRoom room) {
        return roomManagementService.createRoom(room).toResponseEntity();
    }

    @DeleteMapping(RoomManagementApiConstants.MAPPING_DELETE_ROOM)
    @ResponseBody
    public ResponseEntity<Void> deleteRoom(@PathVariable(RoomManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId) {
        return roomManagementService.deleteRoom(roomId).toResponseEntity();
    }

    @GetMapping(RoomManagementApiConstants.MAPPING_GET_MEETINGS_IN_ROOM)
    List<IMeeting> getMeetingsInRoom(@PathVariable(RoomManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId) {
        return roomManagementService.getMeetingsInRoom(roomId);
    }

    @GetMapping(RoomManagementApiConstants.MAPPING_GET_CURRENT_MEETING)
    @ResponseBody
    public ResponseEntity<IMeeting> getCurrentMeeting(@PathVariable(RoomManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId) {
        return responseFromOptional(roomManagementService.getCurrentMeeting(roomId));
    }

    @PostMapping(RoomManagementApiConstants.MAPPING_EXTEND_CURRENT_MEETING)
    @ResponseBody
    public ResponseEntity<Void> extendCurrentMeeting(
            @PathVariable(RoomManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId,
            @RequestParam(RoomManagementApiConstants.PARAMETER_NAME_EXTENSION_IN_MINUTES) long extensionInMinutes) {
        return roomManagementService.extendCurrentMeeting(roomId, Duration.ofMinutes(extensionInMinutes)).toResponseEntity();
    }

    @GetMapping(RoomManagementApiConstants.MAPPING_GET_CURRENT_MEETING_STATUS_PAGE)
    public String getCurrentMeetingStatusPage(@PathVariable(RoomManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId, Model model) {
        return roomManagementService.getCurrentMeetingStatusPage(roomId, model);
    }
}
