package de.qaware.smartlabdevice.business;

import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcore.generic.business.AbstractEntityManagementBusinessLogic;
import de.qaware.smartlabdevice.repository.IDeviceManagementRepository;
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
