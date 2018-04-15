package de.qaware.smartlabcore.device.service;

import de.qaware.smartlabcommons.data.device.IDevice;

import java.util.List;
import java.util.Optional;

public interface IDeviceService {

    List<IDevice> getDevices();
    Optional<IDevice> getDevice(long deviceId);

    boolean createDevice(IDevice device);

    void deleteDevice(long deviceId);
}
