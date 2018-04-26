package de.qaware.smartlabcore.device.repository;

import de.qaware.smartlabcommons.data.device.IDevice;
import de.qaware.smartlabcore.data.sample.provider.ISampleDataProvider;
import de.qaware.smartlabcore.generic.repository.AbstractEntityManagementRepositoryMock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Repository
@Slf4j
public class DeviceManagementRepositoryMock extends AbstractEntityManagementRepositoryMock<IDevice> implements IDeviceManagementRepository {

    public DeviceManagementRepositoryMock(ISampleDataProvider sampleDataProvider) {
        this.entities = new HashSet<>(sampleDataProvider.getDevices());
    }
}
