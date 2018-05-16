package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabcommons.api.service.device.IDeviceManagementService;
import de.qaware.smartlabcommons.data.device.entity.IDevice;
import de.qaware.smartlabcommons.miscellaneous.Constants;
import de.qaware.smartlabcommons.miscellaneous.ProfileNames;
import de.qaware.smartlabdevice.controller.DeviceManagementController;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(ProfileNames.MONOLITH)
public class DeviceManagementServiceMonolith extends AbstractEntityManagementServiceMonolith<IDevice> implements IDeviceManagementService {

    private final DeviceManagementController deviceManagementController;

    public DeviceManagementServiceMonolith(DeviceManagementController deviceManagementController) {
        super(deviceManagementController);
        this.deviceManagementController = deviceManagementController;
    }
}
