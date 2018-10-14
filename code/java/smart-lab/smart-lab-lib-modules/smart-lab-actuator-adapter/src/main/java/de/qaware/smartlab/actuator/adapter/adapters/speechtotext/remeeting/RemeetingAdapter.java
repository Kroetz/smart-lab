package de.qaware.smartlab.actuator.adapter.adapters.speechtotext.remeeting;

import de.qaware.smartlab.actuator.adapter.adapters.generic.AbstractActuatorAdapter;
import de.qaware.smartlab.core.action.speechtotext.ISpeechToTextAdapter;
import de.qaware.smartlab.core.action.speechtotext.ITranscript;
import de.qaware.smartlab.core.exception.actuator.ActuatorException;
import de.qaware.smartlab.core.miscellaneous.Language;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import static de.qaware.smartlab.actuator.adapter.adapters.speechtotext.remeeting.JobStatusResponse.*;
import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.readAllBytes;
import static java.time.Duration.ofSeconds;
import static java.util.Objects.isNull;

@Slf4j
public class RemeetingAdapter extends AbstractActuatorAdapter implements ISpeechToTextAdapter {

    public static final String ACTUATOR_TYPE = "remeeting";
    private static final boolean HAS_LOCAL_API = false;

    private static final String AUTH_HEADER_TEMPLATE = "Basic %s";
    private static final Duration POLLING_INTERVAL = ofSeconds(10);

    private final IRemeetingApiClient remeetingApiClient;
    private final String remeetingApiKey;
    private final RemeetingTranscript.Factory transcriptFactory;

    public RemeetingAdapter(
            IRemeetingApiClient remeetingApiClient,
            String remeetingApiKey,
            RemeetingTranscript.Factory transcriptFactory) {
        super(ACTUATOR_TYPE, HAS_LOCAL_API);
        if(isNull(remeetingApiKey)) throw new NullPointerException(remeetingApiKey);
        this.remeetingApiClient = remeetingApiClient;
        this.remeetingApiKey = remeetingApiKey;
        this.transcriptFactory = transcriptFactory;
    }

    @Override
    public ITranscript speechToText(Path audioFile, Language spokenLanguage) throws ActuatorException {
        if(spokenLanguage != Language.EN_US) {
            String errorMessage = format("The language %s is not supported by Remeeting speech to text", spokenLanguage);
            log.error(errorMessage);
            throw new ActuatorException(errorMessage);
        }
        try {
            String authHeader = buildAuthHeader(this.remeetingApiKey);
            JobSubmissionResponse jobSubmissionResponse = this.remeetingApiClient.submitJob(authHeader, readAllBytes(audioFile));
            String jobId = jobSubmissionResponse.getId();
            JobStatusResponse jobStatusResponse;
            do {
                log.info("Remeeting is processing the submitted job...");
                TimeUnit.SECONDS.sleep(POLLING_INTERVAL.getSeconds());
                jobStatusResponse = this.remeetingApiClient.queryResult(jobId, authHeader);
            } while(jobStatusResponse.getStatus().equals(STATUS_WAITING) || jobStatusResponse.getStatus().equals(STATUS_PROCESSING));
            if(jobStatusResponse.getStatus().equals(STATUS_COMPLETED)) {
                return this.transcriptFactory.of(jobStatusResponse);
            }
        }
        catch(Exception e) {
            String errorMessage = format("Could not convert the audio file %s to text with Remeeting", audioFile);
            log.error(errorMessage);
            throw new ActuatorException(errorMessage, e);
        }
        String errorMessage = format("Speech-to-text conversion of audio file %s with Remeeting timed out", audioFile);
        log.error(errorMessage);
        throw new ActuatorException(errorMessage);
    }

    private String buildAuthHeader(String apiKey) throws UnsupportedEncodingException {
        return format(
                AUTH_HEADER_TEMPLATE,
                Base64.getEncoder().encodeToString((apiKey + ":").getBytes(UTF_8)));
    }
}
