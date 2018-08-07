package de.qaware.smartlab.microservice.service.connector.workgroup;

import de.qaware.smartlab.api.service.client.workgroup.IWorkgroupManagementApiClient;
import de.qaware.smartlab.api.service.connector.workgroup.IWorkgroupManagementService;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.event.EventDto;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.data.workgroup.WorkgroupDto;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.exception.EntityConflictException;
import de.qaware.smartlab.core.exception.EntityNotFoundException;
import de.qaware.smartlab.core.exception.MaximalDurationReachedException;
import de.qaware.smartlab.core.exception.UnknownErrorException;
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
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public IEvent getCurrentEvent(WorkgroupId workgroupId) {
        try {
            EventDto currentEvent = this.workgroupManagementApiClient.getCurrentEvent(workgroupId.getIdValue()).getBody();
            requireNonNull(currentEvent);
            return this.eventConverter.toEntity(currentEvent);
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public void extendCurrentEvent(WorkgroupId workgroupId, Duration extension) {
        try {
            this.workgroupManagementApiClient.extendCurrentEvent(workgroupId.getIdValue(), extension.toMinutes());
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

    @Component
    // TODO: String literal
    @Qualifier("workgroupManagementServiceBaseUrlGetter")
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
            // TODO: Exceptions
            try {
                return this.workgroupManagementApiClient.getBaseUrl().getBody();
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
