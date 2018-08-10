package de.qaware.smartlab.job.service.controller;

import de.qaware.smartlab.api.service.constant.job.JobManagementApiConstants;
import de.qaware.smartlab.core.data.job.IJobInfo;
import de.qaware.smartlab.core.service.controller.AbstractSmartLabController;
import de.qaware.smartlab.core.service.controller.url.AbstractBaseUrlController;
import de.qaware.smartlab.core.service.url.IBaseUrlDetector;
import de.qaware.smartlab.job.service.business.IJobManagementBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping(JobManagementApiConstants.MAPPING_BASE)
@Slf4j
public class JobManagementController extends AbstractSmartLabController {

    private final IJobManagementBusinessLogic jobManagementBusinessLogic;
    private final UrlValidator urlValidator;

    public JobManagementController(
            IJobManagementBusinessLogic jobManagementBusinessLogic,
            UrlValidator urlValidator) {
        this.jobManagementBusinessLogic = jobManagementBusinessLogic;
        this.urlValidator = urlValidator;
    }

    @GetMapping(JobManagementApiConstants.MAPPING_FIND_ALL)
    public Set<IJobInfo> findAll() {
        log.info("Received call to find all existing jobs");
        return this.jobManagementBusinessLogic.findAll();
    }

    @GetMapping(JobManagementApiConstants.MAPPING_FIND_ONE)
    public ResponseEntity<IJobInfo> findOne(@PathVariable(JobManagementApiConstants.PARAMETER_NAME_JOB_ID) Long jobId) {
        log.info("Received call to find the job \"{}\"", jobId);
        return responseFromOptional(this.jobManagementBusinessLogic.findOne(jobId));
    }

    @PostMapping(JobManagementApiConstants.MAPPING_RECORD_NEW_JOB)
    public ResponseEntity<IJobInfo> recordNewJob(
            @RequestParam(value = JobManagementApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl) {
        log.info("Received call to record a new job");
        try {
            if(nonNull(callbackUrl) && !this.urlValidator.isValid(callbackUrl)) throw new MalformedURLException();
            return ResponseEntity.ok().body(this.jobManagementBusinessLogic.recordNewJob(
                    nonNull(callbackUrl) ? new URL(callbackUrl) : null));
        } catch (MalformedURLException e) {
            // TODO: Better exception and message
            throw new RuntimeException(e);
        }
    }

    @PostMapping(JobManagementApiConstants.MAPPING_MARK_JOB_AS_PROCESSING)
    public ResponseEntity<Void> markJobAsProcessing(@PathVariable(JobManagementApiConstants.PARAMETER_NAME_JOB_ID) Long jobId) {
        log.info("Received call that processing of job \"{}\" has started", jobId);
        this.jobManagementBusinessLogic.markJobAsProcessing(jobId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(JobManagementApiConstants.MAPPING_MARK_JOB_AS_FINISHED)
    public ResponseEntity<Void> markJobAsFinished(@PathVariable(JobManagementApiConstants.PARAMETER_NAME_JOB_ID) Long jobId) {
        log.info("Received call that processing of job \"{}\" has finished", jobId);
        this.jobManagementBusinessLogic.markJobAsFinished(jobId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(JobManagementApiConstants.MAPPING_MARK_JOB_AS_FAILED)
    public ResponseEntity<Void> markJobAsFailed(
            @PathVariable(JobManagementApiConstants.PARAMETER_NAME_JOB_ID) Long jobId,
            @RequestBody String errorMessage) {
        log.info("Received call that processing of job \"{}\" has failed", jobId);
        this.jobManagementBusinessLogic.markJobAsFailed(jobId, errorMessage);
        return ResponseEntity.ok().build();
    }

    @RestController
    @RequestMapping(JobManagementApiConstants.MAPPING_BASE)
    @Slf4j
    public static class BaseUrlController extends AbstractBaseUrlController {

        public BaseUrlController(IBaseUrlDetector baseUrlDetector) {
            super(baseUrlDetector);
        }

        @Override
        @GetMapping(JobManagementApiConstants.MAPPING_GET_BASE_URL)
        public ResponseEntity<URL> getBaseUrl() {
            return super.getBaseUrl();
        }
    }
}
