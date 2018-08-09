package de.qaware.smartlab.action.actions.executable.speechtotext;

import de.qaware.smartlab.action.actions.callable.speechtotext.SpeechToTextCallable;
import de.qaware.smartlab.action.actions.executable.generic.AbstractActionExecutable;
import de.qaware.smartlab.action.actions.info.speechtotext.SpeechToTextInfo;
import de.qaware.smartlab.action.result.TranscriptActionResult;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.action.speechtotext.ISpeechToTextAdapter;
import de.qaware.smartlab.core.data.action.speechtotext.ITranscript;
import de.qaware.smartlab.core.data.generic.IResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class SpeechToTextExecutable extends AbstractActionExecutable<SpeechToTextCallable.ActionArgs, ISpeechToTextAdapter> {

    public SpeechToTextExecutable(
            SpeechToTextInfo speechToTextInfo,
            IResolver<String, ISpeechToTextAdapter> speechToTextAdapterResolver,
            // TODO: String literal
            @Qualifier("speechToTextServiceName") String speechToTextServiceName) {
        super(
                speechToTextInfo,
                speechToTextAdapterResolver,
                actionArgs -> speechToTextServiceName,
                actionArgs -> Optional.empty());
    }

    @Override
    protected IActionResult execute(ISpeechToTextAdapter speechToTextAdapter, SpeechToTextCallable.ActionArgs actionArgs) {
        ITranscript transcript = speechToTextAdapter.speechToText(
                actionArgs.getAudioFile(),
                actionArgs.getSpokenLanguage());
        return TranscriptActionResult.of(transcript);
    }
}
