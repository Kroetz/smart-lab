package de.qaware.smartlab.action.actions.callable.audio.recordingstart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.qaware.smartlab.action.actions.callable.generic.AbstractActionCallable;
import de.qaware.smartlab.action.actions.info.audio.recordingstart.AudioRecordingStartInfo;
import de.qaware.smartlab.action.result.VoidActionResult;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.actuator.ActuatorId;
import de.qaware.smartlab.core.exception.action.ActionException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AudioRecordingStartCallable extends AbstractActionCallable<AudioRecordingStartCallable.ActionArgs, Void> {

    public AudioRecordingStartCallable(AudioRecordingStartInfo audioRecordingStartInfo) {
        super(audioRecordingStartInfo);
    }

    public Void call(IActionService actionService, ActionArgs actionArgs) throws ActionException {
        IActionResult actionResult = actionService.executeAction(this.actionInfo.getActionId(), actionArgs);
        return toSpecificResultType(VoidActionResult.class, actionResult).getValue();
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class ActionArgs implements IActionArgs {

        private static final String FIELD_NAME_MICROPHONE_ID = "microphoneId";

        @NonNull
        private final ActuatorId microphoneId;

        @JsonCreator
        public static ActionArgs of(@JsonProperty(FIELD_NAME_MICROPHONE_ID) ActuatorId microphoneId) {
            return new ActionArgs(microphoneId);
        }
    }
}
