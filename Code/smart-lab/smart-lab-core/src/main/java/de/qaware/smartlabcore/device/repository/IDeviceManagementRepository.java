package de.qaware.smartlabcore.device.repository;

import de.qaware.smartlabcommons.data.device.IDevice;

import java.util.List;
import java.util.Optional;

public interface IDeviceManagementRepository {

    List<IDevice> getDevices();
    Optional<IDevice> getDevice(long deviceId);

    boolean createDevice(IDevice device);

    boolean deleteDevice(long deviceId);
}
