package de.qaware.smartlabcore.workgroup.controller;

import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import de.qaware.smartlabcore.entity.meeting.IMeeting;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "workgroup-management", url = "http://localhost:8080")
public interface IWorkgroupManagementApiClient {

    @GetMapping(WorkgroupManagementController.MAPPING_BASE + WorkgroupManagementController.MAPPING_GET_WORKGROUPS)
    List<Workgroup> getWorkgroups();

    @GetMapping(WorkgroupManagementController.MAPPING_BASE + WorkgroupManagementController.MAPPING_GET_WORKGROUP)
    Optional<Workgroup> getWorkgroup(@PathVariable("workgroupId") long workgroupId);

    @PostMapping(value = WorkgroupManagementController.MAPPING_BASE + WorkgroupManagementController.MAPPING_CREATE_WORKGROUP, consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean createWorkgroup(@RequestBody Workgroup workgroup);

    @DeleteMapping(WorkgroupManagementController.MAPPING_BASE + WorkgroupManagementController.MAPPING_DELETE_WORKGROUP)
    void deleteWorkgroup(@PathVariable("workgroupId") long workgroupId);

    @GetMapping(WorkgroupManagementController.MAPPING_BASE + WorkgroupManagementController.MAPPING_GET_MEETINGS_OF_WORKGROUP)
    List<IMeeting> getMeetingsOfWorkgroup(long workgroupId);

    @GetMapping(WorkgroupManagementController.MAPPING_BASE + WorkgroupManagementController.MAPPING_GET_CURRENT_MEETING)
    Optional<IMeeting> getCurrentMeeting(@PathVariable("workgroupId") long workgroupId);

    @PostMapping(WorkgroupManagementController.MAPPING_BASE + WorkgroupManagementController.MAPPING_EXTEND_CURRENT_MEETING)
    boolean extendCurrentMeeting(
            @PathVariable("workgroupId") long workgroupId,
            @RequestParam(value = "extension-in-minutes", defaultValue = "10") long extensionInMinutes);
}
