package de.qaware.smartlab.actuator.adapter.adapters.speechtotext.watson;

import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults;
import de.qaware.smartlab.actuator.adapter.adapters.generic.AbstractActuatorAdapter;
import de.qaware.smartlab.core.data.action.speechtotext.ISpeechToTextAdapter;
import de.qaware.smartlab.core.data.action.speechtotext.ITranscript;
import de.qaware.smartlab.core.exception.actuator.ActuatorException;
import de.qaware.smartlab.core.miscellaneous.Language;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Objects.isNull;

@Slf4j
public class WatsonSpeechToTextAdapter extends AbstractActuatorAdapter implements ISpeechToTextAdapter {

    private static final String ACTUATOR_TYPE = "watson";
    private static final boolean HAS_LOCAL_API = false;

    private final SpeechToText service;
    private final WatsonSpeechToTextTranscript.Factory transcriptFactory;

    public WatsonSpeechToTextAdapter(
            String watsonSpeechToTextUserName,
            String watsonSpeechToTextPassword,
            WatsonSpeechToTextTranscript.Factory transcriptFactory) {
        super(ACTUATOR_TYPE, HAS_LOCAL_API);
        if(isNull(watsonSpeechToTextUserName)) throw new NullPointerException(watsonSpeechToTextUserName);
        if(isNull(watsonSpeechToTextPassword)) throw new NullPointerException(watsonSpeechToTextPassword);
        this.service = new SpeechToText(watsonSpeechToTextUserName, watsonSpeechToTextPassword);
        Map<String, String> defaultHeaders = new HashMap<>();
        // Set default headers, e.g. defaultHeaders.put(HttpHeaders.CONTENT_TYPE, HttpMediaType.AUDIO_WEBM);
        this.service.setDefaultHeaders(defaultHeaders);
        this.transcriptFactory = transcriptFactory;
    }

    @Override
    public ITranscript speechToText(Path audioFile, Language spokenLanguage) throws ActuatorException {
        try {
            RecognizeOptions options = new RecognizeOptions.Builder()
                    // TODO: Determine audio file type automatically rather than using the hardcoded MIME type "audio/wav"
                    .contentType(HttpMediaType.AUDIO_WAV)
                    .speakerLabels(true)
                    .model(toWatsonSpeechToTextLanguage(spokenLanguage))
                    .timestamps(true)
                    .smartFormatting(true)
                    .audio(audioFile.toFile())
                    .build();
            SpeechRecognitionResults speechRecognitionResults = this.service.recognize(options).execute();
            return this.transcriptFactory.of(speechRecognitionResults);
        }
        catch(Exception e) {
            String errorMessage = format("Could not convert the audio file %s to text with IBM Watson", audioFile);
            log.error(errorMessage);
            throw new ActuatorException(errorMessage, e);
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
                throw new ActuatorException(errorMessage);
        }
    }
}
