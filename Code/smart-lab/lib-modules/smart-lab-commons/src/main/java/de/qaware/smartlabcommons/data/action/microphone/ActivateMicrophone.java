package de.qaware.smartlabcommons.data.action.microphone;

import de.qaware.smartlabcommons.api.service.delegate.IDelegateService;
import de.qaware.smartlabcommons.api.service.device.IDeviceManagementService;
import de.qaware.smartlabcommons.data.action.*;
import de.qaware.smartlabcommons.data.action.result.IActionResult;
import de.qaware.smartlabcommons.data.action.result.VoidActionResult;
import de.qaware.smartlabcommons.data.device.entity.IDevice;
import de.qaware.smartlabcommons.data.device.microphone.IMicrophoneAdapter;
import de.qaware.smartlabcommons.data.generic.IResolver;
import de.qaware.smartlabcommons.exception.ActionExecutionFailedException;
import de.qaware.smartlabcommons.exception.UnknownDeviceAdapterException;
import de.qaware.smartlabcommons.persistence.IFileSystemManager;
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
public class ActivateMicrophone extends AbstractAction {

    public static final String ACTION_ID = "activate microphone";
    private IResolver<String, IMicrophoneAdapter> microphoneAdapterResolver;
    private IDeviceManagementService deviceManagementService;
    private final IFileSystemManager fileSystemManager;
    private final Path recordedAudioTempFileSubDir;

    public ActivateMicrophone(
            IResolver<String, IMicrophoneAdapter> microphoneAdapterResolver,
            IDeviceManagementService deviceManagementService,
            IFileSystemManager fileSystemManager,
            Path recordedAudioTempFileSubDir) {
        super(ACTION_ID);
        this.microphoneAdapterResolver = microphoneAdapterResolver;
        this.deviceManagementService = deviceManagementService;
        this.fileSystemManager = fileSystemManager;
        this.recordedAudioTempFileSubDir = recordedAudioTempFileSubDir;
    }

    public IActionExecution<Void> execution(ActionArgs actionArgs) {
        return (actionService) -> {
            IActionResult actionResult = actionService.executeAction(ActivateMicrophone.ACTION_ID, actionArgs);
            return actionResult.getVoidValue();
        };
    }

    // TODO: too much code duplicates
    public IActionDispatching dispatching(String deviceType, IActionArgs genericActionArgs) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        ActionArgs actionArgs = convertToSpecific(ActivateMicrophone.ActionArgs.class, genericActionArgs);
        IMicrophoneAdapter microphoneAdapter = this.microphoneAdapterResolver.resolve(deviceType).orElseThrow(UnknownDeviceAdapterException::new);
        if(!microphoneAdapter.hasLocalApi()) throw new IllegalStateException();     // TODO: Better exception
        return () -> {
            Path recordingTargetFile;
            try {
                recordingTargetFile = this.fileSystemManager.createEmptyTempFile(this.recordedAudioTempFileSubDir);
            } catch (IOException e) {
                throw new ActionExecutionFailedException(e);
            }
            microphoneAdapter.activate(recordingTargetFile);
            return VoidActionResult.instance();
        };
    }

    public IActionDispatching dispatching(IActionArgs genericActionArgs, IDelegateService delegateService) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        ActionArgs actionArgs = convertToSpecific(ActivateMicrophone.ActionArgs.class, genericActionArgs);
        IDevice device = this.deviceManagementService.findOne(actionArgs.getDeviceId());
        String deviceType = device.getType();
        IMicrophoneAdapter microphoneAdapter = this.microphoneAdapterResolver.resolve(deviceType).orElseThrow(UnknownDeviceAdapterException::new);
        if(microphoneAdapter.hasLocalApi()) return forwardingToDelegate(
                delegateService,
                device.getResponsibleDelegate(),
                ACTION_ID,
                deviceType,
                actionArgs);
        return () -> {
            Path recordingTargetFile;
            try {
                recordingTargetFile = this.fileSystemManager.createEmptyTempFile(this.recordedAudioTempFileSubDir);
            } catch (IOException e) {
                throw new ActionExecutionFailedException(e);
            }
            microphoneAdapter.activate(recordingTargetFile);
            return VoidActionResult.instance();
        };
    }

    @Data
    @RequiredArgsConstructor(staticName = "of")     // TODO: Eliminate string literal
    @NoArgsConstructor // TODO: Really necessary for objects being able to serialize/deserialize?
    public static class ActionArgs implements IActionArgs {

        @NonNull
        private String roomId;

        @NonNull
        private String deviceId;
    }
}
