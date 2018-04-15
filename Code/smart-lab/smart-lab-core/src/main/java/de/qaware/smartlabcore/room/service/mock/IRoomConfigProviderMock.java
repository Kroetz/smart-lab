package de.qaware.smartlabcore.room.service.mock;

import de.qaware.smartlabcore.entity.room.Room;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "room-config-provider", url = "http://localhost:8082")
@ApiIgnore
@RestController
@RequestMapping("/smart-lab/room-config-provider")
@Qualifier("mock")
public interface IRoomConfigProviderMock {

    @PostMapping(value = "/{roomId}/exists")
    boolean exists(@PathVariable("roomId") long roomId);

    @GetMapping
    List<Room> getRooms();

    @GetMapping("/{roomId}")
    Optional<Room> getRoom(@PathVariable("roomId") long roomId);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean createRoom(@RequestBody Room room);

    @DeleteMapping("/{roomId}")
    boolean deleteRoom(@PathVariable("roomId") long roomId);
}
