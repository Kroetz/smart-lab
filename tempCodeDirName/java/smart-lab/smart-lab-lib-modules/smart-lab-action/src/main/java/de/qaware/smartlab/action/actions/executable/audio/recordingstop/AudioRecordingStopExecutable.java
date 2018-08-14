package de.qaware.smartlab.action.actions.executable.audio.recordingstop;

import de.qaware.smartlab.action.actions.callable.audio.recordingstop.AudioRecordingStopCallable;
import de.qaware.smartlab.action.actions.executable.generic.AbstractActionExecutable;
import de.qaware.smartlab.action.actions.info.audio.recordingstop.AudioRecordingStopInfo;
import de.qaware.smartlab.action.result.ByteArrayActionResult;
import de.qaware.smartlab.actuator.adapter.adapters.microphone.IMicrophoneAdapter;
import de.qaware.smartlab.api.service.connector.actuator.IActuatorManagementService;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.generic.IResolver;
import de.qaware.smartlab.core.exception.action.ActionException;
import de.qaware.smartlab.core.filesystem.ITempFileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import static java.lang.String.format;
import static java.nio.file.Files.readAllBytes;

@Component
@Slf4j
public class AudioRecordingStopExecutable extends AbstractActionExecutable<AudioRecordingStopCallable.ActionArgs, IMicrophoneAdapter> {

    private final ITempFileManager tempFileManager;

    public AudioRecordingStopExecutable(
            AudioRecordingStopInfo audioRecordingStopInfo,
            IResolver<String, IMicrophoneAdapter> microphoneAdapterResolver,
            IActuatorManagementService actuatorManagementService,
            ITempFileManager tempFileManager) {
        super(
                audioRecordingStopInfo,
                microphoneAdapterResolver,
                actionArgs -> actuatorManagementService.findOne(actionArgs.getMicrophoneId()).getType(),
                actionArgs -> Optional.of(actuatorManagementService.findOne(actionArgs.getMicrophoneId()).getResponsibleDelegate()));
        this.tempFileManager = tempFileManager;
    }

    @Override
    protected IActionResult execute(IMicrophoneAdapter microphoneAdapter, AudioRecordingStopCallable.ActionArgs actionArgs) throws ActionException {
        Path recordedAudio = microphoneAdapter.stopRecording();
        IActionResult actionResult;
        try {
            actionResult = ByteArrayActionResult.of(readAllBytes(recordedAudio));
            this.tempFileManager.markForCleaning(recordedAudio);
        } catch (IOException e) {
            String errorMessage = format("Could not read data from audio file %s", recordedAudio);
            log.error(errorMessage);
            throw new ActionException(errorMessage, e);
        }
        return actionResult;
    }
}
