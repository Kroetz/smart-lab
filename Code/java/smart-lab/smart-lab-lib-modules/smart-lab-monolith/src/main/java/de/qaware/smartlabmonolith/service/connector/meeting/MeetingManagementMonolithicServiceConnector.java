package de.qaware.smartlabmonolith.service.connector.meeting;

import de.qaware.smartlabapi.service.connector.meeting.IMeetingManagementService;
import de.qaware.smartlabcore.data.generic.IDtoConverter;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.meeting.MeetingDto;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.exception.*;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabcore.service.url.AbstractMonolithicBaseUrlGetter;
import de.qaware.smartlabeventmanagement.service.controller.MeetingManagementController;
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
public class MeetingManagementMonolithicServiceConnector extends AbstractBasicEntityManagementMonolithicServiceConnector<IMeeting, MeetingId, MeetingDto> implements IMeetingManagementService {

    private final MeetingManagementController meetingManagementController;

    public MeetingManagementMonolithicServiceConnector(
            MeetingManagementController meetingManagementController,
            IDtoConverter<IMeeting, MeetingDto> meetingConverter) {
        super(meetingManagementController, meetingConverter);
        this.meetingManagementController = meetingManagementController;
    }

    @Override
    public Set<IMeeting> findAll(LocationId locationId) {
        return this.meetingManagementController.findAllByLocationId(locationId.getIdValue()).stream()
                .map(this.converter::toEntity)
                .collect(toSet());
    }

    @Override
    public Set<IMeeting> findAll(WorkgroupId workgroupId) {
        return this.meetingManagementController.findAllByWorkgroupId(workgroupId.getIdValue()).stream()
                .map(this.converter::toEntity)
                .collect(toSet());
    }

    @Override
    public Set<IMeeting> findAllCurrent() {
        return this.meetingManagementController.findAllCurrent().stream()
                .map(this.converter::toEntity)
                .collect(toSet());
    }

    @Override
    public IMeeting findCurrent(LocationId locationId) {
        ResponseEntity<MeetingDto> response = this.meetingManagementController.findCurrentByLocationId(locationId.getIdValue());
        if(response.getStatusCode() == HttpStatus.OK) return this.converter.toEntity(requireNonNull(response.getBody()));
        // TODO: Meaningful exception message
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        throw new UnknownErrorException();
    }

    @Override
    public IMeeting findCurrent(WorkgroupId workgroupId) {
        ResponseEntity<MeetingDto> response = this.meetingManagementController.findCurrentByWorkgroupId(workgroupId.getIdValue());
        if(response.getStatusCode() == HttpStatus.OK) return this.converter.toEntity(requireNonNull(response.getBody()));
        // TODO: Meaningful exception message
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        throw new UnknownErrorException();
    }

    @Override
    public void shortenMeeting(MeetingId meetingId, Duration shortening) {
        ResponseEntity<Void> response = this.meetingManagementController.shortenMeeting(
                meetingId.getIdValue(),
                shortening.toMinutes());
        if(response.getStatusCode() == HttpStatus.OK) return;
        // TODO: Meaningful exception messages
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        if(response.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) throw new MinimalDurationReachedException();
        throw new UnknownErrorException();
    }

    @Override
    public void extendMeeting(MeetingId meetingId, Duration extension) {
        ResponseEntity<Void> response = this.meetingManagementController.extendMeeting(
                meetingId.getIdValue(),
                extension.toMinutes());
        if(response.getStatusCode() == HttpStatus.OK) return;
        // TODO: Meaningful exception messages
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        if(response.getStatusCode() == HttpStatus.CONFLICT) throw new EntityConflictException();
        if(response.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) throw new MaximalDurationReachedException();
        throw new UnknownErrorException();
    }

    @Override
    public void shiftMeeting(MeetingId meetingId, Duration shift) {
        ResponseEntity<Void> response = this.meetingManagementController.shiftMeeting(
                meetingId.getIdValue(),
                shift.toMinutes());
        if(response.getStatusCode() == HttpStatus.OK) return;
        // TODO: Meaningful exception messages
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        if(response.getStatusCode() == HttpStatus.CONFLICT) throw new EntityConflictException();
        throw new UnknownErrorException();
    }

    @Component
    // TODO: String literal
    @Qualifier("meetingManagementServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MONOLITH)
    public static class BaseUrlGetter extends AbstractMonolithicBaseUrlGetter {

        public BaseUrlGetter(MeetingManagementController.BaseUrlController baseUrlController) {
            super(baseUrlController);
        }
    }
}
