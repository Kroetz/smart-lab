package de.qaware.smartlabcommons.api.internal.service.device;

import de.qaware.smartlabcommons.api.internal.client.IDeviceManagementApiClient;
import de.qaware.smartlabcommons.api.internal.service.generic.AbstractEntityManagementService;
import de.qaware.smartlabcommons.data.device.entity.IDevice;
import de.qaware.smartlabcommons.miscellaneous.Property;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
public class DeviceManagementMicroservice extends AbstractEntityManagementService<IDevice> implements IDeviceManagementService {

    private final IDeviceManagementApiClient deviceManagementApiClient;

    public DeviceManagementMicroservice(IDeviceManagementApiClient deviceManagementApiClient) {
        super(deviceManagementApiClient);
        this.deviceManagementApiClient = deviceManagementApiClient;
    }
}
