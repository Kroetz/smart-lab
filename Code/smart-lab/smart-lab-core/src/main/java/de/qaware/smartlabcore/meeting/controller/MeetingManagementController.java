package de.qaware.smartlabcore.meeting.controller;

import de.qaware.smartlabcore.entity.meeting.IMeeting;
import de.qaware.smartlabcore.meeting.service.IMeetingManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(MeetingManagementController.MAPPING_BASE)
@Slf4j
public class MeetingManagementController {

    public static final String MAPPING_BASE = "/smart-lab/api/meeting";
    public static final String MAPPING_GET_MEETINGS = "";
    public static final String MAPPING_GET_MEETING = "/{meetingId}";
    public static final String MAPPING_CREATE_MEETING = "";
    public static final String MAPPING_SHORTEN_MEETING = "/{meetingId}/shorten";
    public static final String MAPPING_EXTEND_MEETING = "/{meetingId}/extend";
    public static final String MAPPING_SHIFT_MEETING = "/{meetingId}/shift";
    public static final String MAPPING_DELETE_MEETING = "/{meetingId}";

    private final IMeetingManagementService meetingManagementService;

    public MeetingManagementController(@Qualifier("mock") IMeetingManagementService meetingManagementService) {
        this.meetingManagementService = meetingManagementService;
    }

    @GetMapping(MAPPING_GET_MEETINGS)
    public List<IMeeting> getMeetings() {
        return meetingManagementService.getMeetings();
    }

    @GetMapping(MAPPING_GET_MEETING)
    public Optional<IMeeting> getMeeting(@PathVariable("meetingId") long meetingId) {
        // TODO: Use response entities instead of return null or an empty optional (see the following line)
        // return ResponseEntity.notFound().build();
        return meetingManagementService.getMeeting(meetingId);
    }

    @PostMapping(value = MAPPING_CREATE_MEETING, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean createMeeting(@RequestBody IMeeting meeting) {
        return meetingManagementService.createMeeting(meeting);
    }

    @PutMapping(MAPPING_SHORTEN_MEETING)
    public void shortenMeeting(
            @PathVariable("meetingId") long meetingId,
            @RequestParam(value = "shortening-in-minutes") long shorteningInMinutes) {
        meetingManagementService.shortenMeeting(meetingId, Duration.ofMinutes(shorteningInMinutes));
    }

    @PutMapping(MAPPING_EXTEND_MEETING)
    public boolean extendMeeting(
            @PathVariable("meetingId") long meetingId,
            @RequestParam(value = "extension-in-minutes") long extensionInMinutes) {
        return meetingManagementService.extendMeeting(meetingId, Duration.ofMinutes(extensionInMinutes));
    }

    @PutMapping(MAPPING_SHIFT_MEETING)
    public boolean shiftMeeting(
            @PathVariable("meetingId") long meetingId,
            @RequestParam(value = "shift-in-minutes") long shiftInMinutes) {
        return meetingManagementService.shiftMeeting(meetingId, Duration.ofMinutes(shiftInMinutes));
    }

    @DeleteMapping(MAPPING_DELETE_MEETING)
    public void deleteMeeting(@PathVariable("meetingId") long meetingId) {
        meetingManagementService.deleteMeeting(meetingId);
    }
}
