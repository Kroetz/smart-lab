package de.qaware.smartlabcommons.api.client;

import de.qaware.smartlabcommons.api.WorkgroupManagementApiConstants;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "workgroup-management", url = "http://localhost:8080")
@Component
public interface IWorkgroupManagementApiClient {

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_BASE + WorkgroupManagementApiConstants.MAPPING_GET_WORKGROUPS)
    List<IWorkgroup> getWorkgroups();

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_BASE + WorkgroupManagementApiConstants.MAPPING_GET_WORKGROUP)
    ResponseEntity<IWorkgroup> getWorkgroup(@PathVariable("workgroupId") long workgroupId);

    @PostMapping(value = WorkgroupManagementApiConstants.MAPPING_BASE + WorkgroupManagementApiConstants.MAPPING_CREATE_WORKGROUP, consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean createWorkgroup(@RequestBody IWorkgroup workgroup);

    @DeleteMapping(WorkgroupManagementApiConstants.MAPPING_BASE + WorkgroupManagementApiConstants.MAPPING_DELETE_WORKGROUP)
    boolean deleteWorkgroup(@PathVariable("workgroupId") long workgroupId);

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_BASE + WorkgroupManagementApiConstants.MAPPING_GET_MEETINGS_OF_WORKGROUP)
    List<IMeeting> getMeetingsOfWorkgroup(long workgroupId);

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_BASE + WorkgroupManagementApiConstants.MAPPING_GET_CURRENT_MEETING)
    ResponseEntity<IMeeting> getCurrentMeeting(@PathVariable("workgroupId") long workgroupId);

    @PostMapping(WorkgroupManagementApiConstants.MAPPING_BASE + WorkgroupManagementApiConstants.MAPPING_EXTEND_CURRENT_MEETING)
    boolean extendCurrentMeeting(
            @PathVariable("workgroupId") long workgroupId,
            @RequestParam(value = "extension-in-minutes", defaultValue = "10") long extensionInMinutes);
}
