package de.qaware.smartlab.location.management.service.business;

import de.qaware.smartlab.api.service.connector.event.IEventManagementService;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.service.business.AbstractBasicEntityManagementBusinessLogic;
import de.qaware.smartlab.core.result.ExtensionResult;
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
    public ExtensionResult extendCurrentEvent(LocationId locationId, Duration extension) {
        return this.locationManagementRepository.findOne(locationId)
                .map(location -> {
                    try {
                        return getCurrentEvent(location.getId())
                                .map(event -> {
                                    this.eventManagementService.extendEvent(event.getId(), extension);
                                    return ExtensionResult.SUCCESS;})
                                .orElse(ExtensionResult.NOT_FOUND);
                    }
                    catch(Exception e) {
                        return ExtensionResult.fromException(e);
                    }
                })
                .orElse(ExtensionResult.NOT_FOUND);
    }
}
