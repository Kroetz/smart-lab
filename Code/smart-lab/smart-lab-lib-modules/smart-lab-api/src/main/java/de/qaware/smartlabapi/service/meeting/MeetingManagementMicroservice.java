package de.qaware.smartlabapi.service.meeting;

import de.qaware.smartlabapi.client.IMeetingManagementApiClient;
import de.qaware.smartlabapi.service.generic.AbstractBasicEntityManagementMicroservice;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.exception.*;
import de.qaware.smartlabcore.miscellaneous.Property;
import feign.FeignException;
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
public class MeetingManagementMicroservice extends AbstractBasicEntityManagementMicroservice<IMeeting, MeetingId> implements IMeetingManagementService {

    private final IMeetingManagementApiClient meetingManagementApiClient;

    public MeetingManagementMicroservice(IMeetingManagementApiClient meetingManagementApiClient) {
        super(meetingManagementApiClient);
        this.meetingManagementApiClient = meetingManagementApiClient;
    }

    @Override
    public Set<IMeeting> findAll(RoomId roomId) {
        try {
            return this.meetingManagementApiClient.findAll(roomId.getIdValue());
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public void shortenMeeting(MeetingId meetingId, Duration shortening)
            throws EntityNotFoundException, MinimalDurationReachedException, UnknownErrorException {
        try {
            this.meetingManagementApiClient.shortenMeeting(
                    meetingId.getIdValue(),
                    shortening.toMinutes());
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            if(e.status() == HttpStatus.UNPROCESSABLE_ENTITY.value()) {
                throw new MinimalDurationReachedException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public void extendMeeting(MeetingId meetingId, Duration extension) {
        try {
            this.meetingManagementApiClient.extendMeeting(
                    meetingId.getIdValue(),
                    extension.toMinutes());
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

    @Override
    public void shiftMeeting(MeetingId meetingId, Duration shift) {
        try {
            this.meetingManagementApiClient.shiftMeeting(
                    meetingId.getIdValue(),
                    shift.toMinutes());
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            if(e.status() == HttpStatus.CONFLICT.value()) {
                // TODO: Incorporate information about the conflict
                throw new MeetingConflictException();
            }
            throw new UnknownErrorException();
        }
    }
}
