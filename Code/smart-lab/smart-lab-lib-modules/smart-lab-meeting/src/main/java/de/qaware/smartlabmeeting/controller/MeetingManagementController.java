package de.qaware.smartlabmeeting.controller;

import de.qaware.smartlabapi.MeetingManagementApiConstants;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.generic.controller.AbstractSmartLabController;
import de.qaware.smartlabcore.generic.controller.IEntityManagementController;
import de.qaware.smartlabmeeting.business.IMeetingManagementBusinessLogic;
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
public class MeetingManagementController extends AbstractSmartLabController implements IEntityManagementController<IMeeting> {

    private final IMeetingManagementBusinessLogic meetingManagementBusinessLogic;

    public MeetingManagementController(IMeetingManagementBusinessLogic meetingManagementBusinessLogic) {
        this.meetingManagementBusinessLogic = meetingManagementBusinessLogic;
    }

    @Override
    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_ALL)
    public Set<IMeeting> findAll() {
        return meetingManagementBusinessLogic.findAll();
    }

    @Override
    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_ONE)
    public ResponseEntity<IMeeting> findOne(@PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId) {
        return responseFromOptional(meetingManagementBusinessLogic.findOne(meetingId));
    }

    @Override
    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_MULTIPLE)
    public ResponseEntity<Set<IMeeting>> findMultiple(@RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_IDS) String[] meetingIds) {
        return responseFromOptionals(meetingManagementBusinessLogic.findMultiple(new HashSet<>(Arrays.asList(meetingIds))));
    }

    @Override
    @PostMapping(value = MeetingManagementApiConstants.MAPPING_CREATE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody IMeeting meeting) {
        return meetingManagementBusinessLogic.create(meeting).toResponseEntity();
    }

    @Override
    @DeleteMapping(MeetingManagementApiConstants.MAPPING_DELETE)
    public ResponseEntity<Void> delete(@PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId) {
        return meetingManagementBusinessLogic.delete(meetingId).toResponseEntity();
    }

    @PutMapping(MeetingManagementApiConstants.MAPPING_SHORTEN_MEETING)
    public ResponseEntity<Void> shortenMeeting(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId,
            @RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_SHORTENING_IN_MINUTES) long shorteningInMinutes) {
        return meetingManagementBusinessLogic.shortenMeeting(meetingId, Duration.ofMinutes(shorteningInMinutes)).toResponseEntity();
    }

    @PutMapping(MeetingManagementApiConstants.MAPPING_EXTEND_MEETING)
    public ResponseEntity<Void> extendMeeting(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId,
            @RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_EXTENSION_IN_MINUTES) long extensionInMinutes) {
        return meetingManagementBusinessLogic.extendMeeting(meetingId, Duration.ofMinutes(extensionInMinutes)).toResponseEntity();
    }

    @PutMapping(MeetingManagementApiConstants.MAPPING_SHIFT_MEETING)
    public ResponseEntity<Void> shiftMeeting(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId,
            @RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_SHIFT_IN_MINUTES) long shiftInMinutes) {
        return meetingManagementBusinessLogic.shiftMeeting(meetingId, Duration.ofMinutes(shiftInMinutes)).toResponseEntity();
    }
}
