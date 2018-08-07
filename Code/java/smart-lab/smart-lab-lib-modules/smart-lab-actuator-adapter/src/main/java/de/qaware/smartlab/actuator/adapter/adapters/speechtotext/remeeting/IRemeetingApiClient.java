package de.qaware.smartlab.actuator.adapter.adapters.speechtotext.remeeting;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "remeeting", url = "https://api.remeeting.com/asr/v1/")
public interface IRemeetingApiClient {

    // TODO: string literals
    @PostMapping(
            value = "/recognitions",
            consumes = "audio/wav",
            produces = MediaType.APPLICATION_JSON_VALUE)
    SubmitJobResponse submitJob(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String credentials,
            @RequestBody byte[] audioFileAsBytes);

    @GetMapping(
            value = "/recognitions/{jobId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    QueryResultResponse queryResult(
            @PathVariable("jobId") String jobId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String credentials);
}
