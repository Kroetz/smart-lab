package de.qaware.smartlabactuatormanagement.service.business;

import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.service.business.AbstractBasicEntityManagementBusinessLogic;
import de.qaware.smartlabactuatormanagement.service.repository.IDeviceManagementRepository;
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
