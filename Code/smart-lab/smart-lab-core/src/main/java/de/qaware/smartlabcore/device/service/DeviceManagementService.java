package de.qaware.smartlabcore.device.service;

import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcore.device.repository.IDeviceManagementRepository;
import de.qaware.smartlabcore.generic.result.CreationResult;
import de.qaware.smartlabcore.generic.result.DeletionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class DeviceManagementService implements IDeviceManagementService {

    private final IDeviceManagementRepository deviceManagementRepository;

    public DeviceManagementService(IDeviceManagementRepository deviceManagementRepository) {
        this.deviceManagementRepository = deviceManagementRepository;
    }

    @Override
    public Set<IDevice> getDevices() {
        return deviceManagementRepository.getDevices();
    }

    @Override
    public Optional<IDevice> getDevice(String deviceId) {
        return deviceManagementRepository.getDevice(deviceId);
    }

    @Override
    public CreationResult createDevice(IDevice device) {
        return deviceManagementRepository.createDevice(device);
    }

    @Override
    public DeletionResult deleteDevice(String deviceId) {
        return deviceManagementRepository.deleteDevice(deviceId);
    }
}
