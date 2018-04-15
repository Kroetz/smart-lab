package de.qaware.smartlabcore.room.controller;

import de.qaware.smartlabcore.entity.meeting.IMeeting;
import de.qaware.smartlabcore.entity.room.Room;
import de.qaware.smartlabcore.room.service.IRoomService;
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
@RequestMapping("/smart-lab/api/room")
@Slf4j
public class RoomController {

    private final IRoomService roomService;

    @Autowired
    public RoomController(@Qualifier("mock") IRoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    @ResponseBody
    public List<Room> getRooms() {
        return roomService.getRooms();
    }

    @GetMapping("/{roomId}")
    @ResponseBody
    public Optional<Room> getRoom(@PathVariable("roomId") long roomId) {
        return roomService.getRoom(roomId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public boolean createRoom(@RequestBody Room room) {
        return roomService.createRoom(room);
    }

    @DeleteMapping("/{roomId}")
    @ResponseBody
    public void deleteRoom(@PathVariable("roomId") long roomId) {
        roomService.deleteRoom(roomId);
    }

    @GetMapping("/{roomId}/current-meeting")
    @ResponseBody
    public Optional<IMeeting> getCurrentMeeting(@PathVariable("roomId") long roomId) {
        return roomService.getCurrentMeeting(roomId);
    }

    @PostMapping("/{roomId}/extend-current-meeting")
    @ResponseBody
    public void extendCurrentMeeting(
            @PathVariable("roomId") long roomId,
            @RequestParam(value = "extension-in-minutes", defaultValue = "10") long extensionInMinutes) {
        roomService.extendCurrentMeeting(roomId, extensionInMinutes);
    }

    @GetMapping(value = "/{roomId}/current-meeting-status-page")
    public String getCurrentMeetingStatusPage(@PathVariable("roomId") long roomId, Model model) {
        return roomService.getCurrentMeetingStatusPage(roomId, model);
    }
}
