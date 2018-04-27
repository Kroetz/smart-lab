package de.qaware.smartlabcommons.api.client;

import de.qaware.smartlabcommons.api.WorkgroupManagementApiConstants;
import de.qaware.smartlabcommons.api.client.generic.ICrudApiClient;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@FeignClient(
        value = WorkgroupManagementApiConstants.FEIGN_CLIENT_VALUE,
        url = WorkgroupManagementApiConstants.FEIGN_CLIENT_URL)
@Component
public interface IWorkgroupManagementApiClient extends ICrudApiClient<IWorkgroup> {

    @Override
    @GetMapping(WorkgroupManagementApiConstants.MAPPING_BASE + WorkgroupManagementApiConstants.MAPPING_GET_WORKGROUPS)
    Set<IWorkgroup> findAll();

    @Override
    @GetMapping(WorkgroupManagementApiConstants.MAPPING_BASE + WorkgroupManagementApiConstants.MAPPING_GET_WORKGROUP)
    ResponseEntity<IWorkgroup> findOne(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId);

    @Override
    @PostMapping(value = WorkgroupManagementApiConstants.MAPPING_BASE + WorkgroupManagementApiConstants.MAPPING_CREATE_WORKGROUP, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> create(@RequestBody IWorkgroup workgroup);

    @Override
    @DeleteMapping(WorkgroupManagementApiConstants.MAPPING_BASE + WorkgroupManagementApiConstants.MAPPING_DELETE_WORKGROUP)
    ResponseEntity<Void> delete(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId);

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_BASE + WorkgroupManagementApiConstants.MAPPING_GET_MEETINGS_OF_WORKGROUP)
    ResponseEntity<Set<IMeeting>> getMeetingsOfWorkgroup(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID)String workgroupId);

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_BASE + WorkgroupManagementApiConstants.MAPPING_GET_CURRENT_MEETING)
    ResponseEntity<IMeeting> getCurrentMeeting(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId);

    @PostMapping(WorkgroupManagementApiConstants.MAPPING_BASE + WorkgroupManagementApiConstants.MAPPING_EXTEND_CURRENT_MEETING)
    ResponseEntity<Void> extendCurrentMeeting(
            @PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(WorkgroupManagementApiConstants.PARAMETER_NAME_EXTENSION_IN_MINUTES) long extensionInMinutes);
}
