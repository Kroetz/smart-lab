package de.qaware.smartlabmonolith.service.connector.location;

import de.qaware.smartlabapi.service.connector.location.ILocationManagementService;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.location.ILocation;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.exception.EntityConflictException;
import de.qaware.smartlabcore.exception.EntityNotFoundException;
import de.qaware.smartlabcore.exception.MaximalDurationReachedException;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabcore.service.url.AbstractMonolithicBaseUrlGetter;
import de.qaware.smartlabmonolith.service.connector.generic.AbstractBasicEntityManagementMonolithicServiceConnector;
import de.qaware.smartlablocation.service.controller.LocationManagementController;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class LocationManagementMonolithicServiceConnector extends AbstractBasicEntityManagementMonolithicServiceConnector<ILocation, LocationId> implements ILocationManagementService {

    private final LocationManagementController locationManagementController;

    public LocationManagementMonolithicServiceConnector(LocationManagementController locationManagementController) {
        super(locationManagementController);
        this.locationManagementController = locationManagementController;
    }

    @Override
    public Set<IMeeting> getMeetingsAtLocation(LocationId locationId) {
        ResponseEntity<Set<IMeeting>> response = this.locationManagementController.getMeetingsAtLocation(locationId.getIdValue());
        if(response.getStatusCode() == HttpStatus.OK) return response.getBody();
        // TODO: Meaningful exception message
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        throw new UnknownErrorException();
    }

    @Override
    public IMeeting getCurrentMeeting(LocationId locationId) {
        ResponseEntity<IMeeting> response = this.locationManagementController.getCurrentMeeting(locationId.getIdValue());
        if(response.getStatusCode() == HttpStatus.OK) return response.getBody();
        // TODO: Meaningful exception message
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        throw new UnknownErrorException();
    }

    @Override
    public void extendCurrentMeeting(LocationId locationId, Duration extension) {
        ResponseEntity<Void> response = this.locationManagementController.extendCurrentMeeting(locationId.getIdValue(), extension.toMinutes());
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
