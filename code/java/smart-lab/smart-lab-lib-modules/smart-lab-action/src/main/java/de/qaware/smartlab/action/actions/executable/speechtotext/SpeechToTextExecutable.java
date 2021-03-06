package de.qaware.smartlab.action.actions.executable.speechtotext;

import de.qaware.smartlab.action.actions.callable.speechtotext.SpeechToTextCallable;
import de.qaware.smartlab.action.actions.executable.generic.AbstractActionExecutable;
import de.qaware.smartlab.action.actions.info.speechtotext.SpeechToTextInfo;
import de.qaware.smartlab.action.result.TranscriptActionResult;
import de.qaware.smartlab.actuator.adapter.adapters.speechtotext.SpeechToTextConfiguration;
import de.qaware.smartlab.core.action.generic.IActionResult;
import de.qaware.smartlab.core.action.speechtotext.ISpeechToTextAdapter;
import de.qaware.smartlab.core.action.speechtotext.ITranscript;
import de.qaware.smartlab.core.resolver.IResolver;
import de.qaware.smartlab.core.exception.action.ActionException;
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
            @Qualifier(SpeechToTextConfiguration.QUALIFIER_SPEECH_TO_TEXT_SERVICE_NAME) String speechToTextServiceName) {
        super(
                speechToTextInfo,
                speechToTextAdapterResolver,
                actionArgs -> speechToTextServiceName,
                actionArgs -> Optional.empty());
    }

    @Override
    protected IActionResult execute(ISpeechToTextAdapter speechToTextAdapter, SpeechToTextCallable.ActionArgs actionArgs) throws ActionException {
        ITranscript transcript = speechToTextAdapter.speechToText(
                actionArgs.getAudioFile(),
                actionArgs.getSpokenLanguage());
        return TranscriptActionResult.of(transcript);
    }
}
