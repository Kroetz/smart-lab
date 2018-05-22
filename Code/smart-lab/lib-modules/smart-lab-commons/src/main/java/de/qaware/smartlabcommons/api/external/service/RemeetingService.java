package de.qaware.smartlabcommons.api.external.service;

import de.qaware.smartlabcommons.api.external.client.IRemeetingApiClient;
import de.qaware.smartlabcommons.data.action.web.ITranscript;
import de.qaware.smartlabcommons.exception.ServiceFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RemeetingService implements IRemeetingService {

    private IRemeetingApiClient remeetingApiClient;
    private String remeetingApiKey;

    public RemeetingService(
            IRemeetingApiClient remeetingApiClient,
            String remeetingApiKey) {
        this.remeetingApiClient = remeetingApiClient;
        this.remeetingApiKey = remeetingApiKey;
    }

    @Override
    public ITranscript speechToText(Path audioFile) throws ServiceFailedException {
        try {
            // TODO: String literals
            String authHeader = "Basic " + Base64.getEncoder().encodeToString((this.remeetingApiKey + ":").getBytes("utf-8"));
            SubmitJobResponse submitJobResponse = this.remeetingApiClient.submitJob(authHeader, Files.readAllBytes(audioFile));
            String jobId = submitJobResponse.getId();
            QueryResultResponse queryResultResponse;
            do {
                // TODO: Do not exceed maximal waiting time
                // TODO: Log progess of job processing
                log.info("Remeeting is processing the submitted job...");
                TimeUnit.SECONDS.sleep(10);
                queryResultResponse = this.remeetingApiClient.queryResult(jobId, authHeader);
            } while(queryResultResponse.getStatus().equals("waiting") || queryResultResponse.getStatus().equals("processing"));
            if(queryResultResponse.getStatus().equals("completed")) {
                return queryResultResponse;
            }
        } catch (Exception e) {
            throw new ServiceFailedException(e);
        }
        // TODO: exception message
        throw new ServiceFailedException();
    }
}
