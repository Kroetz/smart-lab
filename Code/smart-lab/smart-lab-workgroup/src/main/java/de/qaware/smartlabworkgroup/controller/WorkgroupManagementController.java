package de.qaware.smartlabworkgroup.controller;

import de.qaware.smartlabcommons.api.WorkgroupManagementApiConstants;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.generic.controller.AbstractSmartLabController;
import de.qaware.smartlabcore.generic.controller.IEntityManagementController;
import de.qaware.smartlabworkgroup.business.IWorkgroupManagementBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(WorkgroupManagementApiConstants.MAPPING_BASE)
@Slf4j
public class WorkgroupManagementController extends AbstractSmartLabController implements IEntityManagementController<IWorkgroup> {

    private final IWorkgroupManagementBusinessLogic workgroupManagementBusinessLogic;

    public WorkgroupManagementController(IWorkgroupManagementBusinessLogic workgroupManagementBusinessLogic) {
        this.workgroupManagementBusinessLogic = workgroupManagementBusinessLogic;
    }

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_FIND_ALL)
    public Set<IWorkgroup> findAll() {
        return workgroupManagementBusinessLogic.findAll();
    }

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_FIND_ONE)
    public ResponseEntity<IWorkgroup> findOne(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return responseFromOptional(workgroupManagementBusinessLogic.findOne(workgroupId));
    }

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_FIND_MULTIPLE)
    public ResponseEntity<Set<IWorkgroup>> findMultiple(@RequestParam(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_IDS) String[] workgroupIds) {
        return responseFromOptionals(workgroupManagementBusinessLogic.findMultiple(new HashSet<>(Arrays.asList(workgroupIds))));
    }

    @PostMapping(value = WorkgroupManagementApiConstants.MAPPING_CREATE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody IWorkgroup workgroup) {
        return workgroupManagementBusinessLogic.create(workgroup).toResponseEntity();
    }

    @DeleteMapping(WorkgroupManagementApiConstants.MAPPING_DELETE)
    public ResponseEntity<Void> delete(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return workgroupManagementBusinessLogic.delete(workgroupId).toResponseEntity();
    }

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_GET_MEETINGS_OF_WORKGROUP)
    public ResponseEntity<Set<IMeeting>> getMeetingsOfWorkgroup(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return responseFromOptional(workgroupManagementBusinessLogic.getMeetingsOfWorkgroup(workgroupId));
    }

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_GET_CURRENT_MEETING)
    public ResponseEntity<IMeeting> getCurrentMeeting(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return responseFromOptional(workgroupManagementBusinessLogic.getCurrentMeeting(workgroupId));
    }

    @PostMapping(WorkgroupManagementApiConstants.MAPPING_EXTEND_CURRENT_MEETING)
    public ResponseEntity<Void> extendCurrentMeeting(
            @PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(WorkgroupManagementApiConstants.PARAMETER_NAME_EXTENSION_IN_MINUTES) long extensionInMinutes) {
        return workgroupManagementBusinessLogic.extendCurrentMeeting(workgroupId, Duration.ofMinutes(extensionInMinutes)).toResponseEntity();
    }
}
