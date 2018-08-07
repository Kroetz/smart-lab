package de.qaware.smartlabmicroservice.service.connector.location;

import de.qaware.smartlab.api.service.client.location.ILocationManagementApiClient;
import de.qaware.smartlab.api.service.connector.location.ILocationManagementService;
import de.qaware.smartlab.core.data.generic.IDtoConverter;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.location.LocationDto;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.meeting.IMeeting;
import de.qaware.smartlab.core.data.meeting.MeetingDto;
import de.qaware.smartlab.core.exception.EntityConflictException;
import de.qaware.smartlab.core.exception.EntityNotFoundException;
import de.qaware.smartlab.core.exception.MaximalDurationReachedException;
import de.qaware.smartlab.core.exception.UnknownErrorException;
import de.qaware.smartlab.core.miscellaneous.Property;
import de.qaware.smartlab.core.service.url.IServiceBaseUrlGetter;
import de.qaware.smartlabmicroservice.service.connector.generic.AbstractBasicEntityManagementMicroserviceConnector;
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
public class LocationManagementMicroserviceConnector extends AbstractBasicEntityManagementMicroserviceConnector<ILocation, LocationId, LocationDto> implements ILocationManagementService {

    private final ILocationManagementApiClient locationManagementApiClient;
    private final IDtoConverter<IMeeting, MeetingDto> meetingConverter;

    public LocationManagementMicroserviceConnector(
            ILocationManagementApiClient locationManagementApiClient,
            IDtoConverter<ILocation, LocationDto> locationConverter,
            IDtoConverter<IMeeting, MeetingDto> meetingConverter) {
        super(locationManagementApiClient, locationConverter);
        this.locationManagementApiClient = locationManagementApiClient;
        this.meetingConverter = meetingConverter;
    }

    @Override
    public Set<IMeeting> getMeetingsAtLocation(LocationId locationId) {
        try {
            return requireNonNull(this.locationManagementApiClient.getMeetingsAtLocation(locationId.getIdValue()).getBody()).stream()
                    .map(this.meetingConverter::toEntity)
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
    public IMeeting getCurrentMeeting(LocationId locationId) {
        try {
            MeetingDto currentMeeting = this.locationManagementApiClient.getCurrentMeeting(locationId.getIdValue()).getBody();
            requireNonNull(currentMeeting);
            return this.meetingConverter.toEntity(currentMeeting);
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public void extendCurrentMeeting(LocationId locationId, Duration extension) {
        try {
            this.locationManagementApiClient.extendCurrentMeeting(locationId.getIdValue(), extension.toMinutes());
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
    @Qualifier("locationManagementServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MICROSERVICE)
    @Slf4j
    public static class BaseUrlGetter implements IServiceBaseUrlGetter {

        private final ILocationManagementApiClient locationManagementApiClient;

        public BaseUrlGetter(ILocationManagementApiClient locationManagementApiClient) {
            this.locationManagementApiClient = locationManagementApiClient;
        }

        @Override
        public URL getBaseUrl() {
            // TODO: Exceptions
            try {
                return this.locationManagementApiClient.getBaseUrl().getBody();
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
