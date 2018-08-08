package de.qaware.smartlab.actuator.adapter.adapters.speechtotext.remeeting;

import de.qaware.smartlab.actuator.adapter.adapters.generic.AbstractDeviceAdapter;
import de.qaware.smartlab.core.data.action.speechtotext.ISpeechToTextAdapter;
import de.qaware.smartlab.core.data.action.speechtotext.ITranscript;
import de.qaware.smartlab.core.exception.ServiceFailedException;
import de.qaware.smartlab.core.miscellaneous.Language;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static java.nio.file.Files.readAllBytes;
import static java.util.Objects.isNull;

@Slf4j
public class RemeetingAdapter extends AbstractDeviceAdapter implements ISpeechToTextAdapter {

    public static final String SERVICE_ID = "remeeting";
    private static final boolean HAS_LOCAL_API = false;

    private final IRemeetingApiClient remeetingApiClient;
    private final String remeetingApiKey;

    public RemeetingAdapter(
            IRemeetingApiClient remeetingApiClient,
            String remeetingApiKey) {
        super(SERVICE_ID, HAS_LOCAL_API);
        if(isNull(remeetingApiKey)) throw new NullPointerException(remeetingApiKey);
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
            SubmitJobResponse submitJobResponse = this.remeetingApiClient.submitJob(authHeader, readAllBytes(audioFile));
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
