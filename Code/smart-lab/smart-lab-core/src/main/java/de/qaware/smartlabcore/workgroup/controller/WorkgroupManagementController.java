package de.qaware.smartlabcore.workgroup.controller;

import de.qaware.smartlabcommons.api.WorkgroupManagementApiConstants;
import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcore.workgroup.service.IWorkgroupManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(WorkgroupManagementApiConstants.MAPPING_BASE)
@Slf4j
public class WorkgroupManagementController {

    private final IWorkgroupManagementService workgroupManagementService;

    public WorkgroupManagementController(@Qualifier("mock") IWorkgroupManagementService workgroupManagementService) {
        this.workgroupManagementService = workgroupManagementService;
    }

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_GET_WORKGROUPS)
    public List<Workgroup> getWorkgroups() {
        return workgroupManagementService.getWorkgroups();
    }

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_GET_WORKGROUP)
    public Optional<Workgroup> getWorkgroup(@PathVariable("workgroupId") long workgroupId) {
        return workgroupManagementService.getWorkgroup(workgroupId);
    }

    @PostMapping(value = WorkgroupManagementApiConstants.MAPPING_CREATE_WORKGROUP, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean createWorkgroup(@RequestBody Workgroup workgroup) {
        return workgroupManagementService.createWorkgroup(workgroup);
    }

    @DeleteMapping(WorkgroupManagementApiConstants.MAPPING_DELETE_WORKGROUP)
    public void deleteWorkgroup(@PathVariable("workgroupId") long workgroupId) {
        workgroupManagementService.deleteWorkgroup(workgroupId);
    }

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_GET_MEETINGS_OF_WORKGROUP)
    public List<IMeeting> getMeetingsOfWorkgroup(long workgroupId) {
        return workgroupManagementService.getMeetingsOfWorkgroup(workgroupId);
    }

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_GET_CURRENT_MEETING)
    public Optional<IMeeting> getCurrentMeeting(@PathVariable("workgroupId") long workgroupId) {
        return workgroupManagementService.getCurrentMeeting(workgroupId);
    }

    @PostMapping(WorkgroupManagementApiConstants.MAPPING_EXTEND_CURRENT_MEETING)
    public boolean extendCurrentMeeting(
            @PathVariable("workgroupId") long workgroupId,
            @RequestParam(value = "extension-in-minutes", defaultValue = "10") long extensionInMinutes) {
        return workgroupManagementService.extendCurrentMeeting(workgroupId, extensionInMinutes);
    }
}
