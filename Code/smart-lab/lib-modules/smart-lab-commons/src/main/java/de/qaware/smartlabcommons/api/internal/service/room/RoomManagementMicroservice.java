package de.qaware.smartlabcommons.api.internal.service.room;

import de.qaware.smartlabcommons.api.internal.client.IRoomManagementApiClient;
import de.qaware.smartlabcommons.api.internal.service.generic.AbstractEntityManagementService;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.exception.EntityNotFoundException;
import de.qaware.smartlabcommons.exception.MaximalDurationReachedException;
import de.qaware.smartlabcommons.exception.MeetingConflictException;
import de.qaware.smartlabcommons.exception.UnknownErrorException;
import de.qaware.smartlabcommons.miscellaneous.Constants;
import de.qaware.smartlabcommons.miscellaneous.ProfileNames;
import feign.FeignException;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

@Component
@Profile(ProfileNames.MICROSERVICE)
public class RoomManagementMicroservice extends AbstractEntityManagementService<IRoom> implements IRoomManagementService {

    private final IRoomManagementApiClient roomManagementApiClient;

    public RoomManagementMicroservice(IRoomManagementApiClient roomManagementApiClient) {
        super(roomManagementApiClient);
        this.roomManagementApiClient = roomManagementApiClient;
    }

    @Override
    public Set<IMeeting> getMeetingsInRoom(String roomId) {
        try {
            return this.roomManagementApiClient.getMeetingsInRoom(roomId).getBody();
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public IMeeting getCurrentMeeting(String roomId) {
        try {
            return this.roomManagementApiClient.getCurrentMeeting(roomId).getBody();
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public void extendCurrentMeeting(String roomId, Duration extension) {
        try {
            this.roomManagementApiClient.extendCurrentMeeting(roomId, extension.toMinutes());
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            if(e.status() == HttpStatus.CONFLICT.value()) {
                // TODO: Incorporate information about the conflict
                throw new MeetingConflictException();
            }
            if(e.status() == HttpStatus.UNPROCESSABLE_ENTITY.value()) {
                throw new MaximalDurationReachedException();
            }
            throw new UnknownErrorException();
        }
    }
}
