package de.qaware.smartlabcommons.api.service.device;

import de.qaware.smartlabcommons.api.client.IDeviceManagementApiClient;
import de.qaware.smartlabcommons.api.service.generic.AbstractEntityManagementService;
import de.qaware.smartlabcommons.data.device.entity.IDevice;
import de.qaware.smartlabcommons.miscellaneous.Constants;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(Constants.PROFILE_NAME_MICROSERVICE)
public class DeviceManagementMicroservice extends AbstractEntityManagementService<IDevice> implements IDeviceManagementService {

    private final IDeviceManagementApiClient deviceManagementApiClient;

    public DeviceManagementMicroservice(IDeviceManagementApiClient deviceManagementApiClient) {
        super(deviceManagementApiClient);
        this.deviceManagementApiClient = deviceManagementApiClient;
    }
}
