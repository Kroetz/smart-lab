package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabapi.service.device.IDeviceManagementService;
import de.qaware.smartlabcommons.data.device.entity.IDevice;
import de.qaware.smartlabcommons.miscellaneous.Property;
import de.qaware.smartlabdevice.controller.DeviceManagementController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class DeviceManagementServiceMonolith extends AbstractEntityManagementServiceMonolith<IDevice> implements IDeviceManagementService {

    private final DeviceManagementController deviceManagementController;

    public DeviceManagementServiceMonolith(DeviceManagementController deviceManagementController) {
        super(deviceManagementController);
        this.deviceManagementController = deviceManagementController;
    }
}
