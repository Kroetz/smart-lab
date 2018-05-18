package de.qaware.smartlabcommons.data.action.microphone;

import de.qaware.smartlabcommons.api.service.delegate.IDelegateService;
import de.qaware.smartlabcommons.api.service.device.IDeviceManagementService;
import de.qaware.smartlabcommons.api.service.room.IRoomManagementService;
import de.qaware.smartlabcommons.data.action.*;
import de.qaware.smartlabcommons.data.device.microphone.IMicrophoneAdapter;
import de.qaware.smartlabcommons.data.generic.IResolver;
import de.qaware.smartlabcommons.exception.InvalidActionResultException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public class DeactivateMicrophone extends AbstractAction {

    public static final String ACTION_ID = "deactivate microphone";
    private IResolver<String, IMicrophoneAdapter> microphoneAdapterResolver;
    private IRoomManagementService roomManagementService;
    private IDeviceManagementService deviceManagementService;

    public DeactivateMicrophone(
            IResolver<String, IMicrophoneAdapter> microphoneAdapterResolver,
            IRoomManagementService roomManagementService,
            IDeviceManagementService deviceManagementService) {
        super(ACTION_ID);
        this.microphoneAdapterResolver = microphoneAdapterResolver;
        this.roomManagementService = roomManagementService;
        this.deviceManagementService = deviceManagementService;
    }

    public IActionExecution<MultipartFile> execution(DeactivateMicrophone.ActionArgs actionArgs) {
        return (actionService) -> {
            IActionResult actionResult = actionService.executeAction(DeactivateMicrophone.ACTION_ID, actionArgs);
            return actionResult.getMultipartFileValue().orElseThrow(InvalidActionResultException::new);
        };
    }

    @Override
    public IActionDispatching dispatching(String deviceType, IActionArgs genericActionArgs) {
        // TODO: Implementation
        return () -> ActionResult.of(null);
    }

    @Override
    public IActionDispatching dispatching(IActionArgs actionArgs, IDelegateService delegateService) {
        // TODO: Implementation
        return () -> ActionResult.of(null);
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
