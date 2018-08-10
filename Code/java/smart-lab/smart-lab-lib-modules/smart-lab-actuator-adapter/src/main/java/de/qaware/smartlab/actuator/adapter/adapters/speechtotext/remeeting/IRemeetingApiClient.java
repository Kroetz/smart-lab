package de.qaware.smartlab.actuator.adapter.adapters.speechtotext.remeeting;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = RemeetingApiConstants.FEIGN_CLIENT_NAME, url = RemeetingApiConstants.FEIGN_CLIENT_URL)
public interface IRemeetingApiClient {

    @PostMapping(
            value = RemeetingApiConstants.MAPPING_SUBMIT_JOB,
            consumes = RemeetingApiConstants.CONSUMES_TYPE_SUBMIT_JOB,
            produces = MediaType.APPLICATION_JSON_VALUE)
    SubmitJobResponse submitJob(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String credentials,
            @RequestBody byte[] audioFileAsBytes);

    @GetMapping(
            value = RemeetingApiConstants.MAPPING_QUERY_RESULT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    QueryResultResponse queryResult(
            @PathVariable(RemeetingApiConstants.PARAMETER_NAME_JOB_ID) String jobId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String credentials);
}
