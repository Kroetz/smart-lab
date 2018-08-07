package de.qaware.smartlab.actuator.management.service.repository;

import de.qaware.smartlab.core.data.device.DeviceId;
import de.qaware.smartlab.core.data.device.IDevice;
import de.qaware.smartlab.core.service.repository.AbstractBasicEntityManagementRepositoryMock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@Slf4j
public class DeviceManagementRepositoryMock extends AbstractBasicEntityManagementRepositoryMock<IDevice, DeviceId> implements IDeviceManagementRepository {

    public DeviceManagementRepositoryMock(Set<IDevice> initialDevices) {
        super(initialDevices);
    }
}
