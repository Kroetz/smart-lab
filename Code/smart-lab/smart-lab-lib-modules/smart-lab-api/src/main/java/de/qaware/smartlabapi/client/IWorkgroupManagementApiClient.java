package de.qaware.smartlabapi.client;

import de.qaware.smartlabapi.WorkgroupManagementApiConstants;
import de.qaware.smartlabapi.client.generic.IBasicEntityManagementApiClient;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@FeignClient(name = WorkgroupManagementApiConstants.FEIGN_CLIENT_VALUE, path = WorkgroupManagementApiConstants.MAPPING_BASE)
public interface IWorkgroupManagementApiClient extends IBasicEntityManagementApiClient<IWorkgroup> {

    @Override
    @GetMapping(WorkgroupManagementApiConstants.MAPPING_FIND_ALL)
    Set<IWorkgroup> findAll();

    @Override
    @GetMapping(WorkgroupManagementApiConstants.MAPPING_FIND_ONE)
    ResponseEntity<IWorkgroup> findOne(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId);

    @Override
    @GetMapping(WorkgroupManagementApiConstants.MAPPING_FIND_MULTIPLE)
    ResponseEntity<Set<IWorkgroup>> findMultiple(@RequestParam(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_IDS) String[] workgroupIds);

    @Override
    @PostMapping(value = WorkgroupManagementApiConstants.MAPPING_CREATE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> create(@RequestBody IWorkgroup workgroup);

    @Override
    @DeleteMapping(WorkgroupManagementApiConstants.MAPPING_DELETE)
    ResponseEntity<Void> delete(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId);

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_GET_MEETINGS_OF_WORKGROUP)
    ResponseEntity<Set<IMeeting>> getMeetingsOfWorkgroup(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID)String workgroupId);

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_GET_CURRENT_MEETING)
    ResponseEntity<IMeeting> getCurrentMeeting(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId);

    @PostMapping(WorkgroupManagementApiConstants.MAPPING_EXTEND_CURRENT_MEETING)
    ResponseEntity<Void> extendCurrentMeeting(
            @PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(WorkgroupManagementApiConstants.PARAMETER_NAME_EXTENSION_IN_MINUTES) long extensionInMinutes);
}
