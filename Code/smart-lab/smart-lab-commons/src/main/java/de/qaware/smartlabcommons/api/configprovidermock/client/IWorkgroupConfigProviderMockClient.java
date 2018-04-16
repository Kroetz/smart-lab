package de.qaware.smartlabcommons.api.configprovidermock.client;

import de.qaware.smartlabcommons.api.configprovidermock.WorkgroupConfigProviderMockApiConstants;
import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "workgroup-config-provider", url = "http://localhost:8081")
public interface IWorkgroupConfigProviderMockClient {

    @GetMapping(WorkgroupConfigProviderMockApiConstants.MAPPING_BASE + WorkgroupConfigProviderMockApiConstants.MAPPING_GET_WORKGROUPS)
    List<Workgroup> getWorkgroups();

    @GetMapping(WorkgroupConfigProviderMockApiConstants.MAPPING_BASE + WorkgroupConfigProviderMockApiConstants.MAPPING_GET_WORKGROUP)
    ResponseEntity<Workgroup> getWorkgroup(@PathVariable("workgroupId") long workgroupId);

    @PostMapping(
            value = WorkgroupConfigProviderMockApiConstants.MAPPING_BASE + WorkgroupConfigProviderMockApiConstants.MAPPING_CREATE_WORKGROUP,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean createWorkgroup(@RequestBody Workgroup workgroup);

    @DeleteMapping(WorkgroupConfigProviderMockApiConstants.MAPPING_BASE + WorkgroupConfigProviderMockApiConstants.MAPPING_DELETE_WORKGROUP)
    boolean deleteWorkgroup(@PathVariable("workgroupId") long workgroupId);
}
