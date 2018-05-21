package de.qaware.smartlabcommons.api.internal.service.device;

import de.qaware.smartlabcommons.api.internal.client.IDeviceManagementApiClient;
import de.qaware.smartlabcommons.api.internal.service.generic.AbstractEntityManagementService;
import de.qaware.smartlabcommons.data.device.entity.IDevice;
import de.qaware.smartlabcommons.miscellaneous.ProfileNames;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(ProfileNames.MICROSERVICE)
public class DeviceManagementMicroservice extends AbstractEntityManagementService<IDevice> implements IDeviceManagementService {

    private final IDeviceManagementApiClient deviceManagementApiClient;

    public DeviceManagementMicroservice(IDeviceManagementApiClient deviceManagementApiClient) {
        super(deviceManagementApiClient);
        this.deviceManagementApiClient = deviceManagementApiClient;
    }
}
