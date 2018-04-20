package de.qaware.smartlabcore.device.repository;

import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcore.data.sample.ISampleDataFactory;
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

    public DeviceManagementRepositoryMock(
            ISampleDataFactory coastGuardDataFactory,
            ISampleDataFactory forestRangersDataFactory,
            ISampleDataFactory fireFightersDataFactory) {
        this.devices = new ArrayList<>();
        this.devices.addAll(new ArrayList<>(coastGuardDataFactory.createDevices().values()));
        this.devices.addAll(new ArrayList<>(forestRangersDataFactory.createDevices().values()));
        this.devices.addAll(new ArrayList<>(fireFightersDataFactory.createDevices().values()));
        sortDevicesById();
    }

    private boolean exists(String deviceId) {
        return devices.stream()
                .anyMatch(device -> device.getId() == deviceId);
    }

    @Override
    public List<IDevice> getDevices() {
        return this.devices;
    }

    @Override
    public Optional<IDevice> getDevice(String deviceId) {
        return devices.stream()
                .filter(device -> device.getId() == deviceId)
                .findFirst();
    }

    @Override
    public boolean createDevice(IDevice device) {
        return !exists(device.getId()) && devices.add(device);
    }

    @Override
    public boolean deleteDevice(String deviceId) {
        return devices.removeAll(devices.stream()
                .filter(device -> device.getId() == deviceId)
                .collect(Collectors.toList()));
    }

    private void sortDevicesById() {
        devices.sort(Comparator.comparing(IDevice::getId));
    }
}
