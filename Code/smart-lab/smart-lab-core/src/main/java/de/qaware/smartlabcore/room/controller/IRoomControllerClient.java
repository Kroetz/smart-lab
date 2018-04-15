package de.qaware.smartlabcore.room.controller;

import de.qaware.smartlabcore.entity.meeting.IMeeting;
import de.qaware.smartlabcore.entity.room.Room;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "room-controller", url = "http://localhost:8080")
public interface IRoomControllerClient {

    @GetMapping("/smart-lab/api/room")
    @ResponseBody
    List<Room> getRooms();

    @GetMapping("/smart-lab/api/room/{roomId}")
    @ResponseBody
    Optional<Room> getRoom(@PathVariable("roomId") long roomId);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    boolean createRoom(@RequestBody Room room);

    @DeleteMapping("/smart-lab/api/room/{roomId}")
    @ResponseBody
    void deleteRoom(@PathVariable("roomId") long roomId);

    @GetMapping("/smart-lab/api/room/{roomId}/current-meeting")
    @ResponseBody
    Optional<IMeeting> getCurrentMeeting(@PathVariable("roomId") long roomId);

    @PostMapping("/smart-lab/api/room/{roomId}/extend-current-meeting")
    @ResponseBody
    void extendCurrentMeeting(
            @PathVariable("roomId") long roomId,
            @RequestParam(value = "extension-in-minutes", defaultValue = "10") long extensionInMinutes);

    @GetMapping(value = "/smart-lab/api/room/{roomId}/current-meeting-status-page")
    String getCurrentMeetingStatusPage(@PathVariable("roomId") long roomId, Model model);
}