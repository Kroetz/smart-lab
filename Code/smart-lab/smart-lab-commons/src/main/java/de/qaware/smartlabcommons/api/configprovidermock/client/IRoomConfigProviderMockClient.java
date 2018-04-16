package de.qaware.smartlabcommons.api.configprovidermock.client;

import de.qaware.smartlabcommons.api.configprovidermock.RoomConfigProviderMockApiConstants;
import de.qaware.smartlabcommons.data.room.Room;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "room-config-provider", url = "http://localhost:8082")
public interface IRoomConfigProviderMockClient {

    @GetMapping(RoomConfigProviderMockApiConstants.MAPPING_BASE + RoomConfigProviderMockApiConstants.MAPPING_GET_ROOMS)
    List<Room> getRooms();

    @GetMapping(RoomConfigProviderMockApiConstants.MAPPING_BASE + RoomConfigProviderMockApiConstants.MAPPING_GET_ROOM)
    Optional<Room> getRoom(@PathVariable("roomId") long roomId);

    @PostMapping(
            value = RoomConfigProviderMockApiConstants.MAPPING_BASE + RoomConfigProviderMockApiConstants.MAPPING_CREATE_ROOM,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean createRoom(@RequestBody Room room);

    @DeleteMapping(RoomConfigProviderMockApiConstants.MAPPING_BASE + RoomConfigProviderMockApiConstants.MAPPING_DELETE_ROOM)
    boolean deleteRoom(@PathVariable("roomId") long roomId);
}
