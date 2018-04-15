package de.qaware.smartlabcore.meeting.controller;

import de.qaware.smartlabcore.entity.meeting.IMeeting;
import de.qaware.smartlabcore.meeting.service.IMeetingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(MeetingController.MAPPING_BASE)
@Slf4j
public class MeetingController {

    public static final String MAPPING_BASE = "/smart-lab/api/meeting";

    public static final String MAPPING_GET_MEETINGS = "";
    public static final String URL_TEMPLATE_GET_MEETINGS = "";

    private final IMeetingService meetingService;

    @Autowired
    public MeetingController(@Qualifier("mock") IMeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping(MAPPING_GET_MEETINGS)
    public List<IMeeting> getMeetings() {
        return meetingService.getMeetings();
    }

    @GetMapping("/{meetingId}")
    public Optional<IMeeting> getMeeting(@PathVariable("meetingId") long meetingId) {
        // TODO: Use response entities instead of return null or an empty optional (see the following line)
        // return ResponseEntity.notFound().build();
        return meetingService.getMeeting(meetingId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean createMeeting(@RequestBody IMeeting meeting) {
        return meetingService.createMeeting(meeting);
    }

    @PutMapping("/{meetingId}/shorten")
    public void shortenMeeting(
            @PathVariable("meetingId") long meetingId,
            @RequestParam(value = "shortening-in-minutes") long shorteningInMinutes) {
        meetingService.shortenMeeting(meetingId, Duration.ofMinutes(shorteningInMinutes));
    }

    @PutMapping("/{meetingId}/extend")
    public boolean extendMeeting(
            @PathVariable("meetingId") long meetingId,
            @RequestParam(value = "extension-in-minutes") long extensionInMinutes) {
        return meetingService.extendMeeting(meetingId, Duration.ofMinutes(extensionInMinutes));
    }

    @PutMapping("/{meetingId}/shift")
    public boolean shiftMeeting(
            @PathVariable("meetingId") long meetingId,
            @RequestParam(value = "shift-in-minutes") long shiftInMinutes) {
        return meetingService.shiftMeeting(meetingId, Duration.ofMinutes(shiftInMinutes));
    }

    @DeleteMapping("/{meetingId}")
    public void deleteMeeting(@PathVariable("meetingId") long meetingId) {
        meetingService.deleteMeeting(meetingId);
    }
}
