package de.qaware.smartlabroom.controller;

import de.qaware.smartlabapi.RoomManagementApiConstants;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.generic.controller.AbstractSmartLabController;
import de.qaware.smartlabcore.generic.controller.IBasicEntityManagementController;
import de.qaware.smartlabroom.business.IRoomManagementBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping(RoomManagementApiConstants.MAPPING_BASE)
@Slf4j
public class RoomManagementController extends AbstractSmartLabController implements IBasicEntityManagementController<IRoom> {

    private final IRoomManagementBusinessLogic roomManagementBusinessLogic;

    public RoomManagementController(IRoomManagementBusinessLogic roomManagementBusinessLogic) {
        this.roomManagementBusinessLogic = roomManagementBusinessLogic;
    }

    @GetMapping(RoomManagementApiConstants.MAPPING_FIND_ALL)
    @ResponseBody
    public Set<IRoom> findAll() {
        return roomManagementBusinessLogic.findAll();
    }

    @GetMapping(RoomManagementApiConstants.MAPPING_FIND_ONE)
    @ResponseBody
    public ResponseEntity<IRoom> findOne(@PathVariable(RoomManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId) {
        return responseFromOptional(roomManagementBusinessLogic.findOne(RoomId.of(roomId)));
    }

    @GetMapping(RoomManagementApiConstants.MAPPING_FIND_MULTIPLE)
    public ResponseEntity<Set<IRoom>> findMultiple(@RequestParam(RoomManagementApiConstants.PARAMETER_NAME_ROOM_IDS) String[] roomIds) {
        return responseFromOptionals(roomManagementBusinessLogic.findMultiple(
                Arrays.stream(roomIds)
                        .map(RoomId::of)
                        .collect(Collectors.toSet())));
    }

    @PostMapping(value = RoomManagementApiConstants.MAPPING_CREATE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> create(@RequestBody IRoom room) {
        return roomManagementBusinessLogic.create(room).toResponseEntity();
    }

    @DeleteMapping(RoomManagementApiConstants.MAPPING_DELETE)
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable(RoomManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId) {
        return roomManagementBusinessLogic.delete(RoomId.of(roomId)).toResponseEntity();
    }

    @GetMapping(RoomManagementApiConstants.MAPPING_GET_MEETINGS_IN_ROOM)
    public ResponseEntity<Set<IMeeting>> getMeetingsInRoom(@PathVariable(RoomManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId) {
        return responseFromOptional(roomManagementBusinessLogic.getMeetingsInRoom(RoomId.of(roomId)));
    }

    @GetMapping(RoomManagementApiConstants.MAPPING_GET_CURRENT_MEETING)
    @ResponseBody
    public ResponseEntity<IMeeting> getCurrentMeeting(@PathVariable(RoomManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId) {
        return responseFromOptional(roomManagementBusinessLogic.getCurrentMeeting(RoomId.of(roomId)));
    }

    @PostMapping(RoomManagementApiConstants.MAPPING_EXTEND_CURRENT_MEETING)
    @ResponseBody
    public ResponseEntity<Void> extendCurrentMeeting(
            @PathVariable(RoomManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId,
            @RequestParam(RoomManagementApiConstants.PARAMETER_NAME_EXTENSION_IN_MINUTES) long extensionInMinutes) {
        return roomManagementBusinessLogic.extendCurrentMeeting(RoomId.of(roomId), Duration.ofMinutes(extensionInMinutes)).toResponseEntity();
    }

    @GetMapping(RoomManagementApiConstants.MAPPING_GET_CURRENT_MEETING_STATUS_PAGE)
    public String getCurrentMeetingStatusPage(@PathVariable(RoomManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId, Model model) {
        return roomManagementBusinessLogic.getCurrentMeetingStatusPage(RoomId.of(roomId), model);
    }
}
