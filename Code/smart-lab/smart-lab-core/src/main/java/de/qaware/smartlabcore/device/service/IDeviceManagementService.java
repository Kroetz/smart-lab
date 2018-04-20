package de.qaware.smartlabcore.device.service;

import de.qaware.smartlabcommons.data.device.IDevice;

import java.util.List;
import java.util.Optional;

public interface IDeviceManagementService {

    List<IDevice> getDevices();
    Optional<IDevice> getDevice(String deviceId);

    boolean createDevice(IDevice device);

    boolean deleteDevice(String deviceId);
}
