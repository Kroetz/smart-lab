package de.qaware.smartlab.monolith.service.connector.location;

import de.qaware.smartlab.api.service.connector.location.ILocationManagementService;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.location.LocationDto;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.event.EventDto;
import de.qaware.smartlab.core.exception.EntityConflictException;
import de.qaware.smartlab.core.exception.EntityNotFoundException;
import de.qaware.smartlab.core.exception.MaximalDurationReachedException;
import de.qaware.smartlab.core.exception.UnknownErrorException;
import de.qaware.smartlab.core.miscellaneous.Property;
import de.qaware.smartlab.core.service.url.AbstractMonolithicBaseUrlGetter;
import de.qaware.smartlab.location.management.service.controller.LocationManagementController;
import de.qaware.smartlab.monolith.service.connector.generic.AbstractBasicEntityManagementMonolithicServiceConnector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class LocationManagementMonolithicServiceConnector extends AbstractBasicEntityManagementMonolithicServiceConnector<ILocation, LocationId, LocationDto> implements ILocationManagementService {

    private final LocationManagementController locationManagementController;
    private final IDtoConverter<IEvent, EventDto> eventConverter;

    public LocationManagementMonolithicServiceConnector(
            LocationManagementController locationManagementController,
            IDtoConverter<ILocation, LocationDto> locationConverter,
            IDtoConverter<IEvent, EventDto> eventConverter) {
        super(locationManagementController, locationConverter);
        this.locationManagementController = locationManagementController;
        this.eventConverter = eventConverter;
    }

    @Override
    public Set<IEvent> getEventsAtLocation(LocationId locationId) {
        ResponseEntity<Set<EventDto>> response = this.locationManagementController.getEventsAtLocation(locationId.getIdValue());
        if(response.getStatusCode() == HttpStatus.OK) return requireNonNull(response.getBody()).stream().map(this.eventConverter::toEntity).collect(toSet());
        // TODO: Meaningful exception message
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        throw new UnknownErrorException();
    }

    @Override
    public IEvent getCurrentEvent(LocationId locationId) {
        ResponseEntity<EventDto> response = this.locationManagementController.getCurrentEvent(locationId.getIdValue());
        if(response.getStatusCode() == HttpStatus.OK) return this.eventConverter.toEntity(requireNonNull(response.getBody()));
        // TODO: Meaningful exception message
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        throw new UnknownErrorException();
    }

    @Override
    public void extendCurrentEvent(LocationId locationId, Duration extension) {
        ResponseEntity<Void> response = this.locationManagementController.extendCurrentEvent(locationId.getIdValue(), extension.toMinutes());
        if(response.getStatusCode() == HttpStatus.OK) return;
        // TODO: Meaningful exception messages
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        if(response.getStatusCode() == HttpStatus.CONFLICT) throw new EntityConflictException();
        if(response.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) throw new MaximalDurationReachedException();
        throw new UnknownErrorException();
    }

    @Component
    // TODO: String literal
    @Qualifier("locationManagementServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MONOLITH)
    public static class BaseUrlGetter extends AbstractMonolithicBaseUrlGetter {

        public BaseUrlGetter(LocationManagementController.BaseUrlController baseUrlController) {
            super(baseUrlController);
        }
    }
}
