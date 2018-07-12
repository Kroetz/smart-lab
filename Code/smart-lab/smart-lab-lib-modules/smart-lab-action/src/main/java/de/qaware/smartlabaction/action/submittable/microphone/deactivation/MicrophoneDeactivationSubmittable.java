package de.qaware.smartlabaction.action.submittable.microphone.deactivation;

import de.qaware.smartlabaction.action.info.microphone.deactivation.MicrophoneDeactivationInfo;
import de.qaware.smartlabaction.action.submittable.generic.AbstractActionSubmittable;
import de.qaware.smartlabapi.service.connector.action.IActionService;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.exception.ActionExecutionFailedException;
import de.qaware.smartlabcore.exception.InvalidActionResultException;
import de.qaware.smartlabcore.filesystem.IFileSystemManager;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

@Component
@Slf4j
public class MicrophoneDeactivationSubmittable extends AbstractActionSubmittable<MicrophoneDeactivationSubmittable.ActionArgs, Path> {

    private final Path recordedAudioTempFileSubDir;
    private final IFileSystemManager fileSystemManager;

    public MicrophoneDeactivationSubmittable(
            MicrophoneDeactivationInfo microphoneDeactivationInfo,
            Path recordedAudioTempFileSubDir,
            IFileSystemManager fileSystemManager) {
        super(microphoneDeactivationInfo);
        this.recordedAudioTempFileSubDir = recordedAudioTempFileSubDir;
        this.fileSystemManager = fileSystemManager;
    }

    public Path submitExecution(IActionService actionService, ActionArgs actionArgs) {
        IActionResult actionResult = actionService.executeAction(this.actionInfo.getActionId(), actionArgs);
        byte[] recordedAudio = actionResult.getByteArrayValue().orElseThrow(InvalidActionResultException::new);
        try {
            return this.fileSystemManager.saveToTempFile(recordedAudioTempFileSubDir, recordedAudio);
        } catch (IOException e) {
            throw new ActionExecutionFailedException(e);
        }
    }

    @Data
    @RequiredArgsConstructor(staticName = "of")     // TODO: Eliminate string literal
    @NoArgsConstructor // TODO: Really necessary for objects being able to serialize/deserialize?
    public static class ActionArgs implements IActionArgs {

        @NonNull
        private RoomId roomId;

        @NonNull
        private DeviceId microphoneId;
    }
}
