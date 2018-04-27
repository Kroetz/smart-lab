package de.qaware.smartlabcore.workgroup.controller;

import de.qaware.smartlabcommons.api.WorkgroupManagementApiConstants;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.generic.controller.AbstractSmartLabController;
import de.qaware.smartlabcore.workgroup.service.IWorkgroupManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Set;

@RestController
@RequestMapping(WorkgroupManagementApiConstants.MAPPING_BASE)
@Slf4j
public class WorkgroupManagementController extends AbstractSmartLabController {

    private final IWorkgroupManagementService workgroupManagementService;

    public WorkgroupManagementController(IWorkgroupManagementService workgroupManagementService) {
        this.workgroupManagementService = workgroupManagementService;
    }

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_FIND_ALL)
    public Set<IWorkgroup> findAll() {
        return workgroupManagementService.findAll();
    }

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_FIND_ONE)
    public ResponseEntity<IWorkgroup> findOne(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return responseFromOptional(workgroupManagementService.findOne(workgroupId));
    }

    @PostMapping(value = WorkgroupManagementApiConstants.MAPPING_CREATE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody IWorkgroup workgroup) {
        return workgroupManagementService.create(workgroup).toResponseEntity();
    }

    @DeleteMapping(WorkgroupManagementApiConstants.MAPPING_DELETE)
    public ResponseEntity<Void> delete(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return workgroupManagementService.delete(workgroupId).toResponseEntity();
    }

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_GET_MEETINGS_OF_WORKGROUP)
    public ResponseEntity<Set<IMeeting>> getMeetingsOfWorkgroup(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return responseFromOptional(workgroupManagementService.getMeetingsOfWorkgroup(workgroupId));
    }

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_GET_CURRENT_MEETING)
    public ResponseEntity<IMeeting> getCurrentMeeting(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return responseFromOptional(workgroupManagementService.getCurrentMeeting(workgroupId));
    }

    @PostMapping(WorkgroupManagementApiConstants.MAPPING_EXTEND_CURRENT_MEETING)
    public ResponseEntity<Void> extendCurrentMeeting(
            @PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(WorkgroupManagementApiConstants.PARAMETER_NAME_EXTENSION_IN_MINUTES) long extensionInMinutes) {
        return workgroupManagementService.extendCurrentMeeting(workgroupId, Duration.ofMinutes(extensionInMinutes)).toResponseEntity();
    }
}
