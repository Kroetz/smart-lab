package de.qaware.smartlablocation.service.repository;

import de.qaware.smartlabcore.data.location.ILocation;
import de.qaware.smartlabcore.data.location.Location;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.service.repository.AbstractBasicEntityManagementRepositoryMock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
@Slf4j
public class LocationManagementRepositoryMock extends AbstractBasicEntityManagementRepositoryMock<ILocation, LocationId> implements ILocationManagementRepository {

    public LocationManagementRepositoryMock(Set<ILocation> initialLocations) {
        super(initialLocations);
        this.entities = new HashSet<>();
    }
}
