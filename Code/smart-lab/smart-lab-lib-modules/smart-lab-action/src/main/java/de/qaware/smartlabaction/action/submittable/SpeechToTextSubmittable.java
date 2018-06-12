package de.qaware.smartlabaction.action.submittable;

import de.qaware.smartlabaction.action.info.SpeechToTextInfo;
import de.qaware.smartlabapi.service.action.IActionService;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.data.action.speechtotext.ITranscript;
import de.qaware.smartlabcore.exception.InvalidActionResultException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@Slf4j
public class SpeechToTextSubmittable extends AbstractActionSubmittable<SpeechToTextSubmittable.ActionArgs, ITranscript> {

    public SpeechToTextSubmittable(SpeechToTextInfo speechToTextInfo) {
        super(speechToTextInfo);
    }

    public ITranscript submitExecution(IActionService actionService, ActionArgs actionArgs) {
        IActionResult actionResult = actionService.executeAction(this.actionInfo.getActionId(), actionArgs);
        return actionResult.getTranscriptValue().orElseThrow(InvalidActionResultException::new);
    }

    @Data
    @RequiredArgsConstructor(staticName = "of")     // TODO: Eliminate string literal
    @NoArgsConstructor // TODO: Really necessary for objects being able to serialize/deserialize?
    public static class ActionArgs implements IActionArgs {

        @NonNull
        private Path audioFile;
    }
}
