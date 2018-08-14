package de.qaware.smartlab.api.service.client.job;

import de.qaware.smartlab.api.service.constant.job.JobManagementApiConstants;
import de.qaware.smartlab.core.data.job.IJobInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.Set;

@FeignClient(name = JobManagementApiConstants.FEIGN_CLIENT_NAME, path = JobManagementApiConstants.MAPPING_BASE)
public interface IJobManagementApiClient {

    @GetMapping(JobManagementApiConstants.MAPPING_FIND_ALL)
    Set<IJobInfo> findAll();

    @GetMapping(JobManagementApiConstants.MAPPING_FIND_ONE)
    ResponseEntity<IJobInfo> findOne(@PathVariable(JobManagementApiConstants.PARAMETER_NAME_JOB_ID) Long jobId);

    @PostMapping(JobManagementApiConstants.MAPPING_RECORD_NEW_JOB)
    ResponseEntity<IJobInfo> recordNewJob(
            @RequestParam(value = JobManagementApiConstants.PARAMETER_NAME_CALLBACK_URL, required = false) String callbackUrl);

    @PostMapping(JobManagementApiConstants.MAPPING_MARK_JOB_AS_PROCESSING)
    ResponseEntity<Void> markJobAsProcessing(@PathVariable(JobManagementApiConstants.PARAMETER_NAME_JOB_ID) Long jobId);

    @PostMapping(JobManagementApiConstants.MAPPING_MARK_JOB_AS_FINISHED)
    ResponseEntity<Void> markJobAsFinished(@PathVariable(JobManagementApiConstants.PARAMETER_NAME_JOB_ID) Long jobId);

    @PostMapping(JobManagementApiConstants.MAPPING_MARK_JOB_AS_FAILED)
    ResponseEntity<Void> markJobAsFailed(
            @PathVariable(JobManagementApiConstants.PARAMETER_NAME_JOB_ID) Long jobId,
            @RequestBody String errorMessage);

    @GetMapping(JobManagementApiConstants.MAPPING_GET_BASE_URL)
    ResponseEntity<URL> getBaseUrl();
}
