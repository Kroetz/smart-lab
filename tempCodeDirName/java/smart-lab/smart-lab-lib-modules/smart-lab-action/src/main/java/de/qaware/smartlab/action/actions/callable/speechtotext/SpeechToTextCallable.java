package de.qaware.smartlab.action.actions.callable.speechtotext;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.qaware.smartlab.action.actions.callable.generic.AbstractActionCallable;
import de.qaware.smartlab.action.actions.info.speechtotext.SpeechToTextInfo;
import de.qaware.smartlab.action.result.TranscriptActionResult;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.action.speechtotext.ITranscript;
import de.qaware.smartlab.core.exception.action.ActionException;
import de.qaware.smartlab.core.miscellaneous.Language;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@Slf4j
public class SpeechToTextCallable extends AbstractActionCallable<SpeechToTextCallable.ActionArgs, ITranscript> {

    public SpeechToTextCallable(SpeechToTextInfo speechToTextInfo) {
        super(speechToTextInfo);
    }

    public ITranscript call(IActionService actionService, ActionArgs actionArgs) throws ActionException {
        IActionResult actionResult = actionService.executeAction(this.actionInfo.getActionId(), actionArgs);
        return toSpecificResultType(TranscriptActionResult.class, actionResult).getValue();
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class ActionArgs implements IActionArgs {

        private static final String FIELD_NAME_AUDIO_FILE = "audioFile";
        private static final String FIELD_NAME_SPOKEN_LANGUAGE = "spokenLanguage";

        @NonNull
        private final Path audioFile;

        @NonNull
        private final Language spokenLanguage;

        @JsonCreator
        public static ActionArgs of(
                @JsonProperty(FIELD_NAME_AUDIO_FILE) Path audioFile,
                @JsonProperty(FIELD_NAME_SPOKEN_LANGUAGE) Language spokenLanguage) {
            return new ActionArgs(audioFile, spokenLanguage);
        }
    }
}
