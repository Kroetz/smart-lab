package de.qaware.smartlabcommons.api.service.device;

import de.qaware.smartlabcommons.api.client.IDeviceManagementApiClient;
import de.qaware.smartlabcommons.api.service.generic.AbstractEntityManagementService;
import de.qaware.smartlabcommons.data.device.entity.IDevice;
import org.springframework.stereotype.Component;

@Component
public class DeviceManagementService extends AbstractEntityManagementService<IDevice> implements IDeviceManagementService {

    private final IDeviceManagementApiClient deviceManagementApiClient;

    public DeviceManagementService(IDeviceManagementApiClient deviceManagementApiClient) {
        super(deviceManagementApiClient);
        this.deviceManagementApiClient = deviceManagementApiClient;
    }
}
