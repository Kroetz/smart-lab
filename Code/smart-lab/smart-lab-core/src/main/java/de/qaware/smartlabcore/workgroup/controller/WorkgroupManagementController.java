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
import java.util.List;

@RestController
@RequestMapping(WorkgroupManagementApiConstants.MAPPING_BASE)
@Slf4j
public class WorkgroupManagementController extends AbstractSmartLabController {

    private final IWorkgroupManagementService workgroupManagementService;

    public WorkgroupManagementController(IWorkgroupManagementService workgroupManagementService) {
        this.workgroupManagementService = workgroupManagementService;
    }

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_GET_WORKGROUPS)
    public List<IWorkgroup> getWorkgroups() {
        return workgroupManagementService.getWorkgroups();
    }

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_GET_WORKGROUP)
    public ResponseEntity<IWorkgroup> getWorkgroup(@PathVariable("workgroupId") String workgroupId) {
        return responseFromOptional(workgroupManagementService.getWorkgroup(workgroupId));
    }

    @PostMapping(value = WorkgroupManagementApiConstants.MAPPING_CREATE_WORKGROUP, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean createWorkgroup(@RequestBody IWorkgroup workgroup) {
        return workgroupManagementService.createWorkgroup(workgroup);
    }

    @DeleteMapping(WorkgroupManagementApiConstants.MAPPING_DELETE_WORKGROUP)
    public boolean deleteWorkgroup(@PathVariable("workgroupId") String workgroupId) {
        return workgroupManagementService.deleteWorkgroup(workgroupId);
    }

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_GET_MEETINGS_OF_WORKGROUP)
    public List<IMeeting> getMeetingsOfWorkgroup(String workgroupId) {
        return workgroupManagementService.getMeetingsOfWorkgroup(workgroupId);
    }

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_GET_CURRENT_MEETING)
    public ResponseEntity<IMeeting> getCurrentMeeting(@PathVariable("workgroupId") String workgroupId) {
        return responseFromOptional(workgroupManagementService.getCurrentMeeting(workgroupId));
    }

    @PostMapping(WorkgroupManagementApiConstants.MAPPING_EXTEND_CURRENT_MEETING)
    public boolean extendCurrentMeeting(
            @PathVariable("workgroupId") String workgroupId,
            @RequestParam(value = "extension-in-minutes", defaultValue = "10") long extensionInMinutes) {
        return workgroupManagementService.extendCurrentMeeting(workgroupId, Duration.ofMinutes(extensionInMinutes));
    }
}
