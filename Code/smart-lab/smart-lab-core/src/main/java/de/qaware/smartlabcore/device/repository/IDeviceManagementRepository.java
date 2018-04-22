package de.qaware.smartlabcore.device.repository;

import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcore.generic.result.CreationResult;
import de.qaware.smartlabcore.generic.result.DeletionResult;

import java.util.List;
import java.util.Optional;

public interface IDeviceManagementRepository {

    List<IDevice> getDevices();
    Optional<IDevice> getDevice(String deviceId);

    CreationResult createDevice(IDevice device);

    DeletionResult deleteDevice(String deviceId);
}
