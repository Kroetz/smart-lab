package de.qaware.smartlabcommons.data.action.microphone;

import de.qaware.smartlabcommons.api.service.device.IDeviceManagementService;
import de.qaware.smartlabcommons.api.service.room.IRoomManagementService;
import de.qaware.smartlabcommons.data.action.AbstractAction;
import de.qaware.smartlabcommons.data.action.IActionArgs;
import de.qaware.smartlabcommons.data.device.entity.IDevice;
import de.qaware.smartlabcommons.data.device.microphone.IMicrophoneAdapter;
import de.qaware.smartlabcommons.data.generic.IResolver;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.exception.UnknownDeviceAdapterException;
import de.qaware.smartlabcommons.exception.UnmatchingActionArgsTypeException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ActivateMicrophone extends AbstractAction {

    public static final String ACTION_ID = "activate microphone";
    private IResolver<String, IMicrophoneAdapter> microphoneAdapterResolver;
    private IRoomManagementService roomManagementService;
    private IDeviceManagementService deviceManagementService;

    public ActivateMicrophone(
            IResolver<String, IMicrophoneAdapter> microphoneAdapterResolver,
            IRoomManagementService roomManagementService,
            IDeviceManagementService deviceManagementService) {
        super(ACTION_ID);
        this.microphoneAdapterResolver = microphoneAdapterResolver;
        this.roomManagementService = roomManagementService;
        this.deviceManagementService = deviceManagementService;
    }

    public void executeAction(IActionArgs genericActionArgs) {
        ActionArgs actionArgs;
        try {
            // Every action can only handle its own specific argument type.
            // TODO: since this is common to all actions, move it to abstract base class
            actionArgs = ActionArgs.class.cast(genericActionArgs);
        }
        catch(ClassCastException e) {
            throw new UnmatchingActionArgsTypeException(e);
        }
        IRoom room = this.roomManagementService.findOne(actionArgs.getRoomId());
        IDevice device = this.deviceManagementService.findOne(actionArgs.getDeviceId());
        IMicrophoneAdapter microphoneAdapter = this.microphoneAdapterResolver.resolve(device.getType()).orElseThrow(UnknownDeviceAdapterException::new);
        microphoneAdapter.activate(room, device, actionArgs.isExecuteLocally());
    }

    @Data
    @RequiredArgsConstructor(staticName = "of")     // TODO: Eliminate string literal
    @NoArgsConstructor // TODO: Really necessary for objects being able to serialize/deserialize?
    public static class ActionArgs implements IActionArgs {

        @NonNull
        private String roomId;

        @NonNull
        private String deviceId;

        @NonNull    // Eliminate compiler warning that primitives shall not annotated with @NonNull
        private boolean executeLocally;
    }
}
