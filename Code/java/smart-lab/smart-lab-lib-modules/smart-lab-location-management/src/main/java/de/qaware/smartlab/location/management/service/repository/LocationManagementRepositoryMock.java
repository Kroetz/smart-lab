package de.qaware.smartlab.location.management.service.repository;

import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.service.repository.AbstractBasicEntityManagementRepositoryMock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@Slf4j
public class LocationManagementRepositoryMock extends AbstractBasicEntityManagementRepositoryMock<ILocation, LocationId> implements ILocationManagementRepository {

    public LocationManagementRepositoryMock(Set<ILocation> initialLocations) {
        super(initialLocations);
    }
}
