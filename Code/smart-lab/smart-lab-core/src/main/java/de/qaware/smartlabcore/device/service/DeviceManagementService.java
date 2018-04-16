package de.qaware.smartlabcore.device.service;

import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcore.device.repository.IDeviceManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DeviceManagementService implements IDeviceManagementService {

    private final IDeviceManagementRepository deviceManagementRepository;

    public DeviceManagementService(IDeviceManagementRepository deviceManagementRepository) {
        this.deviceManagementRepository = deviceManagementRepository;
    }

    @Override
    public List<IDevice> getDevices() {
        return deviceManagementRepository.getDevices();
    }

    @Override
    public Optional<IDevice> getDevice(long deviceId) {
        return deviceManagementRepository.getDevice(deviceId);
    }

    @Override
    public boolean createDevice(IDevice device) {
        return deviceManagementRepository.createDevice(device);
    }

    @Override
    public void deleteDevice(long deviceId) {
        deviceManagementRepository.deleteDevice(deviceId);
    }
}
