package de.qaware.smartlabdevice.repository;

import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import de.qaware.smartlabcore.generic.repository.AbstractEntityManagementRepositoryMock;
import de.qaware.smartlabsampledata.provider.ISampleDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Repository
@Slf4j
public class DeviceManagementRepositoryMock extends AbstractEntityManagementRepositoryMock<IDevice, DeviceId> implements IDeviceManagementRepository {

    public DeviceManagementRepositoryMock(ISampleDataProvider sampleDataProvider) {
        this.entities = new HashSet<>(sampleDataProvider.getDevices());
    }
}
