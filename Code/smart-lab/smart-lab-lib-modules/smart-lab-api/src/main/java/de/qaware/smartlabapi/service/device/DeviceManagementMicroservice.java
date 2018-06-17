package de.qaware.smartlabapi.service.device;

import de.qaware.smartlabapi.client.IDeviceManagementApiClient;
import de.qaware.smartlabapi.service.generic.AbstractBasicEntityManagementMicroservice;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.miscellaneous.Property;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
public class DeviceManagementMicroservice extends AbstractBasicEntityManagementMicroservice<IDevice, DeviceId> implements IDeviceManagementService {

    private final IDeviceManagementApiClient deviceManagementApiClient;

    public DeviceManagementMicroservice(IDeviceManagementApiClient deviceManagementApiClient) {
        super(deviceManagementApiClient);
        this.deviceManagementApiClient = deviceManagementApiClient;
    }
}
