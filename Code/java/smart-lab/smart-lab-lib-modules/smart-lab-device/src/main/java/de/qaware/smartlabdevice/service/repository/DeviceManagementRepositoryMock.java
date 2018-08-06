package de.qaware.smartlabdevice.service.repository;

import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.service.repository.AbstractBasicEntityManagementRepositoryMock;
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
