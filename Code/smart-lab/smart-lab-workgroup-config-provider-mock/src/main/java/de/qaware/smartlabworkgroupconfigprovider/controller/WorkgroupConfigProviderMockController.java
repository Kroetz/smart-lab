package de.qaware.smartlabworkgroupconfigprovider.controller;

import de.qaware.smartlabcommons.api.configprovidermock.WorkgroupConfigProviderMockApiConstants;
import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import de.qaware.smartlabcore.generic.controller.AbstractSmartLabController;
import de.qaware.smartlabworkgroupconfigprovider.service.IWorkgroupConfigProviderMockService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(WorkgroupConfigProviderMockApiConstants.MAPPING_BASE)
public class WorkgroupConfigProviderMockController extends AbstractSmartLabController {

    private final IWorkgroupConfigProviderMockService workgroupConfigProviderService;

    public WorkgroupConfigProviderMockController(IWorkgroupConfigProviderMockService workgroupConfigProviderService) {
        this.workgroupConfigProviderService = workgroupConfigProviderService;
    }

    @GetMapping(WorkgroupConfigProviderMockApiConstants.MAPPING_GET_WORKGROUPS)
    public List<Workgroup> getWorkgroups() {
        return workgroupConfigProviderService.getWorkgroups();
    }

    @GetMapping(WorkgroupConfigProviderMockApiConstants.MAPPING_GET_WORKGROUP)
    public ResponseEntity<Workgroup> getWorkgroup(@PathVariable("workgroupId") long workgroupId) {
        return responseFromOptional(workgroupConfigProviderService.getWorkgroup(workgroupId));
    }

    @PostMapping(
            value = WorkgroupConfigProviderMockApiConstants.MAPPING_CREATE_WORKGROUP,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean createWorkgroup(@RequestBody Workgroup workgroup) {
        return workgroupConfigProviderService.createWorkgroup(workgroup);
    }

    @DeleteMapping(WorkgroupConfigProviderMockApiConstants.MAPPING_DELETE_WORKGROUP)
    public boolean deleteWorkgroup(@PathVariable("workgroupId") long workgroupId) {
        return workgroupConfigProviderService.deleteWorkgroup(workgroupId);
    }
}
