package de.qaware.smartlabcore.device.business;

import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcore.device.repository.IDeviceManagementRepository;
import de.qaware.smartlabcore.generic.business.AbstractEntityManagementBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeviceManagementBusinessLogic extends AbstractEntityManagementBusinessLogic<IDevice> implements IDeviceManagementBusinessLogic {

    private final IDeviceManagementRepository deviceManagementRepository;

    public DeviceManagementBusinessLogic(IDeviceManagementRepository deviceManagementRepository) {
        super(deviceManagementRepository);
        this.deviceManagementRepository = deviceManagementRepository;
    }
}
