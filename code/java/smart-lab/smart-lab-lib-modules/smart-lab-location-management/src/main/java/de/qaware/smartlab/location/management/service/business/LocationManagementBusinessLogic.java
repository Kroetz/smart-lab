package de.qaware.smartlab.location.management.service.business;

import de.qaware.smartlab.api.service.connector.event.IEventManagementService;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.exception.data.NotFoundException;
import de.qaware.smartlab.core.exception.data.NoCurrentEventException;
import de.qaware.smartlab.core.service.business.AbstractBasicEntityManagementBusinessLogic;
import de.qaware.smartlab.location.management.service.repository.ILocationManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class LocationManagementBusinessLogic extends AbstractBasicEntityManagementBusinessLogic<ILocation, LocationId> implements ILocationManagementBusinessLogic {

    private final ILocationManagementRepository locationManagementRepository;
    private final IEventManagementService eventManagementService;

    public LocationManagementBusinessLogic(
            ILocationManagementRepository locationManagementRepository,
            IEventManagementService eventManagementService) {
        super(locationManagementRepository);
        this.locationManagementRepository = locationManagementRepository;
        this.eventManagementService = eventManagementService;
    }

    @Override
    public Optional<Set<IEvent>> getEventsAtLocation(LocationId locationId) {
        return this.locationManagementRepository.findOne(locationId)
                .map(location -> Optional.of(this.eventManagementService.findAll(location.getId())))
                .orElse(Optional.empty());
    }

    @Override
    public Optional<IEvent> getCurrentEvent(LocationId locationId) {
        return this.locationManagementRepository.findOne(locationId)
                .map(location -> Optional.of(this.eventManagementService.findCurrent(location.getId())))
                .orElse(Optional.empty());
    }

    @Override
    public void extendCurrentEvent(LocationId locationId, Duration extension) {
        // TODO: Simpler with "ifPresentOrElse" in Java 9 (See https://stackoverflow.com/questions/23773024/functional-style-of-java-8s-optional-ifpresent-and-if-not-present)
        Optional<ILocation> locationOptional = this.locationManagementRepository.findOne(locationId);
        if(locationOptional.isPresent()) {
            Optional<IEvent> currentEventOptional = getCurrentEvent(locationOptional.get().getId());
            if(currentEventOptional.isPresent()) {
                this.eventManagementService.extendEvent(currentEventOptional.get().getId(), extension);
                return;
            }
            throw new NoCurrentEventException(locationId.getIdValue());
        }
        throw new NotFoundException(locationId.getIdValue());
    }
}
