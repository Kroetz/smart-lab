package de.qaware.smartlab.action.actions.executable.microphone.activation;

import de.qaware.smartlab.action.actions.executable.generic.AbstractActionExecutable;
import de.qaware.smartlab.action.result.VoidActionResult;
import de.qaware.smartlab.action.actions.info.microphone.activation.MicrophoneActivationInfo;
import de.qaware.smartlab.action.actions.submittable.microphone.activation.MicrophoneActivationSubmittable;
import de.qaware.smartlab.api.service.connector.delegate.IDelegateService;
import de.qaware.smartlab.api.service.connector.device.IDeviceManagementService;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlab.actuator.adapter.adapters.microphone.IMicrophoneAdapter;
import de.qaware.smartlabcore.data.generic.IResolver;
import de.qaware.smartlabcore.exception.ActionExecutionFailedException;
import de.qaware.smartlabcore.filesystem.ITempFileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

@Component
@Slf4j
public class MicrophoneActivationExecutable extends AbstractActionExecutable {

    private IResolver<String, IMicrophoneAdapter> microphoneAdapterResolver;
    private IDeviceManagementService deviceManagementService;
    private final Path recordedAudioTempFileSubDir;
    private final ITempFileManager tempFileManager;

    public MicrophoneActivationExecutable(
            MicrophoneActivationInfo microphoneActivationInfo,
            IResolver<String, IMicrophoneAdapter> microphoneAdapterResolver,
            IDeviceManagementService deviceManagementService,
            // TODO: String literals
            @Qualifier("recordedAudioTempFileSubDir") Path recordedAudioTempFileSubDir,
            ITempFileManager tempFileManager) {
        super(microphoneActivationInfo);
        this.microphoneAdapterResolver = microphoneAdapterResolver;
        this.deviceManagementService = deviceManagementService;
        this.recordedAudioTempFileSubDir = recordedAudioTempFileSubDir;
        this.tempFileManager = tempFileManager;
    }

    // TODO: too much code duplicates
    public IActionResult execute(String deviceType, IActionArgs genericActionArgs) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        MicrophoneActivationSubmittable.ActionArgs actionArgs = convertToSpecificActionArgs(
                MicrophoneActivationSubmittable.ActionArgs.class,
                genericActionArgs);
        IMicrophoneAdapter microphoneAdapter = this.microphoneAdapterResolver.resolve(deviceType);
        if(!microphoneAdapter.hasLocalApi()) throw new IllegalStateException();     // TODO: Better exception
        Path recordingTargetFile;
        try {
            recordingTargetFile = this.tempFileManager.createEmptyTempFile(this.recordedAudioTempFileSubDir);
        } catch (IOException e) {
            throw new ActionExecutionFailedException(e);
        }
        microphoneAdapter.activate(recordingTargetFile);
        return VoidActionResult.newInstance();
    }

    public IActionResult execute(IActionArgs genericActionArgs, IDelegateService delegateService) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        MicrophoneActivationSubmittable.ActionArgs actionArgs = convertToSpecificActionArgs(
                MicrophoneActivationSubmittable.ActionArgs.class,
                genericActionArgs);
        IDevice device = this.deviceManagementService.findOne(actionArgs.getMicrophoneId());
        String deviceType = device.getType();
        IMicrophoneAdapter microphoneAdapter = this.microphoneAdapterResolver.resolve(deviceType);
        if(microphoneAdapter.hasLocalApi()) return delegateService.executeAction(
                device.getResponsibleDelegate(),
                this.actionInfo.getActionId(),
                deviceType,
                actionArgs);
        Path recordingTargetFile;
        try {
            recordingTargetFile = this.tempFileManager.createEmptyTempFile(this.recordedAudioTempFileSubDir);
        } catch (IOException e) {
            throw new ActionExecutionFailedException(e);
        }
        microphoneAdapter.activate(recordingTargetFile);
        return VoidActionResult.newInstance();
    }
}
