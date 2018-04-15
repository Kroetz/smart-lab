package de.qaware.smartlabroomconfigprovider.controller;

import de.qaware.smartlabcore.entity.room.Room;
import de.qaware.smartlabroomconfigprovider.service.IRoomConfigProviderMockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/smart-lab/room-config-provider")
public class RoomConfigProviderMockController {

    private final IRoomConfigProviderMockService roomConfigProviderService;

    @Autowired
    public RoomConfigProviderMockController(IRoomConfigProviderMockService roomConfigProviderService) {
        this.roomConfigProviderService = roomConfigProviderService;
    }

    @PostMapping("/{roomId}/exists")
    boolean exists(@PathVariable("roomId") long roomId) {
        return roomConfigProviderService.exists(roomId);
    }

    @GetMapping
    List<Room> getRooms() {
        return roomConfigProviderService.getRooms();
    }

    @GetMapping("/{roomId}")
    Optional<Room> getRoom(@PathVariable("roomId") long roomId) {
        Optional<Room> r =  roomConfigProviderService.getRoom(roomId);
        return r;


        //return roomConfigProviderService.getRoom(roomId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean createRoom(@RequestBody Room room) {
        return roomConfigProviderService.createRoom(room);
    }

    @DeleteMapping("/{roomId}")
    boolean deleteRoom(@PathVariable("roomId") long roomId) {
        return roomConfigProviderService.deleteRoom(roomId);
    }
}
