package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabapi.service.room.IRoomManagementService;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.exception.EntityConflictException;
import de.qaware.smartlabcore.exception.EntityNotFoundException;
import de.qaware.smartlabcore.exception.MaximalDurationReachedException;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabcore.url.AbstractMonolithicBaseUrlGetter;
import de.qaware.smartlabroom.service.controller.RoomManagementController;
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
public class RoomManagementMonolithicService extends AbstractBasicEntityManagementMonolithicService<IRoom, RoomId> implements IRoomManagementService {

    private final RoomManagementController roomManagementController;

    public RoomManagementMonolithicService(RoomManagementController roomManagementController) {
        super(roomManagementController);
        this.roomManagementController = roomManagementController;
    }

    @Override
    public Set<IMeeting> getMeetingsInRoom(RoomId roomId) {
        ResponseEntity<Set<IMeeting>> response = this.roomManagementController.getMeetingsInRoom(roomId.getIdValue());
        if(response.getStatusCode() == HttpStatus.OK) return response.getBody();
        // TODO: Meaningful exception message
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        throw new UnknownErrorException();
    }

    @Override
    public IMeeting getCurrentMeeting(RoomId roomId) {
        ResponseEntity<IMeeting> response = this.roomManagementController.getCurrentMeeting(roomId.getIdValue());
        if(response.getStatusCode() == HttpStatus.OK) return response.getBody();
        // TODO: Meaningful exception message
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        throw new UnknownErrorException();
    }

    @Override
    public void extendCurrentMeeting(RoomId roomId, Duration extension) {
        ResponseEntity<Void> response = this.roomManagementController.extendCurrentMeeting(roomId.getIdValue(), extension.toMinutes());
        if(response.getStatusCode() == HttpStatus.OK) return;
        // TODO: Meaningful exception messages
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) throw new EntityNotFoundException();
        if(response.getStatusCode() == HttpStatus.CONFLICT) throw new EntityConflictException();
        if(response.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) throw new MaximalDurationReachedException();
        throw new UnknownErrorException();
    }

    @Component
    // TODO: String literal
    @Qualifier("roomManagementServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MONOLITH)
    public static class BaseUrlGetter extends AbstractMonolithicBaseUrlGetter {

        public BaseUrlGetter(RoomManagementController.BaseUrlController baseUrlController) {
            super(baseUrlController);
        }
    }
}
