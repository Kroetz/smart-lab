package de.qaware.smartlab.action.actions.submittable.microphone.deactivation;

import de.qaware.smartlab.action.actions.info.microphone.deactivation.MicrophoneDeactivationInfo;
import de.qaware.smartlab.action.actions.submittable.generic.AbstractActionSubmittable;
import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.actuator.ActuatorId;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.exception.ActionExecutionFailedException;
import de.qaware.smartlab.core.exception.InvalidActionResultException;
import de.qaware.smartlab.core.filesystem.ITempFileManager;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

@Component
@Slf4j
public class MicrophoneDeactivationSubmittable extends AbstractActionSubmittable<MicrophoneDeactivationSubmittable.ActionArgs, Path> {

    private final Path recordedAudioTempFileSubDir;
    private final ITempFileManager tempFileManager;

    public MicrophoneDeactivationSubmittable(
            MicrophoneDeactivationInfo microphoneDeactivationInfo,
            // TODO: String literals
            @Qualifier("recordedAudioTempFileSubDir") Path recordedAudioTempFileSubDir,
            ITempFileManager tempFileManager) {
        super(microphoneDeactivationInfo);
        this.recordedAudioTempFileSubDir = recordedAudioTempFileSubDir;
        this.tempFileManager = tempFileManager;
    }

    public Path submitExecution(IActionService actionService, ActionArgs actionArgs) {
        IActionResult actionResult = actionService.executeAction(this.actionInfo.getActionId(), actionArgs);
        byte[] recordedAudio = actionResult.getByteArrayValue().orElseThrow(InvalidActionResultException::new);
        try {
            return this.tempFileManager.saveToTempFile(recordedAudioTempFileSubDir, recordedAudio);
        } catch (IOException e) {
            throw new ActionExecutionFailedException(e);
        }
    }

    @Data
    @RequiredArgsConstructor(staticName = "of")     // TODO: Eliminate string literal
    @NoArgsConstructor // TODO: Really necessary for objects being able to serialize/deserialize?
    public static class ActionArgs implements IActionArgs {

        @NonNull
        private LocationId locationId;

        @NonNull
        private ActuatorId microphoneId;
    }
}
