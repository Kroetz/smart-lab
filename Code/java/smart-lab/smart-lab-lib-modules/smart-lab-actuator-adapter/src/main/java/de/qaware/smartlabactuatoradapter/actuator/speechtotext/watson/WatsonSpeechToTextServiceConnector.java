package de.qaware.smartlabactuatoradapter.actuator.speechtotext.watson;

import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults;
import de.qaware.smartlabcore.data.action.speechtotext.ITranscript;
import de.qaware.smartlabcore.exception.ServiceFailedException;
import de.qaware.smartlabcore.miscellaneous.Language;
import de.qaware.smartlabcore.miscellaneous.Property;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.SPEECH_TO_TEXT_SERVICE,
        name = Property.Name.SPEECH_TO_TEXT_SERVICE,
        havingValue = Property.Value.SpeechToTextService.WATSON)
@Slf4j
public class WatsonSpeechToTextServiceConnector implements IWatsonSpeechToTextService {

    private final SpeechToText speechToTextService;

    public WatsonSpeechToTextServiceConnector(
            String watsonSpeechToTextUserName,
            String watsonSpeechToTextPassword) {
        this.speechToTextService = new SpeechToText(watsonSpeechToTextUserName, watsonSpeechToTextPassword);
        Map<String, String> defaultHeaders = new HashMap<>();
        // Set default headers, e.g. defaultHeaders.put(HttpHeaders.CONTENT_TYPE, HttpMediaType.AUDIO_WEBM);
        this.speechToTextService.setDefaultHeaders(defaultHeaders);
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
                String errorMessage = format("The language %s is not supported by IBM Watson speech to text", language);
                log.error(errorMessage);
                throw new ServiceFailedException(errorMessage);
        }
    }
}
