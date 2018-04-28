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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(MeetingManagementApiConstants.MAPPING_BASE)
@Slf4j
public class MeetingManagementController extends AbstractSmartLabController {

    private final IMeetingManagementService meetingManagementService;

    public MeetingManagementController(IMeetingManagementService meetingManagementService) {
        this.meetingManagementService = meetingManagementService;
    }

    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_ALL)
    public Set<IMeeting> findAll() {
        return meetingManagementService.findAll();
    }

    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_ONE)
    public ResponseEntity<IMeeting> findOne(@PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId) {
        return responseFromOptional(meetingManagementService.findOne(meetingId));
    }

    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_MULTIPLE)
    ResponseEntity<Set<IMeeting>> findMultiple(@RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_IDS) String[] meetingIds) {
        return responseFromOptionals(meetingManagementService.findMultiple(new HashSet<>(Arrays.asList(meetingIds))));
    }

    @PostMapping(value = MeetingManagementApiConstants.MAPPING_CREATE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody IMeeting meeting) {
        return meetingManagementService.create(meeting).toResponseEntity();
    }

    @DeleteMapping(MeetingManagementApiConstants.MAPPING_DELETE)
    public ResponseEntity<Void> delete(@PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId) {
        return meetingManagementService.delete(meetingId).toResponseEntity();
    }

    @PutMapping(MeetingManagementApiConstants.MAPPING_SHORTEN_MEETING)
    public ResponseEntity<Void> shortenMeeting(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId,
            @RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_SHORTENING_IN_MINUTES) long shorteningInMinutes) {
        return meetingManagementService.shortenMeeting(meetingId, Duration.ofMinutes(shorteningInMinutes)).toResponseEntity();
    }

    @PutMapping(MeetingManagementApiConstants.MAPPING_EXTEND_MEETING)
    public ResponseEntity<Void> extendMeeting(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId,
            @RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_EXTENSION_IN_MINUTES) long extensionInMinutes) {
        return meetingManagementService.extendMeeting(meetingId, Duration.ofMinutes(extensionInMinutes)).toResponseEntity();
    }

    @PutMapping(MeetingManagementApiConstants.MAPPING_SHIFT_MEETING)
    public ResponseEntity<Void> shiftMeeting(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId,
            @RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_SHIFT_IN_MINUTES) long shiftInMinutes) {
        return meetingManagementService.shiftMeeting(meetingId, Duration.ofMinutes(shiftInMinutes)).toResponseEntity();
    }
}
