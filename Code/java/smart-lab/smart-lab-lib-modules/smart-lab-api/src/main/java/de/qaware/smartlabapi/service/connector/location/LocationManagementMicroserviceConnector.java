package de.qaware.smartlabapi.service.connector.location;

import de.qaware.smartlabapi.service.client.location.ILocationManagementApiClient;
import de.qaware.smartlabapi.service.connector.generic.AbstractBasicEntityManagementMicroserviceConnector;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.location.ILocation;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.exception.EntityConflictException;
import de.qaware.smartlabcore.exception.EntityNotFoundException;
import de.qaware.smartlabcore.exception.MaximalDurationReachedException;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabcore.service.url.IServiceBaseUrlGetter;
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

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
public class LocationManagementMicroserviceConnector extends AbstractBasicEntityManagementMicroserviceConnector<ILocation, LocationId> implements ILocationManagementService {

    private final ILocationManagementApiClient locationManagementApiClient;

    public LocationManagementMicroserviceConnector(ILocationManagementApiClient locationManagementApiClient) {
        super(locationManagementApiClient);
        this.locationManagementApiClient = locationManagementApiClient;
    }

    @Override
    public Set<IMeeting> getMeetingsAtLocation(LocationId locationId) {
        try {
            return this.locationManagementApiClient.getMeetingsAtLocation(locationId.getIdValue()).getBody();
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
            return this.locationManagementApiClient.getCurrentMeeting(locationId.getIdValue()).getBody();
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
