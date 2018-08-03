package de.qaware.smartlabmonolith.service.connector.location;

import de.qaware.smartlabapi.service.connector.location.ILocationManagementService;
import de.qaware.smartlabcore.data.generic.IDtoConverter;
import de.qaware.smartlabcore.data.location.ILocation;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.data.location.dto.LocationDto;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.dto.MeetingDto;
import de.qaware.smartlabcore.exception.EntityConflictException;
import de.qaware.smartlabcore.exception.EntityNotFoundException;
import de.qaware.smartlabcore.exception.MaximalDurationReachedException;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabcore.service.url.AbstractMonolithicBaseUrlGetter;
import de.qaware.smartlablocation.service.controller.LocationManagementController;
import de.qaware.smartlabmonolith.service.connector.generic.AbstractBasicEntityManagementMonolithicServiceConnector;
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
    private final IDtoConverter<IMeeting, MeetingDto> meetingConverter;

    public LocationManagementMonolithicServiceConnector(
            LocationManagementController locationManagementController,
            IDtoConverter<ILocation, LocationDto> locationConverter,
            IDtoConverter<IMeeting, MeetingDto> meetingConverter) {
        super(locationManagementController, locationConverter);
        this.locationManagementController = locationManagementController;
        this.meetingConverter = meetingConverter;
    }

    @Override
    public Set<IMeeting> getMeetingsAtLocation(LocationId locationId) {
        ResponseEntity<Set<MeetingDto>> response = this.locationManagementController.getMeetingsAtLocation(locationId.getIdValue());
        if(response.getStatusCode() == HttpStatus.OK) return requireNonNull(response.getBody()).stream().map(this.meetingConverter::toEntity).collect(toSet());
        // TODO: Meaningful exception message
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        throw new UnknownErrorException();
    }

    @Override
    public IMeeting getCurrentMeeting(LocationId locationId) {
        ResponseEntity<MeetingDto> response = this.locationManagementController.getCurrentMeeting(locationId.getIdValue());
        if(response.getStatusCode() == HttpStatus.OK) return this.meetingConverter.toEntity(requireNonNull(response.getBody()));
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
