package de.qaware.smartlab.action.executable.microphone.deactivation;

import de.qaware.smartlab.action.executable.generic.AbstractActionExecutable;
import de.qaware.smartlab.action.info.microphone.deactivation.MicrophoneDeactivationInfo;
import de.qaware.smartlab.action.result.ByteArrayActionResult;
import de.qaware.smartlab.action.submittable.microphone.deactivation.MicrophoneDeactivationSubmittable;
import de.qaware.smartlabapi.service.connector.delegate.IDelegateService;
import de.qaware.smartlabapi.service.connector.device.IDeviceManagementService;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlab.actuator.adapter.adapters.microphone.IMicrophoneAdapter;
import de.qaware.smartlabcore.data.generic.IResolver;
import de.qaware.smartlabcore.exception.ActionExecutionFailedException;
import de.qaware.smartlabcore.filesystem.ITempFileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.readAllBytes;

@Component
@Slf4j
public class MicrophoneDeactivationExecutable extends AbstractActionExecutable {

    private final IResolver<String, IMicrophoneAdapter> microphoneAdapterResolver;
    private final IDeviceManagementService deviceManagementService;
    private final ITempFileManager tempFileManager;

    public MicrophoneDeactivationExecutable(
            MicrophoneDeactivationInfo microphoneDeactivationInfo,
            IResolver<String, IMicrophoneAdapter> microphoneAdapterResolver,
            IDeviceManagementService deviceManagementService,
            ITempFileManager tempFileManager) {
        super(microphoneDeactivationInfo);
        this.microphoneAdapterResolver = microphoneAdapterResolver;
        this.deviceManagementService = deviceManagementService;
        this.tempFileManager = tempFileManager;
    }

    @Override
    public IActionResult execute(String deviceType, IActionArgs genericActionArgs) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        MicrophoneDeactivationSubmittable.ActionArgs actionArgs = convertToSpecificActionArgs(
                MicrophoneDeactivationSubmittable.ActionArgs.class,
                genericActionArgs);
        IMicrophoneAdapter microphoneAdapter = this.microphoneAdapterResolver.resolve(deviceType);
        if(!microphoneAdapter.hasLocalApi()) throw new IllegalStateException();     // TODO: Better exception
        Path recordedAudio = microphoneAdapter.deactivate();
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
        MicrophoneDeactivationSubmittable.ActionArgs actionArgs = convertToSpecificActionArgs(
                MicrophoneDeactivationSubmittable.ActionArgs.class,
                genericActionArgs);
        IDevice device = this.deviceManagementService.findOne(actionArgs.getMicrophoneId());
        String deviceType = device.getType();
        IMicrophoneAdapter microphoneAdapter = this.microphoneAdapterResolver.resolve(deviceType);
        if(microphoneAdapter.hasLocalApi()) return delegateService.executeAction(
                device.getResponsibleDelegate(),
                this.actionInfo.getActionId(),
                deviceType,
                actionArgs);
        Path recordedAudio = microphoneAdapter.deactivate();
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
