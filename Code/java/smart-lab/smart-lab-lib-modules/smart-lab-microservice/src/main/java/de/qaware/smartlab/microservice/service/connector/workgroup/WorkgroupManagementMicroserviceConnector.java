package de.qaware.smartlab.microservice.service.connector.workgroup;

import de.qaware.smartlab.api.service.client.workgroup.IWorkgroupManagementApiClient;
import de.qaware.smartlab.api.service.connector.workgroup.IWorkgroupManagementService;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.event.EventDto;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.data.workgroup.WorkgroupDto;
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
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
public class WorkgroupManagementMicroserviceConnector extends AbstractBasicEntityManagementMicroserviceConnector<IWorkgroup, WorkgroupId, WorkgroupDto> implements IWorkgroupManagementService {

    private final IWorkgroupManagementApiClient workgroupManagementApiClient;
    private final IDtoConverter<IEvent, EventDto> eventConverter;

    public WorkgroupManagementMicroserviceConnector(
            IWorkgroupManagementApiClient workgroupManagementApiClient,
            IDtoConverter<IWorkgroup, WorkgroupDto> workgroupConverter,
            IDtoConverter<IEvent, EventDto> eventConverter) {
        super(workgroupManagementApiClient, workgroupConverter);
        this.workgroupManagementApiClient = workgroupManagementApiClient;
        this.eventConverter = eventConverter;
    }

    @Override
    public Set<IEvent> getEventsOfWorkgroup(WorkgroupId workgroupId) {
        try {
            return requireNonNull(this.workgroupManagementApiClient.getEventsOfWorkgroup(workgroupId.getIdValue()).getBody()).stream()
                    .map(this.eventConverter::toEntity)
                    .collect(toSet());
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public IEvent getCurrentEvent(WorkgroupId workgroupId) {
        try {
            EventDto currentEvent = this.workgroupManagementApiClient.getCurrentEvent(workgroupId.getIdValue()).getBody();
            requireNonNull(currentEvent);
            return this.eventConverter.toEntity(currentEvent);
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public void extendCurrentEvent(WorkgroupId workgroupId, Duration extension) {
        try {
            this.workgroupManagementApiClient.extendCurrentEvent(workgroupId.getIdValue(), extension.toMinutes());
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Component
    @Qualifier(IServiceBaseUrlGetter.QUALIFIER_WORKGROUP_MANAGEMENT_SERVICE_BASE_URL_GETTER)
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MICROSERVICE)
    @Slf4j
    public static class BaseUrlGetter implements IServiceBaseUrlGetter {

        private final IWorkgroupManagementApiClient workgroupManagementApiClient;

        public BaseUrlGetter(IWorkgroupManagementApiClient workgroupManagementApiClient) {
            this.workgroupManagementApiClient = workgroupManagementApiClient;
        }

        @Override
        public URL getBaseUrl() {
            try {
                return this.workgroupManagementApiClient.getBaseUrl().getBody();
            }
            // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
            catch (Exception e) {
                throw new SmartLabException(e);
            }
        }
    }
}
