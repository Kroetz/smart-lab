package de.qaware.smartlabeventmanagement.service.controller;

import de.qaware.smartlabapi.service.constant.meeting.MeetingManagementApiConstants;
import de.qaware.smartlabcore.data.generic.IDtoConverter;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.meeting.MeetingDto;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.service.controller.AbstractSmartLabController;
import de.qaware.smartlabcore.service.controller.IBasicEntityManagementController;
import de.qaware.smartlabcore.service.controller.url.AbstractBaseUrlController;
import de.qaware.smartlabcore.service.url.IBaseUrlDetector;
import de.qaware.smartlabeventmanagement.service.business.IMeetingManagementBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.time.Duration;
import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping(MeetingManagementApiConstants.MAPPING_BASE)
@Slf4j
public class MeetingManagementController extends AbstractSmartLabController implements IBasicEntityManagementController<IMeeting, MeetingDto> {

    private final IMeetingManagementBusinessLogic meetingManagementBusinessLogic;
    private final IDtoConverter<IMeeting, MeetingDto> meetingConverter;

    public MeetingManagementController(
            IMeetingManagementBusinessLogic meetingManagementBusinessLogic,
            IDtoConverter<IMeeting, MeetingDto> meetingConverter) {
        this.meetingManagementBusinessLogic = meetingManagementBusinessLogic;
        this.meetingConverter = meetingConverter;
    }

    @Override
    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_ALL)
    @ResponseBody
    public Set<MeetingDto> findAll() {
        return this.meetingManagementBusinessLogic.findAll().stream()
                .map(this.meetingConverter::toDto)
                .collect(toSet());
    }

    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_ALL_BY_LOCATION_ID)
    @ResponseBody
    public Set<MeetingDto> findAllByLocationId(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId) {
        return this.meetingManagementBusinessLogic.findAll(LocationId.of(locationId)).stream()
                .map(this.meetingConverter::toDto)
                .collect(toSet());
    }

    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_ALL_BY_WORKGROUP_ID)
    @ResponseBody
    public Set<MeetingDto> findAllByWorkgroupId(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return this.meetingManagementBusinessLogic.findAll(WorkgroupId.of(workgroupId)).stream()
                .map(this.meetingConverter::toDto)
                .collect(toSet());
    }

    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_ALL_CURRENT)
    @ResponseBody
    public Set<MeetingDto> findAllCurrent() {
        return this.meetingManagementBusinessLogic.findAllCurrent().stream()
                .map(this.meetingConverter::toDto)
                .collect(toSet());
    }

    @Override
    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_ONE)
    @ResponseBody
    public ResponseEntity<MeetingDto> findOne(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_ID) String meetingId) {
        return responseFromEntityOptional(
                this.meetingManagementBusinessLogic.findOne(MeetingId.of(meetingId)),
                this.meetingConverter);
    }

    @Override
    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_MULTIPLE)
    @ResponseBody
    public ResponseEntity<Set<MeetingDto>> findMultiple(
            @RequestParam(MeetingManagementApiConstants.PARAMETER_NAME_MEETING_IDS) String[] meetingIds) {
        return responseFromEntityOptionals(
                this.meetingManagementBusinessLogic.findMultiple(stream(meetingIds)
                        .map(MeetingId::of)
                        .collect(toSet())),
                this.meetingConverter);
    }

    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_CURRENT_BY_LOCATION_ID)
    @ResponseBody
    public ResponseEntity<MeetingDto> findCurrentByLocationId(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId) {
        return responseFromEntityOptional(
                this.meetingManagementBusinessLogic.findCurrent(LocationId.of(locationId)),
                this.meetingConverter);
    }

    @GetMapping(MeetingManagementApiConstants.MAPPING_FIND_CURRENT_BY_WORKGROUP_ID)
    @ResponseBody
    public ResponseEntity<MeetingDto> findCurrentByWorkgroupId(
            @PathVariable(MeetingManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return responseFromEntityOptional(
                this.meetingManagementBusinessLogic.findCurrent(WorkgroupId.of(workgroupId)),
                this.meetingConverter);
    }

    @Override
    @PostMapping(value = MeetingManagementApiConstants.MAPPING_CREATE_SINGLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<MeetingDto> create(@RequestBody MeetingDto meeting) {
        IMeeting meetingToCreate = this.meetingConverter.toEntity(meeting);
        MeetingDto createdMeeting = this.meetingConverter.toDto(this.meetingManagementBusinessLogic.create(meetingToCreate));
        return ResponseEntity.ok(createdMeeting);
    }

    @Override
    @PostMapping(value = MeetingManagementApiConstants.MAPPING_CREATE_MULTIPLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Set<MeetingDto>> create(Set<MeetingDto> meetings) {
        return ResponseEntity.ok(this.meetingManagementBusinessLogic.create(meetings
                .stream()
                .map(this.meetingConverter::toEntity)
                .collect(toSet()))
                .stream()
                .map(this.meetingConverter::toDto)
                .collect(toSet()));
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
