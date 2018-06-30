package de.qaware.smartlabworkgroup.controller;

import de.qaware.smartlabapi.WorkgroupManagementApiConstants;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.generic.controller.AbstractSmartLabController;
import de.qaware.smartlabcore.generic.controller.IBasicEntityManagementController;
import de.qaware.smartlabworkgroup.business.IWorkgroupManagementBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(WorkgroupManagementApiConstants.MAPPING_BASE)
@Slf4j
public class WorkgroupManagementController extends AbstractSmartLabController implements IBasicEntityManagementController<IWorkgroup> {

    private final IWorkgroupManagementBusinessLogic workgroupManagementBusinessLogic;

    public WorkgroupManagementController(IWorkgroupManagementBusinessLogic workgroupManagementBusinessLogic) {
        this.workgroupManagementBusinessLogic = workgroupManagementBusinessLogic;
    }

    @Override
    @GetMapping(WorkgroupManagementApiConstants.MAPPING_FIND_ALL)
    public Set<IWorkgroup> findAll() {
        return this.workgroupManagementBusinessLogic.findAll();
    }

    @Override
    @GetMapping(WorkgroupManagementApiConstants.MAPPING_FIND_ONE)
    public ResponseEntity<IWorkgroup> findOne(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return responseFromOptional(this.workgroupManagementBusinessLogic.findOne(WorkgroupId.of(workgroupId)));
    }

    @Override
    @GetMapping(WorkgroupManagementApiConstants.MAPPING_FIND_MULTIPLE)
    public ResponseEntity<Set<IWorkgroup>> findMultiple(@RequestParam(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_IDS) String[] workgroupIds) {
        return responseFromOptionals(
                this.workgroupManagementBusinessLogic.findMultiple(Arrays.stream(workgroupIds)
                        .map(WorkgroupId::of)
                        .collect(Collectors.toSet())));
    }

    @Override
    @PostMapping(value = WorkgroupManagementApiConstants.MAPPING_CREATE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IWorkgroup> create(@RequestBody IWorkgroup workgroup) {
        return ResponseEntity.ok(this.workgroupManagementBusinessLogic.create(workgroup));
    }

    @Override
    @DeleteMapping(WorkgroupManagementApiConstants.MAPPING_DELETE)
    public ResponseEntity<Void> delete(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return this.workgroupManagementBusinessLogic.delete(WorkgroupId.of(workgroupId)).toResponseEntity();
    }

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_GET_MEETINGS_OF_WORKGROUP)
    public ResponseEntity<Set<IMeeting>> getMeetingsOfWorkgroup(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return responseFromOptional(this.workgroupManagementBusinessLogic.getMeetingsOfWorkgroup(WorkgroupId.of(workgroupId)));
    }

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_GET_CURRENT_MEETING)
    public ResponseEntity<IMeeting> getCurrentMeeting(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return responseFromOptional(this.workgroupManagementBusinessLogic.getCurrentMeeting(WorkgroupId.of(workgroupId)));
    }

    @PostMapping(WorkgroupManagementApiConstants.MAPPING_EXTEND_CURRENT_MEETING)
    public ResponseEntity<Void> extendCurrentMeeting(
            @PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(WorkgroupManagementApiConstants.PARAMETER_NAME_EXTENSION_IN_MINUTES) long extensionInMinutes) {
        return this.workgroupManagementBusinessLogic.extendCurrentMeeting(WorkgroupId.of(workgroupId), Duration.ofMinutes(extensionInMinutes)).toResponseEntity();
    }
}
