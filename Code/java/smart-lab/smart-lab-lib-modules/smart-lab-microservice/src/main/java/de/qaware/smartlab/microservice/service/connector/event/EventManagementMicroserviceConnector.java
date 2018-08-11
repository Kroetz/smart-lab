package de.qaware.smartlab.microservice.service.connector.event;

import de.qaware.smartlab.api.service.client.event.IEventManagementApiClient;
import de.qaware.smartlab.api.service.connector.event.IEventManagementService;
import de.qaware.smartlab.core.configuration.ModularityConfiguration;
import de.qaware.smartlab.core.data.event.EventDto;
import de.qaware.smartlab.core.data.event.EventId;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.exception.data.NotFoundException;
import de.qaware.smartlab.core.exception.data.MinimalDurationReachedException;
import de.qaware.smartlab.core.exception.SmartLabException;
import de.qaware.smartlab.core.constant.Property;
import de.qaware.smartlab.core.service.url.IServiceBaseUrlGetter;
import de.qaware.smartlab.microservice.service.connector.generic.AbstractBasicEntityManagementMicroserviceConnector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.Duration;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;

@Component
@ConditionalOnProperty(
        prefix = ModularityConfiguration.Properties.PREFIX,
        name = ModularityConfiguration.Properties.MODULARITY,
        havingValue = ModularityConfiguration.Properties.MICROSERVICE)
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
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public Set<IEvent> findAll(WorkgroupId workgroupId) {
        try {
            return this.eventManagementApiClient.findAllByWorkgroupId(workgroupId.getIdValue()).stream()
                    .map(this.converter::toEntity)
                    .collect(toSet());
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public Set<IEvent> findAllCurrent() {
        try {
            return this.eventManagementApiClient.findAllCurrent().stream()
                    .map(this.converter::toEntity)
                    .collect(toSet());
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public IEvent findCurrent(LocationId locationId) {
        try {
            EventDto currentEvent = this.eventManagementApiClient.findCurrentByLocationId(locationId.getIdValue()).getBody();
            requireNonNull(currentEvent);
            return this.converter.toEntity(currentEvent);
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public IEvent findCurrent(WorkgroupId workgroupId) {
        try {
            EventDto currentEvent = this.eventManagementApiClient.findCurrentByWorkgroupId(workgroupId.getIdValue()).getBody();
            requireNonNull(currentEvent);
            return this.converter.toEntity(currentEvent);
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public void shortenEvent(EventId eventId, Duration shortening)
            throws NotFoundException, MinimalDurationReachedException {
        try {
            this.eventManagementApiClient.shortenEvent(
                    eventId.getIdValue(),
                    shortening.toMinutes());
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public void extendEvent(EventId eventId, Duration extension) {
        try {
            this.eventManagementApiClient.extendEvent(
                    eventId.getIdValue(),
                    extension.toMinutes());
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public void shiftEvent(EventId eventId, Duration shift) {
        try {
            this.eventManagementApiClient.shiftEvent(
                    eventId.getIdValue(),
                    shift.toMinutes());
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Component
    @Qualifier(IServiceBaseUrlGetter.QUALIFIER_EVENT_MANAGEMENT_SERVICE_BASE_URL_GETTER)
    @ConditionalOnProperty(
            prefix = ModularityConfiguration.Properties.PREFIX,
            name = ModularityConfiguration.Properties.MODULARITY,
            havingValue = ModularityConfiguration.Properties.MICROSERVICE)
    @Slf4j
    public static class BaseUrlGetter implements IServiceBaseUrlGetter {

        private final IEventManagementApiClient eventManagementApiClient;

        public BaseUrlGetter(IEventManagementApiClient eventManagementApiClient) {
            this.eventManagementApiClient = eventManagementApiClient;
        }

        @Override
        public URL getBaseUrl() {
            try {
                return this.eventManagementApiClient.getBaseUrl().getBody();
            }
            // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
            catch (Exception e) {
                throw new SmartLabException(e);
            }
        }
    }
}
