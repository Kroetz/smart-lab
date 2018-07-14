package de.qaware.smartlabmonolith.service.connector.meeting;

import de.qaware.smartlabapi.service.connector.meeting.IMeetingManagementService;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.exception.*;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabmeeting.service.controller.MeetingManagementController;
import de.qaware.smartlabcore.service.url.AbstractMonolithicBaseUrlGetter;
import de.qaware.smartlabmonolith.service.connector.generic.AbstractBasicEntityManagementMonolithicServiceConnector;
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
public class MeetingManagementMonolithicServiceConnector extends AbstractBasicEntityManagementMonolithicServiceConnector<IMeeting, MeetingId> implements IMeetingManagementService {

    private final MeetingManagementController meetingManagementController;

    public MeetingManagementMonolithicServiceConnector(MeetingManagementController meetingManagementController) {
        super(meetingManagementController);
        this.meetingManagementController = meetingManagementController;
    }

    @Override
    public Set<IMeeting> findAll(RoomId roomId) {
        return this.meetingManagementController.findAllByRoomId(roomId.getIdValue());
    }

    @Override
    public Set<IMeeting> findAll(WorkgroupId workgroupId) {
        return this.meetingManagementController.findAllByWorkgroupId(workgroupId.getIdValue());
    }

    @Override
    public Set<IMeeting> findAllCurrent() {
        return this.meetingManagementController.findAllCurrent();
    }

    @Override
    public IMeeting findCurrent(RoomId roomId) {
        ResponseEntity<IMeeting> response = this.meetingManagementController.findCurrentByRoomId(roomId.getIdValue());
        if(response.getStatusCode() == HttpStatus.OK) return response.getBody();
        // TODO: Meaningful exception message
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        throw new UnknownErrorException();
    }

    @Override
    public IMeeting findCurrent(WorkgroupId workgroupId) {
        ResponseEntity<IMeeting> response = this.meetingManagementController.findCurrentByWorkgroupId(workgroupId.getIdValue());
        if(response.getStatusCode() == HttpStatus.OK) return response.getBody();
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