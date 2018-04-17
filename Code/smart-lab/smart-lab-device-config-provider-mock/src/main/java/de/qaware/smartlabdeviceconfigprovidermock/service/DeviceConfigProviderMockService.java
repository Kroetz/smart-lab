package de.qaware.smartlabdeviceconfigprovidermock.service;

import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcore.data.sample.ISampleDataFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceConfigProviderMockService implements IDeviceConfigProviderMockService {

    private List<IDevice> devices;

    public DeviceConfigProviderMockService(
            ISampleDataFactory coastGuardDataFactory,
            ISampleDataFactory forestRangersDataFactory,
            ISampleDataFactory fireFightersDataFactory) {
        this.devices = new ArrayList<>();
        this.devices.addAll(coastGuardDataFactory.createDevices());
        this.devices.addAll(forestRangersDataFactory.createDevices());
        this.devices.addAll(fireFightersDataFactory.createDevices());
        sortDevicesById();
    }

    private boolean exists(long deviceId) {
        return devices.stream()
                .anyMatch(device -> device.getId() == deviceId);
    }

    @Override
    public List<IDevice> getDevices() {
        return this.devices;
    }

    @Override
    public Optional<IDevice> getDevice(long deviceId) {
        return devices.stream()
                .filter(device -> device.getId() == deviceId)
                .findFirst();
    }

    @Override
    public boolean createDevice(IDevice device) {
        return !exists(device.getId()) && devices.add(device);
    }

    @Override
    public boolean deleteDevice(long deviceId) {
        return devices.removeAll(devices.stream()
                .filter(device -> device.getId() == deviceId)
                .collect(Collectors.toList()));
    }

    private void sortDevicesById() {
        devices.sort(Comparator.comparingLong(IDevice::getId));
    }
}
