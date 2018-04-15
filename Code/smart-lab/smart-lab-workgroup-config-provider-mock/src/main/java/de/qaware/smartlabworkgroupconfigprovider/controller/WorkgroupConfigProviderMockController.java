package de.qaware.smartlabworkgroupconfigprovider.controller;

import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import de.qaware.smartlabworkgroupconfigprovider.service.IWorkgroupConfigProviderMockService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/smart-lab/workgroup-config-provider")
public class WorkgroupConfigProviderMockController {

    private final IWorkgroupConfigProviderMockService workgroupConfigProviderService;

    public WorkgroupConfigProviderMockController(IWorkgroupConfigProviderMockService workgroupConfigProviderService) {
        this.workgroupConfigProviderService = workgroupConfigProviderService;
    }

    @PostMapping("/{workgroupId}/exists")
    public boolean exists(@PathVariable("workgroupId") long workgroupId) {
        return workgroupConfigProviderService.exists(workgroupId);
    }

    @GetMapping
    public List<Workgroup> getWorkgroups() {
        return workgroupConfigProviderService.getWorkgroups();
    }

    @GetMapping("/{workgroupId}")
    public Optional<Workgroup> getWorkgroup(@PathVariable("workgroupId") long workgroupId) {
        return workgroupConfigProviderService.getWorkgroup(workgroupId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean createWorkgroup(@RequestBody Workgroup workgroup) {
        return workgroupConfigProviderService.createWorkgroup(workgroup);
    }

    @DeleteMapping("/{workgroupId}")
    public boolean deleteWorkgroup(@PathVariable("workgroupId") long workgroupId) {
        return workgroupConfigProviderService.deleteWorkgroup(workgroupId);
    }
}
