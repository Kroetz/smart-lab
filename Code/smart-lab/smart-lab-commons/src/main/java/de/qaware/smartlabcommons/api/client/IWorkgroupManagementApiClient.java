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

@FeignClient(
        value = WorkgroupManagementApiConstants.FEIGN_CLIENT_VALUE,
        url = WorkgroupManagementApiConstants.FEIGN_CLIENT_URL)
@Component
public interface IWorkgroupManagementApiClient {

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_BASE + WorkgroupManagementApiConstants.MAPPING_GET_WORKGROUPS)
    List<IWorkgroup> getWorkgroups();

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_BASE + WorkgroupManagementApiConstants.MAPPING_GET_WORKGROUP)
    ResponseEntity<IWorkgroup> getWorkgroup(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId);

    @PostMapping(value = WorkgroupManagementApiConstants.MAPPING_BASE + WorkgroupManagementApiConstants.MAPPING_CREATE_WORKGROUP, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> createWorkgroup(@RequestBody IWorkgroup workgroup);

    @DeleteMapping(WorkgroupManagementApiConstants.MAPPING_BASE + WorkgroupManagementApiConstants.MAPPING_DELETE_WORKGROUP)
    ResponseEntity<Void> deleteWorkgroup(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId);

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_BASE + WorkgroupManagementApiConstants.MAPPING_GET_MEETINGS_OF_WORKGROUP)
    List<IMeeting> getMeetingsOfWorkgroup(String workgroupId);

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_BASE + WorkgroupManagementApiConstants.MAPPING_GET_CURRENT_MEETING)
    ResponseEntity<IMeeting> getCurrentMeeting(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId);

    @PostMapping(WorkgroupManagementApiConstants.MAPPING_BASE + WorkgroupManagementApiConstants.MAPPING_EXTEND_CURRENT_MEETING)
    ResponseEntity<Void> extendCurrentMeeting(
            @PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(WorkgroupManagementApiConstants.PARAMETER_NAME_EXTENSION_IN_MINUTES) long extensionInMinutes);
}
