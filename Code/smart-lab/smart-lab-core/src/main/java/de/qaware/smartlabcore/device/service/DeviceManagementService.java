package de.qaware.smartlabcore.device.service;

import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcore.device.repository.IDeviceManagementRepository;
import de.qaware.smartlabcore.generic.service.AbstractEntityManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeviceManagementService extends AbstractEntityManagementService<IDevice> implements IDeviceManagementService {

    private final IDeviceManagementRepository deviceManagementRepository;

    public DeviceManagementService(IDeviceManagementRepository deviceManagementRepository) {
        super(deviceManagementRepository);
        this.deviceManagementRepository = deviceManagementRepository;
    }
}
