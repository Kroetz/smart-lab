package de.qaware.smartlabaction.action.microphone;

import de.qaware.smartlabcommons.api.internal.service.delegate.IDelegateService;
import de.qaware.smartlabcommons.api.internal.service.device.IDeviceManagementService;
import de.qaware.smartlabaction.action.generic.AbstractAction;
import de.qaware.smartlabcommons.data.action.generic.IActionArgs;
import de.qaware.smartlabcommons.data.action.generic.IActionDispatching;
import de.qaware.smartlabcommons.data.action.generic.IActionExecution;
import de.qaware.smartlabaction.action.generic.result.ByteArrayActionResult;
import de.qaware.smartlabcommons.data.action.generic.result.IActionResult;
import de.qaware.smartlabcommons.data.device.entity.IDevice;
import de.qaware.smartlabcommons.data.device.microphone.IMicrophoneAdapter;
import de.qaware.smartlabcommons.data.generic.IResolver;
import de.qaware.smartlabcommons.exception.ActionExecutionFailedException;
import de.qaware.smartlabcommons.exception.InvalidActionResultException;
import de.qaware.smartlabcommons.exception.UnknownDeviceAdapterException;
import de.qaware.smartlabcommons.filesystem.IFileSystemManager;
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
            IActionResult actionResult;
            try {
                actionResult = ByteArrayActionResult.of(Files.readAllBytes(recordedAudio));
                Files.deleteIfExists(recordedAudio);
            } catch (IOException e) {
                throw new ActionExecutionFailedException(e);
            }
            return actionResult;
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
            IActionResult actionResult;
            try {
                actionResult = ByteArrayActionResult.of(Files.readAllBytes(recordedAudio));
                Files.deleteIfExists(recordedAudio);
            } catch (IOException e) {
                throw new ActionExecutionFailedException(e);
            }
            return actionResult;
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
