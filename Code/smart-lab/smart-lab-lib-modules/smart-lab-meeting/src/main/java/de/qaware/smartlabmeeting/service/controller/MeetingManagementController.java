package de.qaware.smartlabmeeting.service.controller;

import de.qaware.smartlabapi.service.constant.meeting.MeetingManagementApiConstants;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.service.controller.AbstractSmartLabController;
import de.qaware.smartlabcore.service.controller.IBasicEntityManagementController;
import de.qaware.smartlabcore.service.controller.url.AbstractBaseUrlController;
import de.qaware.smartlabcore.url.IBaseUrlDetector;
import de.qaware.smartlabmeeting.service.business.IMeetingManagementBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(MeetingManagementApiConstants.MAPPING_BASE)
@Slf4j
public class MeetingManagementController extends AbstractSmartLabController implements IBasicEntityManagementController<IMeeting> {

    private final IMeetingManagementBusinessLogic meetingManagementBusinessLogic;

    public MeetingManagementController(IMeetingManagementBusinessLogic meetingManagementBusinessLogic) {
        this.meetingManagementBusinessLogic = meetingManagementBusinessLogic;
    }

    @Override
    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_ALL)
    @ResponseBody
    public Set<IMeeting> findAll() {
        return this.meetingManagementBusinessLogic.findAll();
    }

    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_ALL_BY_ROOM_ID)
    @ResponseBody
    public Set<IMeeting> findAll(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_ROOM_ID) String roomId) {
        return this.meetingManagementBusinessLogic.findAll(RoomId.of(roomId));
    }

    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_ALL_CURRENT)
    @ResponseBody
    public Set<IMeeting> findAllCurrent() {
        return this.meetingManagementBusinessLogic.findAllCurrent();
    }

    @Override
    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_ONE)
    @ResponseBody
    public ResponseEntity<IMeeting> findOne(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId) {
        return responseFromOptional(this.meetingManagementBusinessLogic.findOne(MeetingId.of(meetingId)));
    }

    @Override
    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_MULTIPLE)
    @ResponseBody
    public ResponseEntity<Set<IMeeting>> findMultiple(
            @RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_IDS) String[] meetingIds) {
        return responseFromOptionals(this.meetingManagementBusinessLogic.findMultiple(
                Arrays.stream(meetingIds)
                        .map(MeetingId::of)
                        .collect(Collectors.toSet())));
    }

    @Override
    @PostMapping(value = MeetingManagementApiConstants.MAPPING_CREATE_SINGLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<IMeeting> create(@RequestBody IMeeting meeting) {
        return ResponseEntity.ok(this.meetingManagementBusinessLogic.create(meeting));
    }

    @Override
    @PostMapping(value = MeetingManagementApiConstants.MAPPING_CREATE_MULTIPLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Set<IMeeting>> create(Set<IMeeting> meetings) {
        return ResponseEntity.ok(this.meetingManagementBusinessLogic.create(meetings));
    }

    @Override
    @DeleteMapping(MeetingManagementApiConstants.MAPPING_DELETE)
    @ResponseBody
    public ResponseEntity<Void> delete(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId) {
        return this.meetingManagementBusinessLogic.delete(MeetingId.of(meetingId)).toResponseEntity();
    }

    @PutMapping(MeetingManagementApiConstants.MAPPING_SHORTEN_MEETING)
    @ResponseBody
    public ResponseEntity<Void> shortenMeeting(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId,
            @RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_SHORTENING_IN_MINUTES) long shorteningInMinutes) {
        return this.meetingManagementBusinessLogic.shortenMeeting(
                MeetingId.of(meetingId),
                Duration.ofMinutes(shorteningInMinutes)).toResponseEntity();
    }

    @PutMapping(MeetingManagementApiConstants.MAPPING_EXTEND_MEETING)
    @ResponseBody
    public ResponseEntity<Void> extendMeeting(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId,
            @RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_EXTENSION_IN_MINUTES) long extensionInMinutes) {
        return this.meetingManagementBusinessLogic.extendMeeting(
                MeetingId.of(meetingId),
                Duration.ofMinutes(extensionInMinutes)).toResponseEntity();
    }

    @PutMapping(MeetingManagementApiConstants.MAPPING_SHIFT_MEETING)
    @ResponseBody
    public ResponseEntity<Void> shiftMeeting(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId,
            @RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_SHIFT_IN_MINUTES) long shiftInMinutes) {
        return this.meetingManagementBusinessLogic.shiftMeeting(
                MeetingId.of(meetingId),
                Duration.ofMinutes(shiftInMinutes)).toResponseEntity();
    }

    @RestController
    @RequestMapping(MeetingManagementApiConstants.MAPPING_BASE)
    @Slf4j
    public static class BaseUrlController extends AbstractBaseUrlController {

        public BaseUrlController(IBaseUrlDetector baseUrlDetector) {
            super(baseUrlDetector);
        }

        @Override
        @GetMapping(MeetingManagementApiConstants.MAPPING_GET_BASE_URL)
        public ResponseEntity<URL> getBaseUrl() {
            return super.getBaseUrl();
        }
    }
}
