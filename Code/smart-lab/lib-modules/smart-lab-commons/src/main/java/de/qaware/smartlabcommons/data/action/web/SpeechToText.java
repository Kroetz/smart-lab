package de.qaware.smartlabcommons.data.action.web;

import de.qaware.smartlabcommons.api.internal.service.delegate.IDelegateService;
import de.qaware.smartlabcommons.data.action.AbstractAction;
import de.qaware.smartlabcommons.data.action.IActionArgs;
import de.qaware.smartlabcommons.data.action.IActionDispatching;
import de.qaware.smartlabcommons.data.action.IActionExecution;
import de.qaware.smartlabcommons.data.action.result.IActionResult;
import de.qaware.smartlabcommons.data.action.result.TranscriptActionResult;
import de.qaware.smartlabcommons.data.action.result.VoidActionResult;
import de.qaware.smartlabcommons.exception.InvalidActionResultException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@Slf4j
public class SpeechToText extends AbstractAction {

    public static final String ACTION_ID = "speech to text";
    private final ISpeechToTextService speechToTextService;

    public SpeechToText(ISpeechToTextService speechToTextService) {
        super(ACTION_ID);
        this.speechToTextService = speechToTextService;
    }

    public IActionExecution<ITranscript> execution(ActionArgs actionArgs) {
        return (actionService) -> {
            IActionResult actionResult = actionService.executeAction(SpeechToText.ACTION_ID, actionArgs);
            return actionResult.getTranscriptValue().orElseThrow(InvalidActionResultException::new);
        };
    }

    @Override
    public IActionDispatching dispatching(String deviceType, IActionArgs genericActionArgs) {
        // TODO: Is never needed because there is no local execution for a web service.
        return () -> VoidActionResult.instance();
    }

    @Override
    public IActionDispatching dispatching(IActionArgs genericActionArgs, IDelegateService delegateService) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        ActionArgs actionArgs = convertToSpecific(SpeechToText.ActionArgs.class, genericActionArgs);
        return () -> {
            ITranscript transcript = this.speechToTextService.speechToText(actionArgs.getAudioFile());
            return TranscriptActionResult.of(transcript);
        };
    }

    @Data
    @RequiredArgsConstructor(staticName = "of")     // TODO: Eliminate string literal
    @NoArgsConstructor // TODO: Really necessary for objects being able to serialize/deserialize?
    public static class ActionArgs implements IActionArgs {

        @NonNull
        private Path audioFile;
    }
}
