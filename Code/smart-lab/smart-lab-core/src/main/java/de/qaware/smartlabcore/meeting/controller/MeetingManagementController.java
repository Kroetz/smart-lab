package de.qaware.smartlabcore.meeting.controller;

import de.qaware.smartlabcommons.api.MeetingManagementApiConstants;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcore.generic.controller.AbstractSmartLabController;
import de.qaware.smartlabcore.meeting.service.IMeetingManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping(MeetingManagementApiConstants.MAPPING_BASE)
@Slf4j
public class MeetingManagementController extends AbstractSmartLabController {

    private final IMeetingManagementService meetingManagementService;

    public MeetingManagementController(IMeetingManagementService meetingManagementService) {
        this.meetingManagementService = meetingManagementService;
    }

    @GetMapping(MeetingManagementApiConstants.MAPPING_GET_MEETINGS)
    public List<IMeeting> getMeetings() {
        return meetingManagementService.getMeetings();
    }

    @GetMapping(MeetingManagementApiConstants.MAPPING_GET_MEETING)
    public ResponseEntity<IMeeting> getMeeting(@PathVariable("meetingId") String meetingId) {
        return responseFromOptional(meetingManagementService.getMeeting(meetingId));
    }

    @PostMapping(value = MeetingManagementApiConstants.MAPPING_CREATE_MEETING, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean createMeeting(@RequestBody IMeeting meeting) {
        return meetingManagementService.createMeeting(meeting);
    }

    @DeleteMapping(MeetingManagementApiConstants.MAPPING_DELETE_MEETING)
    public boolean deleteMeeting(@PathVariable("meetingId") String meetingId) {
        return meetingManagementService.deleteMeeting(meetingId);
    }

    @PutMapping(MeetingManagementApiConstants.MAPPING_SHORTEN_MEETING)
    public void shortenMeeting(
            @PathVariable("meetingId") String meetingId,
            @RequestParam(value = "shortening-in-minutes") long shorteningInMinutes) {
        meetingManagementService.shortenMeeting(meetingId, Duration.ofMinutes(shorteningInMinutes));
    }

    @PutMapping(MeetingManagementApiConstants.MAPPING_EXTEND_MEETING)
    public boolean extendMeeting(
            @PathVariable("meetingId") String meetingId,
            @RequestParam(value = "extension-in-minutes") long extensionInMinutes) {
        return meetingManagementService.extendMeeting(meetingId, Duration.ofMinutes(extensionInMinutes));
    }

    @PutMapping(MeetingManagementApiConstants.MAPPING_SHIFT_MEETING)
    public boolean shiftMeeting(
            @PathVariable("meetingId") String meetingId,
            @RequestParam(value = "shift-in-minutes") long shiftInMinutes) {
        return meetingManagementService.shiftMeeting(meetingId, Duration.ofMinutes(shiftInMinutes));
    }
}