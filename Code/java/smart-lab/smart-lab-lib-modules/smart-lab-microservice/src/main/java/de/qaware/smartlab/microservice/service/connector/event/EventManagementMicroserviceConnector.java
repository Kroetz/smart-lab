package de.qaware.smartlab.microservice.service.connector.event;

import de.qaware.smartlab.api.service.client.event.IEventManagementApiClient;
import de.qaware.smartlab.api.service.connector.event.IEventManagementService;
import de.qaware.smartlab.core.data.event.EventId;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.event.EventDto;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.exception.*;
import de.qaware.smartlab.core.miscellaneous.Property;
import de.qaware.smartlab.core.service.url.IServiceBaseUrlGetter;
import de.qaware.smartlab.microservice.service.connector.generic.AbstractBasicEntityManagementMicroserviceConnector;
import feign.FeignException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.Duration;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
public class EventManagementMicroserviceConnector extends AbstractBasicEntityManagementMicroserviceConnector<IEvent, EventId, EventDto> implements IEventManagementService {

    private final IEventManagementApiClient eventManagementApiClient;

    public EventManagementMicroserviceConnector(
            IEventManagementApiClient eventManagementApiClient,
            IDtoConverter<IEvent, EventDto> eventConverter) {
        super(eventManagementApiClient, eventConverter);
        this.eventManagementApiClient = eventManagementApiClient;
    }

    @Override
    public Set<IEvent> findAll(LocationId locationId) {
        try {
            return this.eventManagementApiClient.findAllByLocationId(locationId.getIdValue()).stream()
                    .map(this.converter::toEntity)
                    .collect(toSet());
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public Set<IEvent> findAll(WorkgroupId workgroupId) {
        try {
            return this.eventManagementApiClient.findAllByWorkgroupId(workgroupId.getIdValue()).stream()
                    .map(this.converter::toEntity)
                    .collect(toSet());
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public Set<IEvent> findAllCurrent() {
        try {
            return this.eventManagementApiClient.findAllCurrent().stream()
                    .map(this.converter::toEntity)
                    .collect(toSet());
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IEvent findCurrent(LocationId locationId) {
        try {
            EventDto currentEvent = this.eventManagementApiClient.findCurrentByLocationId(locationId.getIdValue()).getBody();
            requireNonNull(currentEvent);
            return this.converter.toEntity(currentEvent);
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public IEvent findCurrent(WorkgroupId workgroupId) {
        try {
            EventDto currentEvent = this.eventManagementApiClient.findCurrentByWorkgroupId(workgroupId.getIdValue()).getBody();
            requireNonNull(currentEvent);
            return this.converter.toEntity(currentEvent);
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public void shortenEvent(EventId eventId, Duration shortening)
            throws EntityNotFoundException, MinimalDurationReachedException, UnknownErrorException {
        try {
            this.eventManagementApiClient.shortenEvent(
                    eventId.getIdValue(),
                    shortening.toMinutes());
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            if(e.status() == HttpStatus.UNPROCESSABLE_ENTITY.value()) {
                throw new MinimalDurationReachedException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public void extendEvent(EventId eventId, Duration extension) {
        try {
            this.eventManagementApiClient.extendEvent(
                    eventId.getIdValue(),
                    extension.toMinutes());
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            if(e.status() == HttpStatus.CONFLICT.value()) {
                // TODO: Incorporate information about the conflict
                throw new EntityConflictException();
            }
            if(e.status() == HttpStatus.UNPROCESSABLE_ENTITY.value()) {
                throw new MaximalDurationReachedException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public void shiftEvent(EventId eventId, Duration shift) {
        try {
            this.eventManagementApiClient.shiftEvent(
                    eventId.getIdValue(),
                    shift.toMinutes());
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            if(e.status() == HttpStatus.CONFLICT.value()) {
                // TODO: Incorporate information about the conflict
                throw new EntityConflictException();
            }
            throw new UnknownErrorException();
        }
    }

    @Component
    // TODO: String literal
    @Qualifier("eventManagementServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MICROSERVICE)
    @Slf4j
    public static class BaseUrlGetter implements IServiceBaseUrlGetter {

        private final IEventManagementApiClient eventManagementApiClient;

        public BaseUrlGetter(IEventManagementApiClient eventManagementApiClient) {
            this.eventManagementApiClient = eventManagementApiClient;
        }

        @Override
        public URL getBaseUrl() {
            // TODO: Exceptions
            try {
                return this.eventManagementApiClient.getBaseUrl().getBody();
            }
            catch(RetryableException e) {
                throw e;
            }
            catch(FeignException e) {
                throw new UnknownErrorException();
            }
        }
    }
}
