package de.qaware.smartlabapi.service.connector.device;

import de.qaware.smartlabapi.service.client.device.IDeviceManagementApiClient;
import de.qaware.smartlabapi.service.connector.generic.AbstractBasicEntityManagementMicroserviceConnector;
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
public class DeviceManagementMicroserviceConnector extends AbstractBasicEntityManagementMicroserviceConnector<IDevice, DeviceId> implements IDeviceManagementService {

    private final IDeviceManagementApiClient deviceManagementApiClient;

    public DeviceManagementMicroserviceConnector(IDeviceManagementApiClient deviceManagementApiClient) {
        super(deviceManagementApiClient);
        this.deviceManagementApiClient = deviceManagementApiClient;
    }
}
