package de.qaware.smartlabcore.device.repository;

import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcore.data.sample.provider.ISampleDataProvider;
import lombok.extern.slf4j.Slf4j;
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
    public boolean createDevice(IDevice device) {
        return !exists(device.getId()) && devices.add(device);
    }

    @Override
    public boolean deleteDevice(String deviceId) {
        return devices.removeAll(devices.stream()
                .filter(device -> device.getId().equals(deviceId))
                .collect(Collectors.toList()));
    }

    private void sortDevicesById() {
        devices.sort(Comparator.comparing(IDevice::getId));
    }
}
