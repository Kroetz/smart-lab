package de.qaware.smartlabroomconfigprovidermock.controller;

import de.qaware.smartlabcommons.api.configprovidermock.RoomConfigProviderMockApiConstants;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcore.generic.controller.AbstractSmartLabController;
import de.qaware.smartlabroomconfigprovidermock.service.IRoomConfigProviderMockService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RoomConfigProviderMockApiConstants.MAPPING_BASE)
public class RoomConfigProviderMockController extends AbstractSmartLabController {

    private final IRoomConfigProviderMockService roomConfigProviderService;

    public RoomConfigProviderMockController(IRoomConfigProviderMockService roomConfigProviderService) {
        this.roomConfigProviderService = roomConfigProviderService;
    }

    @GetMapping(RoomConfigProviderMockApiConstants.MAPPING_GET_ROOMS)
    List<IRoom> getRooms() {
        return roomConfigProviderService.getRooms();
    }

    @GetMapping(RoomConfigProviderMockApiConstants.MAPPING_GET_ROOM)
    ResponseEntity<IRoom> getRoom(@PathVariable("roomId") long roomId) {
        return responseFromOptional(roomConfigProviderService.getRoom(roomId));
    }

    @PostMapping(
            value = RoomConfigProviderMockApiConstants.MAPPING_CREATE_ROOM,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean createRoom(@RequestBody IRoom room) {
        return roomConfigProviderService.createRoom(room);
    }

    @DeleteMapping(RoomConfigProviderMockApiConstants.MAPPING_DELETE_ROOM)
    boolean deleteRoom(@PathVariable("roomId") long roomId) {
        return roomConfigProviderService.deleteRoom(roomId);
    }
}
