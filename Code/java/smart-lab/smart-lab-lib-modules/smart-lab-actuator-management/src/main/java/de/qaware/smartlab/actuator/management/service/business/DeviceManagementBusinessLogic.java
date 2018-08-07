package de.qaware.smartlab.actuator.management.service.business;

import de.qaware.smartlab.core.data.device.DeviceId;
import de.qaware.smartlab.core.data.device.IDevice;
import de.qaware.smartlab.core.service.business.AbstractBasicEntityManagementBusinessLogic;
import de.qaware.smartlab.actuator.management.service.repository.IDeviceManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeviceManagementBusinessLogic extends AbstractBasicEntityManagementBusinessLogic<IDevice, DeviceId> implements IDeviceManagementBusinessLogic {

    private final IDeviceManagementRepository deviceManagementRepository;

    public DeviceManagementBusinessLogic(IDeviceManagementRepository deviceManagementRepository) {
        super(deviceManagementRepository);
        this.deviceManagementRepository = deviceManagementRepository;
    }
}
