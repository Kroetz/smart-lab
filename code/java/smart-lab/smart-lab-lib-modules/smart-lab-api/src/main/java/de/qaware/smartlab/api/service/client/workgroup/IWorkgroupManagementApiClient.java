package de.qaware.smartlab.api.service.client.workgroup;

import de.qaware.smartlab.api.service.client.generic.IBasicEntityManagementApiClient;
import de.qaware.smartlab.core.data.event.EventDto;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.data.workgroup.WorkgroupDto;
import de.qaware.smartlab.api.service.constant.workgroup.WorkgroupManagementApiConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.Set;

@FeignClient(name = WorkgroupManagementApiConstants.FEIGN_CLIENT_NAME, path = WorkgroupManagementApiConstants.MAPPING_BASE)
public interface IWorkgroupManagementApiClient extends IBasicEntityManagementApiClient<WorkgroupDto> {

    @Override
    @GetMapping(WorkgroupManagementApiConstants.MAPPING_FIND_ALL)
    @ResponseBody
    Set<WorkgroupDto> findAll();

    @Override
    @GetMapping(WorkgroupManagementApiConstants.MAPPING_FIND_ONE)
    @ResponseBody
    ResponseEntity<WorkgroupDto> findOne(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId);

    @Override
    @GetMapping(WorkgroupManagementApiConstants.MAPPING_FIND_MULTIPLE)
    @ResponseBody
    ResponseEntity<Set<WorkgroupDto>> findMultiple(@RequestParam(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_IDS) String[] workgroupIds);

    @Override
    @PostMapping(value = WorkgroupManagementApiConstants.MAPPING_CREATE_SINGLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<WorkgroupDto> create(@RequestBody WorkgroupDto workgroup);

    @Override
    @PostMapping(value = WorkgroupManagementApiConstants.MAPPING_CREATE_MULTIPLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<Set<WorkgroupDto>> create(@RequestBody Set<WorkgroupDto> workgroups);

    @Override
    @DeleteMapping(WorkgroupManagementApiConstants.MAPPING_DELETE)
    @ResponseBody
    ResponseEntity<Void> delete(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId);

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_GET_EVENTS_OF_WORKGROUP)
    @ResponseBody
    ResponseEntity<Set<EventDto>> getEventsOfWorkgroup(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID)String workgroupId);

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_GET_CURRENT_EVENT)
    @ResponseBody
    ResponseEntity<EventDto> getCurrentEvent(@PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId);

    @PostMapping(WorkgroupManagementApiConstants.MAPPING_EXTEND_CURRENT_EVENT)
    @ResponseBody
    ResponseEntity<Void> extendCurrentEvent(
            @PathVariable(WorkgroupManagementApiConstants.PARAMETER_NAME_WORKGROUP_ID) String workgroupId,
            @RequestParam(WorkgroupManagementApiConstants.PARAMETER_NAME_EXTENSION_IN_MINUTES) long extensionInMinutes);

    @GetMapping(WorkgroupManagementApiConstants.MAPPING_GET_BASE_URL)
    ResponseEntity<URL> getBaseUrl();
}
