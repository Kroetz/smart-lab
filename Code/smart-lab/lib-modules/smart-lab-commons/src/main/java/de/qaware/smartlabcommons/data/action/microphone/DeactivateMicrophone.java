package de.qaware.smartlabcommons.data.action.microphone;

import de.qaware.smartlabcommons.api.service.delegate.IDelegateService;
import de.qaware.smartlabcommons.api.service.device.IDeviceManagementService;
import de.qaware.smartlabcommons.data.action.AbstractAction;
import de.qaware.smartlabcommons.data.action.IActionArgs;
import de.qaware.smartlabcommons.data.action.IActionDispatching;
import de.qaware.smartlabcommons.data.action.IActionExecution;
import de.qaware.smartlabcommons.data.action.result.ByteArrayActionResult;
import de.qaware.smartlabcommons.data.action.result.IActionResult;
import de.qaware.smartlabcommons.data.device.entity.IDevice;
import de.qaware.smartlabcommons.data.device.microphone.IMicrophoneAdapter;
import de.qaware.smartlabcommons.data.generic.IResolver;
import de.qaware.smartlabcommons.exception.ActionExecutionFailedException;
import de.qaware.smartlabcommons.exception.InvalidActionResultException;
import de.qaware.smartlabcommons.exception.UnknownDeviceAdapterException;
import de.qaware.smartlabcommons.persistence.IFileSystemManager;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@Slf4j
public class DeactivateMicrophone extends AbstractAction {

    public static final String ACTION_ID = "deactivate microphone";
    private final IResolver<String, IMicrophoneAdapter> microphoneAdapterResolver;
    private final IDeviceManagementService deviceManagementService;
    private final IFileSystemManager fileSystemManager;
    private final Path recordedAudioTempFileSubDir;

    public DeactivateMicrophone(
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

    public IActionExecution<Path> execution(DeactivateMicrophone.ActionArgs actionArgs) {
        return (actionService) -> {
            IActionResult actionResult = actionService.executeAction(DeactivateMicrophone.ACTION_ID, actionArgs);
            byte[] recordedAudio = actionResult.getByteArrayValue().orElseThrow(InvalidActionResultException::new);
            try {
                return this.fileSystemManager.saveToTempFile(recordedAudioTempFileSubDir, recordedAudio);
            } catch (IOException e) {
                throw new ActionExecutionFailedException(e);
            }
        };
    }

    @Override
    public IActionDispatching dispatching(String deviceType, IActionArgs genericActionArgs) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        ActionArgs actionArgs = convertToSpecific(DeactivateMicrophone.ActionArgs.class, genericActionArgs);
        IMicrophoneAdapter microphoneAdapter = this.microphoneAdapterResolver.resolve(deviceType).orElseThrow(UnknownDeviceAdapterException::new);
        if(!microphoneAdapter.hasLocalApi()) throw new IllegalStateException();     // TODO: Better exception
        return () -> {
            Path recordedAudio = microphoneAdapter.deactivate();
            try {
                return ByteArrayActionResult.of(Files.readAllBytes(recordedAudio));
            } catch (IOException e) {
                throw new ActionExecutionFailedException(e);
            }
        };
    }

    @Override
    public IActionDispatching dispatching(IActionArgs genericActionArgs, IDelegateService delegateService) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        ActionArgs actionArgs = convertToSpecific(DeactivateMicrophone.ActionArgs.class, genericActionArgs);
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
            Path recordedAudio = microphoneAdapter.deactivate();
            try {
                return ByteArrayActionResult.of(Files.readAllBytes(recordedAudio));
            } catch (IOException e) {
                throw new ActionExecutionFailedException(e);
            }
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
