package de.qaware.smartlabcore.workgroup.controller;

import de.qaware.smartlabcore.entity.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import de.qaware.smartlabcore.workgroup.service.IWorkgroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/smart-lab/api/workgroup")
@Slf4j
public class WorkgroupController {

    private final IWorkgroupService workgroupService;

    @Autowired
    public WorkgroupController(@Qualifier("mock") IWorkgroupService workgroupService) {
        this.workgroupService = workgroupService;
    }

    @GetMapping
    public List<Workgroup> getWorkgroups() {
        return workgroupService.getWorkgroups();
    }

    @GetMapping("/{workgroupId}")
    public Optional<Workgroup> getWorkgroup(@PathVariable("workgroupId") long workgroupId) {
        return workgroupService.getWorkgroup(workgroupId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean createWorkgroup(@RequestBody Workgroup workgroup) {
        return workgroupService.createWorkgroup(workgroup);
    }

    @DeleteMapping("/{workgroupId}")
    public void deleteWorkgroup(@PathVariable("workgroupId") long workgroupId) {
        workgroupService.deleteWorkgroup(workgroupId);
    }

    @GetMapping(value = "/{workgroupId}/current-meeting")
    public Optional<IMeeting> getCurrentMeeting(@PathVariable("workgroupId") long workgroupId) {
        return workgroupService.getCurrentMeeting(workgroupId);
    }

    @PostMapping(value = "/{workgroupId}/extend-current-meeting")
    public boolean extendCurrentMeeting(
            @PathVariable("workgroupId") long workgroupId,
            @RequestParam(value = "extension-in-minutes", defaultValue = "10") long extensionInMinutes) {
        return workgroupService.extendCurrentMeeting(workgroupId, extensionInMinutes);
    }
}
