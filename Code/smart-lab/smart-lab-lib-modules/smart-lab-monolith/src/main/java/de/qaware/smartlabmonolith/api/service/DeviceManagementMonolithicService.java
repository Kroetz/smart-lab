package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabapi.service.device.IDeviceManagementService;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabdevice.controller.DeviceManagementController;
import de.qaware.smartlabcore.url.AbstractMonolithicBaseUrlGetter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class DeviceManagementMonolithicService extends AbstractBasicEntityManagementMonolithicService<IDevice, DeviceId> implements IDeviceManagementService {

    private final DeviceManagementController deviceManagementController;

    public DeviceManagementMonolithicService(DeviceManagementController deviceManagementController) {
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
