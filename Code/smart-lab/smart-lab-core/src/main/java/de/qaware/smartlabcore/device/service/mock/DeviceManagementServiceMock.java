package de.qaware.smartlabcore.device.service.mock;

import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcore.device.service.IDeviceManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("mock")
@Slf4j
public class DeviceManagementServiceMock implements IDeviceManagementService {

    private final IDeviceConfigProviderMock deviceConfigProvider;

    @Autowired
    public DeviceManagementServiceMock(@Qualifier("mock") IDeviceConfigProviderMock deviceConfigProvider) {
        this.deviceConfigProvider = deviceConfigProvider;
    }

    @Override
    public List<IDevice> getDevices() {
        return deviceConfigProvider.getDevices();
    }

    @Override
    public Optional<IDevice> getDevice(long deviceId) {
        return deviceConfigProvider.getDevice(deviceId);
    }

    @Override
    public boolean createDevice(IDevice device) {
        return deviceConfigProvider.createDevice(device);
    }

    @Override
    public void deleteDevice(long deviceId) {
        deviceConfigProvider.deleteDevice(deviceId);
    }
}
