package de.qaware.smartlabjob.controller;

import de.qaware.smartlabapi.JobManagementApiConstants;
import de.qaware.smartlabcore.data.job.IJobInfo;
import de.qaware.smartlabcore.generic.controller.AbstractSmartLabController;
import de.qaware.smartlabjob.business.IJobManagementBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

@RestController
@RequestMapping(JobManagementApiConstants.MAPPING_BASE)
@Slf4j
public class JobManagementController extends AbstractSmartLabController {

    private final IJobManagementBusinessLogic jobManagementBusinessLogic;

    public JobManagementController(IJobManagementBusinessLogic jobManagementBusinessLogic) {
        this.jobManagementBusinessLogic = jobManagementBusinessLogic;
    }

    @GetMapping(JobManagementApiConstants.MAPPING_FIND_ALL)
    public Set<IJobInfo> findAll() {
        log.info("Received call to find all existing jobs");
        return this.jobManagementBusinessLogic.findAll();
    }

    @GetMapping(JobManagementApiConstants.MAPPING_FIND_ONE)
    public ResponseEntity<IJobInfo> findOne(@PathVariable(JobManagementApiConstants.PARAMETER_NAME_JOB_ID) Long jobId) {
        log.info("Received call to find the job with ID \"{}\"", jobId);
        return responseFromOptional(this.jobManagementBusinessLogic.findOne(jobId));
    }

    @PostMapping(JobManagementApiConstants.MAPPING_RECORD_NEW_JOB)
    public ResponseEntity<IJobInfo> recordNewJob(
            @RequestParam(value = JobManagementApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl) {
        log.info("Received call to record a new job");
        try {
            return ResponseEntity.ok().body(this.jobManagementBusinessLogic.recordNewJob(
                    callbackUrl != null? new URL(callbackUrl) : null));
        } catch (MalformedURLException e) {
            // TODO: Better exception and message
            throw new RuntimeException(e);
        }
    }

    @PostMapping(JobManagementApiConstants.MAPPING_MARK_JOB_AS_PROCESSING)
    public ResponseEntity<Void> markJobAsProcessing(@PathVariable(JobManagementApiConstants.PARAMETER_NAME_JOB_ID) Long jobId) {
        log.info("Received call that processing of job with ID \"{}\" has started", jobId);
        this.jobManagementBusinessLogic.markJobAsProcessing(jobId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(JobManagementApiConstants.MAPPING_MARK_JOB_AS_FINISHED)
    public ResponseEntity<Void> markJobAsFinished(@PathVariable(JobManagementApiConstants.PARAMETER_NAME_JOB_ID) Long jobId) {
        log.info("Received call that processing of job with ID \"{}\" has finished", jobId);
        this.jobManagementBusinessLogic.markJobAsFinished(jobId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(JobManagementApiConstants.MAPPING_MARK_JOB_AS_FAILED)
    public ResponseEntity<Void> markJobAsFailed(
            @PathVariable(JobManagementApiConstants.PARAMETER_NAME_JOB_ID) Long jobId,
            @RequestBody String errorMessage) {
        log.info("Received call that processing of job with ID \"{}\" has failed", jobId);
        this.jobManagementBusinessLogic.markJobAsFailed(jobId, errorMessage);
        return ResponseEntity.ok().build();
    }
}
