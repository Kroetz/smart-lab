package de.qaware.smartlabcommons.data.action.microphone;

import de.qaware.smartlabcommons.api.service.device.IDeviceManagementService;
import de.qaware.smartlabcommons.api.service.room.IRoomManagementService;
import de.qaware.smartlabcommons.data.action.AbstractAction;
import de.qaware.smartlabcommons.data.action.IActionArgs;
import de.qaware.smartlabcommons.data.device.microphone.IMicrophoneAdapter;
import de.qaware.smartlabcommons.data.generic.IResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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

    @Override
    public void executeAction(IActionArgs actionArgs) {
        // TODO: Implementation
    }

    // TODO: ActionArgs definition
}
