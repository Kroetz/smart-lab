package de.qaware.smartlabdeviceconfigprovidermock.service;

import de.qaware.smartlabcommons.data.device.AcmeDisplay;
import de.qaware.smartlabcommons.data.device.AcmeMicrophone;
import de.qaware.smartlabcommons.data.device.IDevice;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceConfigProviderMockService implements IDeviceConfigProviderMockService {

    private List<IDevice> devices;

    public DeviceConfigProviderMockService() {
        this.devices = new ArrayList<>();

        this.devices.add(AcmeDisplay.builder()
                .id(0)
                .name("Display in Room Blue")
                .dummyDisplayProperty("Dummy property of Display in Room Blue")
                .build());
        this.devices.add(AcmeMicrophone.builder()
                .id(1)
                .name("Microphone in Room Green")
                .dummyMicrophoneProperty("Dummy property of Microphone in Room Green")
                .build());
        this.devices.add(AcmeDisplay.builder()
                .id(2)
                .name("Display in Room Red")
                .dummyDisplayProperty("Dummy property of Display in Room Red")
                .build());
        this.devices.add(AcmeMicrophone.builder()
                .id(3)
                .name("Microphone in Room Red")
                .dummyMicrophoneProperty("Dummy property of Microphone in Room Red")
                .build());

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
