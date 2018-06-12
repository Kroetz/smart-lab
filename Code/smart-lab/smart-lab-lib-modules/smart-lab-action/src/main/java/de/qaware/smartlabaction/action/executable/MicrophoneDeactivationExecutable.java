package de.qaware.smartlabaction.action.executable;

import de.qaware.smartlabaction.action.result.ByteArrayActionResult;
import de.qaware.smartlabaction.action.info.MicrophoneDeactivationInfo;
import de.qaware.smartlabaction.action.submittable.MicrophoneDeactivationSubmittable;
import de.qaware.smartlabapi.service.delegate.IDelegateService;
import de.qaware.smartlabapi.service.device.IDeviceManagementService;
import de.qaware.smartlabcore.data.action.generic.IActionArgs;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.data.device.microphone.IMicrophoneAdapter;
import de.qaware.smartlabcore.data.generic.IResolver;
import de.qaware.smartlabcore.exception.ActionExecutionFailedException;
import de.qaware.smartlabcore.exception.UnknownDeviceAdapterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@Slf4j
public class MicrophoneDeactivationExecutable extends AbstractActionExecutable {

    private final IResolver<String, IMicrophoneAdapter> microphoneAdapterResolver;
    private final IDeviceManagementService deviceManagementService;

    public MicrophoneDeactivationExecutable(
            MicrophoneDeactivationInfo microphoneDeactivationInfo,
            IResolver<String, IMicrophoneAdapter> microphoneAdapterResolver,
            IDeviceManagementService deviceManagementService) {
        super(microphoneDeactivationInfo);
        this.microphoneAdapterResolver = microphoneAdapterResolver;
        this.deviceManagementService = deviceManagementService;
    }

    @Override
    public IActionResult execute(String deviceType, IActionArgs genericActionArgs) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        MicrophoneDeactivationSubmittable.ActionArgs actionArgs = convertToSpecificActionArgs(
                MicrophoneDeactivationSubmittable.ActionArgs.class,
                genericActionArgs);
        IMicrophoneAdapter microphoneAdapter = this.microphoneAdapterResolver
                .resolve(deviceType)
                .orElseThrow(UnknownDeviceAdapterException::new);
        if(!microphoneAdapter.hasLocalApi()) throw new IllegalStateException();     // TODO: Better exception
        Path recordedAudio = microphoneAdapter.deactivate();
        IActionResult actionResult;
        try {
            actionResult = ByteArrayActionResult.of(Files.readAllBytes(recordedAudio));
            Files.deleteIfExists(recordedAudio);
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
        IDevice device = this.deviceManagementService.findOne(actionArgs.getDeviceId());
        String deviceType = device.getType();
        IMicrophoneAdapter microphoneAdapter = this.microphoneAdapterResolver
                .resolve(deviceType)
                .orElseThrow(UnknownDeviceAdapterException::new);
        if(microphoneAdapter.hasLocalApi()) return delegateService.executeAction(
                device.getResponsibleDelegate(),
                this.actionInfo.getActionId(),
                deviceType,
                actionArgs);
        Path recordedAudio = microphoneAdapter.deactivate();
        IActionResult actionResult;
        try {
            actionResult = ByteArrayActionResult.of(Files.readAllBytes(recordedAudio));
            Files.deleteIfExists(recordedAudio);
        } catch (IOException e) {
            throw new ActionExecutionFailedException(e);
        }
        return actionResult;
    }
}
