package de.qaware.smartlab.microservice.service.connector.location;

import de.qaware.smartlab.api.service.client.location.ILocationManagementApiClient;
import de.qaware.smartlab.api.service.connector.location.ILocationManagementService;
import de.qaware.smartlab.core.configuration.ModularityConfiguration;
import de.qaware.smartlab.core.data.event.EventDto;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.location.LocationDto;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.exception.SmartLabException;
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
public class LocationManagementMicroserviceConnector extends AbstractBasicEntityManagementMicroserviceConnector<ILocation, LocationId, LocationDto> implements ILocationManagementService {

    private final ILocationManagementApiClient locationManagementApiClient;
    private final IDtoConverter<IEvent, EventDto> eventConverter;

    public LocationManagementMicroserviceConnector(
            ILocationManagementApiClient locationManagementApiClient,
            IDtoConverter<ILocation, LocationDto> locationConverter,
            IDtoConverter<IEvent, EventDto> eventConverter) {
        super(locationManagementApiClient, locationConverter);
        this.locationManagementApiClient = locationManagementApiClient;
        this.eventConverter = eventConverter;
    }

    @Override
    public Set<IEvent> getEventsAtLocation(LocationId locationId) {
        try {
            return requireNonNull(this.locationManagementApiClient.getEventsAtLocation(locationId.getIdValue()).getBody()).stream()
                    .map(this.eventConverter::toEntity)
                    .collect(toSet());
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public IEvent getCurrentEvent(LocationId locationId) {
        try {
            EventDto currentEvent = this.locationManagementApiClient.getCurrentEvent(locationId.getIdValue()).getBody();
            requireNonNull(currentEvent);
            return this.eventConverter.toEntity(currentEvent);
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public void extendCurrentEvent(LocationId locationId, Duration extension) {
        try {
            this.locationManagementApiClient.extendCurrentEvent(locationId.getIdValue(), extension.toMinutes());
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Component
    @Qualifier(IServiceBaseUrlGetter.QUALIFIER_LOCATION_MANAGEMENT_SERVICE_BASE_URL_GETTER)
    @ConditionalOnProperty(
            prefix = ModularityConfiguration.Properties.PREFIX,
            name = ModularityConfiguration.Properties.MODULARITY,
            havingValue = ModularityConfiguration.Properties.MICROSERVICE)
    @Slf4j
    public static class BaseUrlGetter implements IServiceBaseUrlGetter {

        private final ILocationManagementApiClient locationManagementApiClient;

        public BaseUrlGetter(ILocationManagementApiClient locationManagementApiClient) {
            this.locationManagementApiClient = locationManagementApiClient;
        }

        @Override
        public URL getBaseUrl() {
            try {
                return this.locationManagementApiClient.getBaseUrl().getBody();
            }
            // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
            catch (Exception e) {
                throw new SmartLabException(e);
            }
        }
    }
}
