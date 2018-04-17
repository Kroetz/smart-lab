package de.qaware.smartlabcommons.api.configprovidermock.client;

import de.qaware.smartlabcommons.api.configprovidermock.WorkgroupConfigProviderMockApiConstants;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "workgroup-config-provider", url = "http://localhost:8081")
@Component
public interface IWorkgroupConfigProviderMockClient {

    @GetMapping(WorkgroupConfigProviderMockApiConstants.MAPPING_BASE + WorkgroupConfigProviderMockApiConstants.MAPPING_GET_WORKGROUPS)
    List<IWorkgroup> getWorkgroups();

    @GetMapping(WorkgroupConfigProviderMockApiConstants.MAPPING_BASE + WorkgroupConfigProviderMockApiConstants.MAPPING_GET_WORKGROUP)
    ResponseEntity<IWorkgroup> getWorkgroup(@PathVariable("workgroupId") long workgroupId);

    @PostMapping(
            value = WorkgroupConfigProviderMockApiConstants.MAPPING_BASE + WorkgroupConfigProviderMockApiConstants.MAPPING_CREATE_WORKGROUP,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean createWorkgroup(@RequestBody IWorkgroup workgroup);

    @DeleteMapping(WorkgroupConfigProviderMockApiConstants.MAPPING_BASE + WorkgroupConfigProviderMockApiConstants.MAPPING_DELETE_WORKGROUP)
    boolean deleteWorkgroup(@PathVariable("workgroupId") long workgroupId);
}
