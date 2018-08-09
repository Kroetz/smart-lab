package de.qaware.smartlab.action.actions.executable.audio.recordingstart;

import de.qaware.smartlab.action.actions.callable.audio.recordingstart.AudioRecordingStartCallable;
import de.qaware.smartlab.action.actions.executable.generic.AbstractActionExecutable;
import de.qaware.smartlab.action.actions.info.audio.recordingstart.AudioRecordingStartInfo;
import de.qaware.smartlab.action.result.VoidActionResult;
import de.qaware.smartlab.actuator.adapter.adapters.microphone.IMicrophoneAdapter;
import de.qaware.smartlab.api.service.connector.actuator.IActuatorManagementService;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.generic.IResolver;
import de.qaware.smartlab.core.exception.ActionExecutionFailedException;
import de.qaware.smartlab.core.filesystem.ITempFileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

@Component
@Slf4j
public class AudioRecordingStartExecutable extends AbstractActionExecutable<AudioRecordingStartCallable.ActionArgs, IMicrophoneAdapter> {

    private final Path recordedAudioTempFileSubDir;
    private final ITempFileManager tempFileManager;

    public AudioRecordingStartExecutable(
            AudioRecordingStartInfo audioRecordingStartInfo,
            IResolver<String, IMicrophoneAdapter> microphoneAdapterResolver,
            IActuatorManagementService actuatorManagementService,
            // TODO: String literals
            @Qualifier("recordedAudioTempFileSubDir") Path recordedAudioTempFileSubDir,
            ITempFileManager tempFileManager) {
        super(
                audioRecordingStartInfo,
                microphoneAdapterResolver,
                actionArgs -> actuatorManagementService.findOne(actionArgs.getMicrophoneId()).getType(),
                actionArgs -> Optional.of(actuatorManagementService.findOne(actionArgs.getMicrophoneId()).getResponsibleDelegate()));
        this.recordedAudioTempFileSubDir = recordedAudioTempFileSubDir;
        this.tempFileManager = tempFileManager;
    }

    @Override
    protected IActionResult execute(IMicrophoneAdapter microphoneAdapter, AudioRecordingStartCallable.ActionArgs actionArgs) {
        Path recordingTargetFile;
        try {
            recordingTargetFile = this.tempFileManager.createEmptyTempFile(this.recordedAudioTempFileSubDir);
        } catch (IOException e) {
            // TODO: Exception message
            throw new ActionExecutionFailedException(e);
        }
        microphoneAdapter.startRecording(recordingTargetFile);
        return VoidActionResult.newInstance();
    }
}
