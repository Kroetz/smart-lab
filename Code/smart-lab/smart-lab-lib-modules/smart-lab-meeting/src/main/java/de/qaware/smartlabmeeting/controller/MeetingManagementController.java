package de.qaware.smartlabmeeting.controller;

import de.qaware.smartlabapi.MeetingManagementApiConstants;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.generic.controller.AbstractSmartLabController;
import de.qaware.smartlabmeeting.business.IMeetingManagementBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(MeetingManagementApiConstants.MAPPING_BASE)
@Slf4j
public class MeetingManagementController extends AbstractSmartLabController implements IMeetingManagementController {

    private final IMeetingManagementBusinessLogic meetingManagementBusinessLogic;

    public MeetingManagementController(IMeetingManagementBusinessLogic meetingManagementBusinessLogic) {
        this.meetingManagementBusinessLogic = meetingManagementBusinessLogic;
    }

    @Override
    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_ALL)
    public Map<RoomId, Set<IMeeting>> findAll() {
        return this.meetingManagementBusinessLogic.findAll();
    }

    @Override
    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_ALL_BY_ROOM_ID)
    public Set<IMeeting> findAll(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId) {
        return this.meetingManagementBusinessLogic.findAll(RoomId.of(roomId));
    }

    @Override
    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_ONE)
    public ResponseEntity<IMeeting> findOne(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId,
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId) {
        return responseFromOptional(this.meetingManagementBusinessLogic.findOne(MeetingId.of(meetingId), RoomId.of(roomId)));
    }

    @Override
    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_MULTIPLE)
    public ResponseEntity<Set<IMeeting>> findMultiple(
            @RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_IDS) String[] meetingIds,
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId) {
        return responseFromOptionals(this.meetingManagementBusinessLogic.findMultiple(
                Arrays.stream(meetingIds)
                        .map(MeetingId::of)
                        .collect(Collectors.toSet()), RoomId.of(roomId)));
    }

    @Override
    @PostMapping(value = MeetingManagementApiConstants.MAPPING_CREATE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody IMeeting meeting) {
        return this.meetingManagementBusinessLogic.create(meeting).toResponseEntity();
    }

    @Override
    @DeleteMapping(MeetingManagementApiConstants.MAPPING_DELETE)
    public ResponseEntity<Void> delete(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId,
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId) {
        return this.meetingManagementBusinessLogic.delete(MeetingId.of(meetingId), RoomId.of(roomId)).toResponseEntity();
    }

    @Override
    @PutMapping(MeetingManagementApiConstants.MAPPING_SHORTEN_MEETING)
    public ResponseEntity<Void> shortenMeeting(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId,
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId,
            @RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_SHORTENING_IN_MINUTES) long shorteningInMinutes) {
        return this.meetingManagementBusinessLogic.shortenMeeting(
                MeetingId.of(meetingId),
                RoomId.of(roomId),
                Duration.ofMinutes(shorteningInMinutes)).toResponseEntity();
    }

    @Override
    @PutMapping(MeetingManagementApiConstants.MAPPING_EXTEND_MEETING)
    public ResponseEntity<Void> extendMeeting(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId,
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId,
            @RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_EXTENSION_IN_MINUTES) long extensionInMinutes) {
        return this.meetingManagementBusinessLogic.extendMeeting(
                MeetingId.of(meetingId),
                RoomId.of(roomId),
                Duration.ofMinutes(extensionInMinutes)).toResponseEntity();
    }

    @Override
    @PutMapping(MeetingManagementApiConstants.MAPPING_SHIFT_MEETING)
    public ResponseEntity<Void> shiftMeeting(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId,
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId,
            @RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_SHIFT_IN_MINUTES) long shiftInMinutes) {
        return this.meetingManagementBusinessLogic.shiftMeeting(
                MeetingId.of(meetingId),
                RoomId.of(roomId),
                Duration.ofMinutes(shiftInMinutes)).toResponseEntity();
    }
}
