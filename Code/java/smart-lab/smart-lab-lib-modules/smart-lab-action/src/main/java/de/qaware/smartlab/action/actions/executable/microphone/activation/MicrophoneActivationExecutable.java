package de.qaware.smartlab.action.actions.executable.microphone.activation;

import de.qaware.smartlab.action.actions.executable.generic.AbstractActionExecutable;
import de.qaware.smartlab.action.result.VoidActionResult;
import de.qaware.smartlab.action.actions.info.microphone.activation.MicrophoneActivationInfo;
import de.qaware.smartlab.action.actions.callable.microphone.activation.MicrophoneActivationCallable;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

@Component
@Slf4j
public class MicrophoneActivationExecutable extends AbstractActionExecutable {

    private IResolver<String, IMicrophoneAdapter> microphoneAdapterResolver;
    private IActuatorManagementService actuatorManagementService;
    private final Path recordedAudioTempFileSubDir;
    private final ITempFileManager tempFileManager;

    public MicrophoneActivationExecutable(
            MicrophoneActivationInfo microphoneActivationInfo,
            IResolver<String, IMicrophoneAdapter> microphoneAdapterResolver,
            IActuatorManagementService actuatorManagementService,
            // TODO: String literals
            @Qualifier("recordedAudioTempFileSubDir") Path recordedAudioTempFileSubDir,
            ITempFileManager tempFileManager) {
        super(microphoneActivationInfo);
        this.microphoneAdapterResolver = microphoneAdapterResolver;
        this.actuatorManagementService = actuatorManagementService;
        this.recordedAudioTempFileSubDir = recordedAudioTempFileSubDir;
        this.tempFileManager = tempFileManager;
    }

    // TODO: too much code duplicates
    public IActionResult execute(String actuatorType, IActionArgs genericActionArgs) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        MicrophoneActivationCallable.ActionArgs actionArgs = toSpecificArgsType(
                MicrophoneActivationCallable.ActionArgs.class,
                genericActionArgs);
        IMicrophoneAdapter microphoneAdapter = this.microphoneAdapterResolver.resolve(actuatorType);
        if(!microphoneAdapter.hasLocalApi()) throw new IllegalStateException();     // TODO: Better exception
        Path recordingTargetFile;
        try {
            recordingTargetFile = this.tempFileManager.createEmptyTempFile(this.recordedAudioTempFileSubDir);
        } catch (IOException e) {
            throw new ActionExecutionFailedException(e);
        }
        microphoneAdapter.startRecording(recordingTargetFile);
        return VoidActionResult.newInstance();
    }

    public IActionResult execute(IActionArgs genericActionArgs, IDelegateService delegateService) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        MicrophoneActivationCallable.ActionArgs actionArgs = toSpecificArgsType(
                MicrophoneActivationCallable.ActionArgs.class,
                genericActionArgs);
        IActuator actuator = this.actuatorManagementService.findOne(actionArgs.getMicrophoneId());
        String actuatorType = actuator.getType();
        IMicrophoneAdapter microphoneAdapter = this.microphoneAdapterResolver.resolve(actuatorType);
        if(microphoneAdapter.hasLocalApi()) return delegateService.executeAction(
                actuator.getResponsibleDelegate(),
                this.actionInfo.getActionId(),
                actuatorType,
                actionArgs);
        Path recordingTargetFile;
        try {
            recordingTargetFile = this.tempFileManager.createEmptyTempFile(this.recordedAudioTempFileSubDir);
        } catch (IOException e) {
            throw new ActionExecutionFailedException(e);
        }
        microphoneAdapter.startRecording(recordingTargetFile);
        return VoidActionResult.newInstance();
    }
}
