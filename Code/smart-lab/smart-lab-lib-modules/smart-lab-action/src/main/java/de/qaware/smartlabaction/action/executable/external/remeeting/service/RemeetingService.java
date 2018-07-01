package de.qaware.smartlabaction.action.executable.external.remeeting.service;

import de.qaware.smartlabaction.action.executable.external.remeeting.client.IRemeetingApiClient;
import de.qaware.smartlabcore.data.action.speechtotext.ITranscript;
import de.qaware.smartlabcore.exception.ServiceFailedException;
import de.qaware.smartlabcore.miscellaneous.Language;
import de.qaware.smartlabcore.miscellaneous.Property;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.SPEECH_TO_TEXT_SERVICE,
        name = Property.Name.SPEECH_TO_TEXT_SERVICE,
        havingValue = Property.Value.SpeechToTextService.REMEETING)
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
    public ITranscript speechToText(Path audioFile, Language spokenLanguage) throws ServiceFailedException {
        if(spokenLanguage != Language.EN_US) {
            String errorMessage = format("The language %s is not supported by Remeeting speech to text", spokenLanguage);
            log.error(errorMessage);
            throw new ServiceFailedException(errorMessage);
        }
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
