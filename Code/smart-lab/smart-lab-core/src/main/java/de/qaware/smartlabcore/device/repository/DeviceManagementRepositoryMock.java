package de.qaware.smartlabcore.device.repository;

import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcore.data.sample.provider.ISampleDataProvider;
import de.qaware.smartlabcore.generic.result.CreationResult;
import de.qaware.smartlabcore.generic.result.DeletionResult;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class DeviceManagementRepositoryMock implements IDeviceManagementRepository {

    private List<IDevice> devices;

    public DeviceManagementRepositoryMock(ISampleDataProvider sampleDataProvider) {
        this.devices = new ArrayList<>(sampleDataProvider.getDevices());
        sortDevicesById();
    }

    private boolean exists(String deviceId) {
        return devices.stream()
                .anyMatch(device -> device.getId().equals(deviceId));
    }

    @Override
    public List<IDevice> getDevices() {
        return this.devices;
    }

    @Override
    public Optional<IDevice> getDevice(String deviceId) {
        return devices.stream()
                .filter(device -> device.getId().equals(deviceId))
                .findFirst();
    }

    @Override
    public CreationResult createDevice(IDevice device) {
        if (exists(device.getId())) {
            return CreationResult.CONFLICT;
        }
        if(devices.add(device)) {
            return CreationResult.SUCCESS;
        }
        return CreationResult.ERROR;
    }

    @Override
    public DeletionResult deleteDevice(String deviceId) {
        val devicesToDelete = devices.stream()
                .filter(device -> device.getId().equals(deviceId))
                .collect(Collectors.toList());
        if(devicesToDelete.isEmpty()) {
            return DeletionResult.NOT_FOUND;
        }
        val deleted =  devices.removeAll(devicesToDelete);
        if(deleted) {
            return DeletionResult.SUCCESS;
        }
        return DeletionResult.ERROR;
    }

    private void sortDevicesById() {
        devices.sort(Comparator.comparing(IDevice::getId));
    }
}
