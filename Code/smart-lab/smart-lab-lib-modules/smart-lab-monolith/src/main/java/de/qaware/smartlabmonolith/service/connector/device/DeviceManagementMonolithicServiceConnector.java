package de.qaware.smartlabmonolith.service.connector.device;

import de.qaware.smartlabapi.service.connector.device.IDeviceManagementService;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabdevice.service.controller.DeviceManagementController;
import de.qaware.smartlabcore.service.url.AbstractMonolithicBaseUrlGetter;
import de.qaware.smartlabmonolith.service.connector.generic.AbstractBasicEntityManagementMonolithicServiceConnector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class DeviceManagementMonolithicServiceConnector extends AbstractBasicEntityManagementMonolithicServiceConnector<IDevice, DeviceId> implements IDeviceManagementService {

    private final DeviceManagementController deviceManagementController;

    public DeviceManagementMonolithicServiceConnector(DeviceManagementController deviceManagementController) {
        super(deviceManagementController);
        this.deviceManagementController = deviceManagementController;
    }

    @Component
    // TODO: String literal
    @Qualifier("deviceManagementServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MONOLITH)
    public static class BaseUrlGetter extends AbstractMonolithicBaseUrlGetter {

        public BaseUrlGetter(DeviceManagementController.BaseUrlController baseUrlController) {
            super(baseUrlController);
        }
    }
}
