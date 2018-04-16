package de.qaware.smartlabcore.device.repository;

import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcommons.api.configprovidermock.client.IDeviceConfigProviderMockClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class DeviceManagementRepositoryMock implements IDeviceManagementRepository {

    private final IDeviceConfigProviderMockClient deviceConfigProviderMockClient;

    public DeviceManagementRepositoryMock(IDeviceConfigProviderMockClient deviceConfigProviderMockClient) {
        this.deviceConfigProviderMockClient = deviceConfigProviderMockClient;
    }

    @Override
    public List<IDevice> getDevices() {
        return deviceConfigProviderMockClient.getDevices();
    }

    @Override
    public Optional<IDevice> getDevice(long deviceId) {
        return deviceConfigProviderMockClient.getDevice(deviceId);
    }

    @Override
    public boolean createDevice(IDevice device) {
        return deviceConfigProviderMockClient.createDevice(device);
    }

    @Override
    public void deleteDevice(long deviceId) {
        deviceConfigProviderMockClient.deleteDevice(deviceId);
    }
}
