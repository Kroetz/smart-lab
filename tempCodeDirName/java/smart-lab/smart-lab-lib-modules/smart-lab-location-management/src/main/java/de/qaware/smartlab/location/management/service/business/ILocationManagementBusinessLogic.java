package de.qaware.smartlab.location.management.service.business;

import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.service.business.IBasicEntityManagementBusinessLogic;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface ILocationManagementBusinessLogic extends IBasicEntityManagementBusinessLogic<ILocation, LocationId> {

    Optional<Set<IEvent>> getEventsAtLocation(LocationId locationId);
    Optional<IEvent> getCurrentEvent(LocationId locationId);
    void extendCurrentEvent(LocationId locationId, Duration extension);
}
