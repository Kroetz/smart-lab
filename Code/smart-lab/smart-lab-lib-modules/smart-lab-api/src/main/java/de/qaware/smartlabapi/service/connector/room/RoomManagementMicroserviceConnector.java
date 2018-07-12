package de.qaware.smartlabapi.service.connector.room;

import de.qaware.smartlabapi.service.client.room.IRoomManagementApiClient;
import de.qaware.smartlabapi.service.connector.generic.AbstractBasicEntityManagementMicroserviceConnector;
import de.qaware.smartlabapi.service.url.AbstractMicroserviceBaseUrlGetter;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.exception.EntityConflictException;
import de.qaware.smartlabcore.exception.EntityNotFoundException;
import de.qaware.smartlabcore.exception.MaximalDurationReachedException;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import de.qaware.smartlabcore.miscellaneous.Property;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
public class RoomManagementMicroserviceConnector extends AbstractBasicEntityManagementMicroserviceConnector<IRoom, RoomId> implements IRoomManagementService {

    private final IRoomManagementApiClient roomManagementApiClient;

    public RoomManagementMicroserviceConnector(IRoomManagementApiClient roomManagementApiClient) {
        super(roomManagementApiClient);
        this.roomManagementApiClient = roomManagementApiClient;
    }

    @Override
    public Set<IMeeting> getMeetingsInRoom(RoomId roomId) {
        try {
            return this.roomManagementApiClient.getMeetingsInRoom(roomId.getIdValue()).getBody();
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public IMeeting getCurrentMeeting(RoomId roomId) {
        try {
            return this.roomManagementApiClient.getCurrentMeeting(roomId.getIdValue()).getBody();
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public void extendCurrentMeeting(RoomId roomId, Duration extension) {
        try {
            this.roomManagementApiClient.extendCurrentMeeting(roomId.getIdValue(), extension.toMinutes());
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
    @Qualifier("roomManagementServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MICROSERVICE)
    public static class BaseUrlGetter extends AbstractMicroserviceBaseUrlGetter {

        public BaseUrlGetter(IRoomManagementApiClient roomManagementApiClient) {
            super(roomManagementApiClient);
        }
    }
}
