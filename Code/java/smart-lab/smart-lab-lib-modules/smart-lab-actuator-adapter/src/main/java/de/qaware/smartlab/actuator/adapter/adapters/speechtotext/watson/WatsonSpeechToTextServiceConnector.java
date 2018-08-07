package de.qaware.smartlab.actuator.adapter.adapters.speechtotext.watson;

import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults;
import de.qaware.smartlabcore.data.action.speechtotext.ITranscript;
import de.qaware.smartlabcore.exception.ServiceFailedException;
import de.qaware.smartlabcore.miscellaneous.Language;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Objects.isNull;

@Slf4j
public class WatsonSpeechToTextServiceConnector implements IWatsonSpeechToTextService {

    private static final String SERVICE_ID = "watson";

    private final SpeechToText speechToTextService;

    public WatsonSpeechToTextServiceConnector(
            String watsonSpeechToTextUserName,
            String watsonSpeechToTextPassword) {
        if(isNull(watsonSpeechToTextUserName)) throw new NullPointerException(watsonSpeechToTextUserName);
        if(isNull(watsonSpeechToTextPassword)) throw new NullPointerException(watsonSpeechToTextPassword);
        this.speechToTextService = new SpeechToText(watsonSpeechToTextUserName, watsonSpeechToTextPassword);
        Map<String, String> defaultHeaders = new HashMap<>();
        // Set default headers, e.g. defaultHeaders.put(HttpHeaders.CONTENT_TYPE, HttpMediaType.AUDIO_WEBM);
        this.speechToTextService.setDefaultHeaders(defaultHeaders);
    }

    @Override
    public String getServiceId() {
        return WatsonSpeechToTextServiceConnector.SERVICE_ID;
    }

    @Override
    public ITranscript speechToText(Path audioFile, Language spokenLanguage) throws ServiceFailedException {
        try {
            RecognizeOptions options = new RecognizeOptions.Builder()
                    // TODO: Get file type from assistance config or determine automatically
                    .contentType(HttpMediaType.AUDIO_WAV)
                    .speakerLabels(true)
                    .model(toWatsonSpeechToTextLanguage(spokenLanguage))
                    .timestamps(true)
                    .smartFormatting(true)
                    .audio(audioFile.toFile())
                    .build();
            SpeechRecognitionResults speechRecognitionResults = this.speechToTextService.recognize(options).execute();
            return WatsonSpeechToTextTranscript.of(speechRecognitionResults);
        }
        catch (Exception e) {
            // TODO: exception message
            throw new ServiceFailedException(e);
        }
    }

    private String toWatsonSpeechToTextLanguage(Language language) {
        switch (language) {
            case EN_US:
                return "en-US_BroadbandModel";
            case EN_GB:
                return "en-GB_BroadbandModel";
            case FR_FR:
                return "fr-FR_BroadbandModel";
            default:
                String errorMessage = format("The language %s is not supported by IBM Watson speech-to-text", language);
                log.error(errorMessage);
                throw new ServiceFailedException(errorMessage);
        }
    }
}
