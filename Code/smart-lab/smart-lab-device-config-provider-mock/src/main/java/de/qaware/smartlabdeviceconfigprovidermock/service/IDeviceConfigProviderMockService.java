package de.qaware.smartlabdeviceconfigprovidermock.service;

import de.qaware.smartlabcommons.data.device.IDevice;

import java.util.List;
import java.util.Optional;

public interface IDeviceConfigProviderMockService {

    boolean exists(long deviceId);
    List<IDevice> getDevices();
    Optional<IDevice> getDevice(long deviceId);
    boolean createDevice(IDevice device);
    boolean deleteDevice(long deviceId);
}
