package de.qaware.smartlabcore.workgroup.repository.mock;

import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "workgroup-config-provider", url = "http://localhost:8081")
@ApiIgnore
@RestController
@RequestMapping("/smart-lab/workgroup-config-provider")
public interface IWorkgroupConfigProviderMockClient {

    @PostMapping(value = "/{workgroupId}/exists")
    boolean exists(@PathVariable("workgroupId") long workgroupId);

    @GetMapping
    List<Workgroup> getWorkgroups();

    @GetMapping("/{workgroupId}")
    Optional<Workgroup> getWorkgroup(@PathVariable("workgroupId") long workgroupId);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean createWorkgroup(@RequestBody Workgroup workgroup);

    @DeleteMapping("/{workgroupId}")
    boolean deleteWorkgroup(@PathVariable("workgroupId") long workgroupId);
}
