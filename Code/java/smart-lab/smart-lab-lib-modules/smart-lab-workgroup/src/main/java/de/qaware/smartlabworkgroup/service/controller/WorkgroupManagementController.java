package de.qaware.smartlabworkgroup.service.controller;

import de.qaware.smartlabcore.service.constant.workgroup.WorkgroupManagementApiConstants;
import de.qaware.smartlabcore.data.generic.IDtoConverter;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingDto;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.data.workgroup.WorkgroupDto;
import de.qaware.smartlabcore.service.controller.AbstractSmartLabController;
import de.qaware.smartlabcore.service.controller.IBasicEntityManagementController;
import de.qaware.smartlabcore.service.controller.url.AbstractBaseUrlController;
import de.qaware.smartlabcore.service.url.IBaseUrlDetector;
import de.qaware.smartlabworkgroup.service.business.IWorkgroupManagementBusinessLogic;
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
@RequestMapping(WorkgroupManagementApiConstants.MAPPING_BASE)
@Slf4j
public class WorkgroupManagementController extends AbstractSmartLabController implements IBasicEntityManagementController<IWorkgroup, WorkgroupDto> {

    private final IWorkgroupManagementBusinessLogic workgroupManagementBusinessLogic;
    private final IDtoConverter<IWorkgroup, WorkgroupDto> workgroupConverter;
    private final IDtoConverter<IMeeting, MeetingDto> meetingConverter;

    public WorkgroupManagementController(
            IWorkgroupManagementBusinessLogic workgroupManagementBusinessLogic,
            IDtoConverter<IWorkgroup, WorkgroupDto> workgroupConverter,
            IDtoConverter<IMeeting, MeetingDto> meetingConverter) {
        this.workgroupManagementBusinessLogic = workgroupManagementBusinessLogic;
        this.workgroupConverter = workgroupConverter;
        this.meetingConverter = meetingConverter;
    }

    @Override
    @GetMapping(WorkgroupManagementApiConstants.MAPPING_FIND_ALL)
    @ResponseBody
    public Set<WorkgroupDto> findAll() {
        return this.workgroupManagementBusinessLogic.findAll().stream()
                .map(this.workgroupConverter::toDto)
                .collect(toSet());
    }

    @Override
    @GetMapping(WorkgroupManagementApiConstants.MAPPING_FIND_ONE)
    @ResponseBody
    public ResponseEntity<WorkgroupDto> findOne(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return responseFromEntityOptional(
                this.workgroupManagementBusinessLogic.findOne(WorkgroupId.of(workgroupId)),
                this.workgroupConverter);
    }

    @Override
    @GetMapping(WorkgroupManagementApiConstants.MAPPING_FIND_MULTIPLE)
    @ResponseBody
    public ResponseEntity<Set<WorkgroupDto>> findMultiple(@RequestParam(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_IDS) String[] workgroupIds) {
        return responseFromEntityOptionals(
                this.workgroupManagementBusinessLogic.findMultiple(stream(workgroupIds)
                        .map(WorkgroupId::of)
                        .collect(toSet())),
                this.workgroupConverter);
    }

    @Override
    @PostMapping(value = WorkgroupManagementApiConstants.MAPPING_CREATE_SINGLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<WorkgroupDto> create(@RequestBody WorkgroupDto workgroup) {
        IWorkgroup workgroupToCreate = this.workgroupConverter.toEntity(workgroup);
        WorkgroupDto createdWorkgroup = this.workgroupConverter.toDto(
                this.workgroupManagementBusinessLogic.create(workgroupToCreate));
        return ResponseEntity.ok(createdWorkgroup);
    }

    @Override
    @PostMapping(value = WorkgroupManagementApiConstants.MAPPING_CREATE_MULTIPLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Set<WorkgroupDto>> create(@RequestBody Set<WorkgroupDto> workgroups) {
        return ResponseEntity.ok(this.workgroupManagementBusinessLogic.create(workgroups
                .stream()
                .map(this.workgroupConverter::toEntity)
                .collect(toSet()))
                .stream()
                .map(this.workgroupConverter::toDto)
                .collect(toSet()));
    }

    @Override
    @DeleteMapping(WorkgroupManagementApiConstants.MAPPING_DELETE)
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return this.workgroupManagementBusinessLogic.delete(WorkgroupId.of(workgroupId)).toResponseEntity();
    }

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_GET_MEETINGS_OF_WORKGROUP)
    @ResponseBody
    public ResponseEntity<Set<MeetingDto>> getMeetingsOfWorkgroup(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return responseFromEntityOptionals(
                this.workgroupManagementBusinessLogic.getMeetingsOfWorkgroup(WorkgroupId.of(workgroupId)),
                this.meetingConverter);
    }

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_GET_CURRENT_MEETING)
    @ResponseBody
    public ResponseEntity<MeetingDto> getCurrentMeeting(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return responseFromEntityOptional(
                this.workgroupManagementBusinessLogic.getCurrentMeeting(WorkgroupId.of(workgroupId)),
                this.meetingConverter);
    }

    @PostMapping(WorkgroupManagementApiConstants.MAPPING_EXTEND_CURRENT_MEETING)
    @ResponseBody
    public ResponseEntity<Void> extendCurrentMeeting(
            @PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(WorkgroupManagementApiConstants.PARAMETER_NAME_EXTENSION_IN_MINUTES) long extensionInMinutes) {
        return this.workgroupManagementBusinessLogic.extendCurrentMeeting(WorkgroupId.of(workgroupId), Duration.ofMinutes(extensionInMinutes)).toResponseEntity();
    }

    @RestController
    @RequestMapping(WorkgroupManagementApiConstants.MAPPING_BASE)
    @Slf4j
    public static class BaseUrlController extends AbstractBaseUrlController {

        public BaseUrlController(IBaseUrlDetector baseUrlDetector) {
            super(baseUrlDetector);
        }

        @Override
        @GetMapping(WorkgroupManagementApiConstants.MAPPING_GET_BASE_URL)
        public ResponseEntity<URL> getBaseUrl() {
            return super.getBaseUrl();
        }
    }
}
