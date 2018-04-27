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
import java.util.Set;

@Controller
@RequestMapping(RoomManagementApiConstants.MAPPING_BASE)
@Slf4j
public class RoomManagementController extends AbstractSmartLabController {

    private final IRoomManagementService roomManagementService;

    public RoomManagementController(IRoomManagementService roomManagementService) {
        this.roomManagementService = roomManagementService;
    }

    @GetMapping(RoomManagementApiConstants.MAPPING_FIND_ALL)
    @ResponseBody
    public Set<IRoom> findAll() {
        return roomManagementService.findAll();
    }

    @GetMapping(RoomManagementApiConstants.MAPPING_FIND_ONE)
    @ResponseBody
    public ResponseEntity<IRoom> findOne(@PathVariable(RoomManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId) {
        return responseFromOptional(roomManagementService.findOne(roomId));
    }

    @PostMapping(value = RoomManagementApiConstants.MAPPING_CREATE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> create(@RequestBody IRoom room) {
        return roomManagementService.create(room).toResponseEntity();
    }

    @DeleteMapping(RoomManagementApiConstants.MAPPING_DELETE)
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable(RoomManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId) {
        return roomManagementService.delete(roomId).toResponseEntity();
    }

    @GetMapping(RoomManagementApiConstants.MAPPING_GET_MEETINGS_IN_ROOM)
    public ResponseEntity<Set<IMeeting>> getMeetingsInRoom(@PathVariable(RoomManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId) {
        return responseFromOptional(roomManagementService.getMeetingsInRoom(roomId));
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
