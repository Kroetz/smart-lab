package de.qaware.smartlab.action.actions.executable.audio.recordingstop;

import de.qaware.smartlab.action.actions.executable.generic.AbstractActionExecutable;
import de.qaware.smartlab.action.actions.info.audio.recordingstop.AudioRecordingStopInfo;
import de.qaware.smartlab.action.result.ByteArrayActionResult;
import de.qaware.smartlab.action.actions.callable.audio.recordingstop.AudioRecordingStopCallable;
import de.qaware.smartlab.api.service.connector.delegate.IDelegateService;
import de.qaware.smartlab.api.service.connector.actuator.IActuatorManagementService;
import de.qaware.smartlab.core.data.action.generic.IActionArgs;
import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.actuator.IActuator;
import de.qaware.smartlab.actuator.adapter.adapters.microphone.IMicrophoneAdapter;
import de.qaware.smartlab.core.data.generic.IResolver;
import de.qaware.smartlab.core.exception.ActionExecutionFailedException;
import de.qaware.smartlab.core.filesystem.ITempFileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.readAllBytes;

@Component
@Slf4j
public class AudioRecordingStopExecutable extends AbstractActionExecutable {

    private final IResolver<String, IMicrophoneAdapter> microphoneAdapterResolver;
    private final IActuatorManagementService actuatorManagementService;
    private final ITempFileManager tempFileManager;

    public AudioRecordingStopExecutable(
            AudioRecordingStopInfo audioRecordingStopInfo,
            IResolver<String, IMicrophoneAdapter> microphoneAdapterResolver,
            IActuatorManagementService actuatorManagementService,
            ITempFileManager tempFileManager) {
        super(audioRecordingStopInfo);
        this.microphoneAdapterResolver = microphoneAdapterResolver;
        this.actuatorManagementService = actuatorManagementService;
        this.tempFileManager = tempFileManager;
    }

    @Override
    public IActionResult execute(String actuatorType, IActionArgs genericActionArgs) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        AudioRecordingStopCallable.ActionArgs actionArgs = toSpecificArgsType(
                AudioRecordingStopCallable.ActionArgs.class,
                genericActionArgs);
        IMicrophoneAdapter microphoneAdapter = this.microphoneAdapterResolver.resolve(actuatorType);
        if(!microphoneAdapter.hasLocalApi()) throw new IllegalStateException();     // TODO: Better exception
        Path recordedAudio = microphoneAdapter.stopRecording();
        IActionResult actionResult;
        try {
            actionResult = ByteArrayActionResult.of(readAllBytes(recordedAudio));
            this.tempFileManager.markForCleaning(recordedAudio);
        } catch (IOException e) {
            throw new ActionExecutionFailedException(e);
        }
        return actionResult;
    }

    @Override
    public IActionResult execute(IActionArgs genericActionArgs, IDelegateService delegateService) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        AudioRecordingStopCallable.ActionArgs actionArgs = toSpecificArgsType(
                AudioRecordingStopCallable.ActionArgs.class,
                genericActionArgs);
        IActuator actuator = this.actuatorManagementService.findOne(actionArgs.getMicrophoneId());
        String actuatorType = actuator.getType();
        IMicrophoneAdapter microphoneAdapter = this.microphoneAdapterResolver.resolve(actuatorType);
        if(microphoneAdapter.hasLocalApi()) return delegateService.executeAction(
                actuator.getResponsibleDelegate(),
                this.actionInfo.getActionId(),
                actuatorType,
                actionArgs);
        Path recordedAudio = microphoneAdapter.stopRecording();
        IActionResult actionResult;
        try {
            actionResult = ByteArrayActionResult.of(readAllBytes(recordedAudio));
            this.tempFileManager.markForCleaning(recordedAudio);
        } catch (IOException e) {
            throw new ActionExecutionFailedException(e);
        }
        return actionResult;
    }
}
