package de.qaware.smartlabworkgroup.service.controller;

import de.qaware.smartlabapi.service.constant.workgroup.WorkgroupManagementApiConstants;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
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
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

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
    @ResponseBody
    public Set<IWorkgroup> findAll() {
        return this.workgroupManagementBusinessLogic.findAll();
    }

    @Override
    @GetMapping(WorkgroupManagementApiConstants.MAPPING_FIND_ONE)
    @ResponseBody
    public ResponseEntity<IWorkgroup> findOne(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return responseFromOptional(this.workgroupManagementBusinessLogic.findOne(WorkgroupId.of(workgroupId)));
    }

    @Override
    @GetMapping(WorkgroupManagementApiConstants.MAPPING_FIND_MULTIPLE)
    @ResponseBody
    public ResponseEntity<Set<IWorkgroup>> findMultiple(@RequestParam(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_IDS) String[] workgroupIds) {
        return responseFromOptionals(
                this.workgroupManagementBusinessLogic.findMultiple(stream(workgroupIds)
                        .map(WorkgroupId::of)
                        .collect(toSet())));
    }

    @Override
    @PostMapping(value = WorkgroupManagementApiConstants.MAPPING_CREATE_SINGLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<IWorkgroup> create(@RequestBody IWorkgroup workgroup) {
        return ResponseEntity.ok(this.workgroupManagementBusinessLogic.create(workgroup));
    }

    @Override
    @PostMapping(value = WorkgroupManagementApiConstants.MAPPING_CREATE_MULTIPLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Set<IWorkgroup>> create(@RequestBody Set<IWorkgroup> workgroups) {
        return ResponseEntity.ok(this.workgroupManagementBusinessLogic.create(workgroups));
    }

    @Override
    @DeleteMapping(WorkgroupManagementApiConstants.MAPPING_DELETE)
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return this.workgroupManagementBusinessLogic.delete(WorkgroupId.of(workgroupId)).toResponseEntity();
    }

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_GET_MEETINGS_OF_WORKGROUP)
    @ResponseBody
    public ResponseEntity<Set<IMeeting>> getMeetingsOfWorkgroup(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return responseFromOptional(this.workgroupManagementBusinessLogic.getMeetingsOfWorkgroup(WorkgroupId.of(workgroupId)));
    }

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_GET_CURRENT_MEETING)
    @ResponseBody
    public ResponseEntity<IMeeting> getCurrentMeeting(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId) {
        return responseFromOptional(this.workgroupManagementBusinessLogic.getCurrentMeeting(WorkgroupId.of(workgroupId)));
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
