package de.qaware.smartlabcommons.api.external.watson.speechtotext;

import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults;
import de.qaware.smartlabcommons.data.action.web.ITranscript;
import de.qaware.smartlabcommons.exception.ServiceFailedException;
import de.qaware.smartlabcommons.miscellaneous.ProfileNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Component
@Profile(ProfileNames.WATSON_SPEECH_TO_TEXT)
@Slf4j
public class WatsonSpeechToTextService implements IWatsonSpeechToTextService {

    private final SpeechToText speechToTextService;

    public WatsonSpeechToTextService(
            String watsonSpeechToTextUserName,
            String watsonSpeechToTextPassword) {
        this.speechToTextService = new SpeechToText(watsonSpeechToTextUserName, watsonSpeechToTextPassword);
        Map<String, String> defaultHeaders = new HashMap<>();
        // Set default headers, e.g. defaultHeaders.put(HttpHeaders.CONTENT_TYPE, HttpMediaType.AUDIO_WEBM);
        this.speechToTextService.setDefaultHeaders(defaultHeaders);
    }

    @Override
    public ITranscript speechToText(Path audioFile) throws ServiceFailedException {
        try {
            RecognizeOptions options = new RecognizeOptions.Builder()
                    // TODO: Get file type from assistance config or determine automatically
                    .contentType(HttpMediaType.AUDIO_WAV)
                    .speakerLabels(true)
                    // TODO: Get language from assistance config
                    .model("en-US_BroadbandModel")
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
}
