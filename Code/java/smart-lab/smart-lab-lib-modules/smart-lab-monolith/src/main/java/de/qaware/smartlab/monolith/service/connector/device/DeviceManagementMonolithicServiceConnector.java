package de.qaware.smartlab.monolith.service.connector.device;

import de.qaware.smartlab.api.service.connector.device.IDeviceManagementService;
import de.qaware.smartlab.core.data.device.DeviceDto;
import de.qaware.smartlab.core.data.device.DeviceId;
import de.qaware.smartlab.core.data.device.IDevice;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.miscellaneous.Property;
import de.qaware.smartlab.core.service.url.AbstractMonolithicBaseUrlGetter;
import de.qaware.smartlab.actuator.management.service.controller.DeviceManagementController;
import de.qaware.smartlab.monolith.service.connector.generic.AbstractBasicEntityManagementMonolithicServiceConnector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class DeviceManagementMonolithicServiceConnector extends AbstractBasicEntityManagementMonolithicServiceConnector<IDevice, DeviceId, DeviceDto> implements IDeviceManagementService {

    private final DeviceManagementController deviceManagementController;

    public DeviceManagementMonolithicServiceConnector(
            DeviceManagementController deviceManagementController,
            IDtoConverter<IDevice, DeviceDto> deviceConverter) {
        super(deviceManagementController, deviceConverter);
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