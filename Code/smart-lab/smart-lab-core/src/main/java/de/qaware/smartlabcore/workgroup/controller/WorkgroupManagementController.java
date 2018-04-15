package de.qaware.smartlabcore.workgroup.controller;

import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import de.qaware.smartlabcore.entity.meeting.IMeeting;
import de.qaware.smartlabcore.workgroup.service.IWorkgroupManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(WorkgroupManagementController.MAPPING_BASE)
@Slf4j
public class WorkgroupManagementController {

    public static final String MAPPING_BASE = "/smart-lab/api/workgroup";
    public static final String MAPPING_GET_WORKGROUPS = "";
    public static final String MAPPING_GET_WORKGROUP = "/{workgroupId}";
    public static final String MAPPING_CREATE_WORKGROUP = "";
    public static final String MAPPING_DELETE_WORKGROUP = "/{workgroupId}";
    public static final String MAPPING_GET_MEETINGS_OF_WORKGROUP = "/{workgroupId}/meetings";
    public static final String MAPPING_GET_CURRENT_MEETING = "/{workgroupId}/current-meeting";
    public static final String MAPPING_EXTEND_CURRENT_MEETING = "/{workgroupId}/extend-current-meeting";

    private final IWorkgroupManagementService workgroupManagementService;

    public WorkgroupManagementController(@Qualifier("mock") IWorkgroupManagementService workgroupManagementService) {
        this.workgroupManagementService = workgroupManagementService;
    }

    @GetMapping(MAPPING_GET_WORKGROUPS)
    public List<Workgroup> getWorkgroups() {
        return workgroupManagementService.getWorkgroups();
    }

    @GetMapping(MAPPING_GET_WORKGROUP)
    public Optional<Workgroup> getWorkgroup(@PathVariable("workgroupId") long workgroupId) {
        return workgroupManagementService.getWorkgroup(workgroupId);
    }

    @PostMapping(value = MAPPING_CREATE_WORKGROUP, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean createWorkgroup(@RequestBody Workgroup workgroup) {
        return workgroupManagementService.createWorkgroup(workgroup);
    }

    @DeleteMapping(MAPPING_DELETE_WORKGROUP)
    public void deleteWorkgroup(@PathVariable("workgroupId") long workgroupId) {
        workgroupManagementService.deleteWorkgroup(workgroupId);
    }

    @GetMapping(MAPPING_GET_MEETINGS_OF_WORKGROUP)
    public List<IMeeting> getMeetingsOfWorkgroup(long workgroupId) {
        return workgroupManagementService.getMeetingsOfWorkgroup(workgroupId);
    }

    @GetMapping(MAPPING_GET_CURRENT_MEETING)
    public Optional<IMeeting> getCurrentMeeting(@PathVariable("workgroupId") long workgroupId) {
        return workgroupManagementService.getCurrentMeeting(workgroupId);
    }

    @PostMapping(MAPPING_EXTEND_CURRENT_MEETING)
    public boolean extendCurrentMeeting(
            @PathVariable("workgroupId") long workgroupId,
            @RequestParam(value = "extension-in-minutes", defaultValue = "10") long extensionInMinutes) {
        return workgroupManagementService.extendCurrentMeeting(workgroupId, extensionInMinutes);
    }
}
