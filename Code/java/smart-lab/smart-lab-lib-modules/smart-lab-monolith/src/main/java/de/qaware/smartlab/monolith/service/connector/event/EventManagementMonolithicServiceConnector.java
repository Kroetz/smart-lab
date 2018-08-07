package de.qaware.smartlab.monolith.service.connector.event;

import de.qaware.smartlab.api.service.connector.event.IEventManagementService;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.event.EventId;
import de.qaware.smartlab.core.data.event.EventDto;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.exception.*;
import de.qaware.smartlab.core.miscellaneous.Property;
import de.qaware.smartlab.core.service.url.AbstractMonolithicBaseUrlGetter;
import de.qaware.smartlab.event.management.service.controller.EventManagementController;
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
public class EventManagementMonolithicServiceConnector extends AbstractBasicEntityManagementMonolithicServiceConnector<IEvent, EventId, EventDto> implements IEventManagementService {

    private final EventManagementController eventManagementController;

    public EventManagementMonolithicServiceConnector(
            EventManagementController eventManagementController,
            IDtoConverter<IEvent, EventDto> eventConverter) {
        super(eventManagementController, eventConverter);
        this.eventManagementController = eventManagementController;
    }

    @Override
    public Set<IEvent> findAll(LocationId locationId) {
        return this.eventManagementController.findAllByLocationId(locationId.getIdValue()).stream()
                .map(this.converter::toEntity)
                .collect(toSet());
    }

    @Override
    public Set<IEvent> findAll(WorkgroupId workgroupId) {
        return this.eventManagementController.findAllByWorkgroupId(workgroupId.getIdValue()).stream()
                .map(this.converter::toEntity)
                .collect(toSet());
    }

    @Override
    public Set<IEvent> findAllCurrent() {
        return this.eventManagementController.findAllCurrent().stream()
                .map(this.converter::toEntity)
                .collect(toSet());
    }

    @Override
    public IEvent findCurrent(LocationId locationId) {
        ResponseEntity<EventDto> response = this.eventManagementController.findCurrentByLocationId(locationId.getIdValue());
        if(response.getStatusCode() == HttpStatus.OK) return this.converter.toEntity(requireNonNull(response.getBody()));
        // TODO: Meaningful exception message
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        throw new UnknownErrorException();
    }

    @Override
    public IEvent findCurrent(WorkgroupId workgroupId) {
        ResponseEntity<EventDto> response = this.eventManagementController.findCurrentByWorkgroupId(workgroupId.getIdValue());
        if(response.getStatusCode() == HttpStatus.OK) return this.converter.toEntity(requireNonNull(response.getBody()));
        // TODO: Meaningful exception message
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        throw new UnknownErrorException();
    }

    @Override
    public void shortenEvent(EventId eventId, Duration shortening) {
        ResponseEntity<Void> response = this.eventManagementController.shortenEvent(
                eventId.getIdValue(),
                shortening.toMinutes());
        if(response.getStatusCode() == HttpStatus.OK) return;
        // TODO: Meaningful exception messages
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        if(response.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) throw new MinimalDurationReachedException();
        throw new UnknownErrorException();
    }

    @Override
    public void extendEvent(EventId eventId, Duration extension) {
        ResponseEntity<Void> response = this.eventManagementController.extendEvent(
                eventId.getIdValue(),
                extension.toMinutes());
        if(response.getStatusCode() == HttpStatus.OK) return;
        // TODO: Meaningful exception messages
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        if(response.getStatusCode() == HttpStatus.CONFLICT) throw new EntityConflictException();
        if(response.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) throw new MaximalDurationReachedException();
        throw new UnknownErrorException();
    }

    @Override
    public void shiftEvent(EventId eventId, Duration shift) {
        ResponseEntity<Void> response = this.eventManagementController.shiftEvent(
                eventId.getIdValue(),
                shift.toMinutes());
        if(response.getStatusCode() == HttpStatus.OK) return;
        // TODO: Meaningful exception messages
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        if(response.getStatusCode() == HttpStatus.CONFLICT) throw new EntityConflictException();
        throw new UnknownErrorException();
    }

    @Component
    // TODO: String literal
    @Qualifier("eventManagementServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MONOLITH)
    public static class BaseUrlGetter extends AbstractMonolithicBaseUrlGetter {

        public BaseUrlGetter(EventManagementController.BaseUrlController baseUrlController) {
            super(baseUrlController);
        }
    }
}
