package de.qaware.smartlabapi.service.meeting;

import de.qaware.smartlabapi.client.IMeetingManagementApiClient;
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
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
public class MeetingManagementMicroservice implements IMeetingManagementService {

    private final IMeetingManagementApiClient meetingManagementApiClient;

    public MeetingManagementMicroservice(IMeetingManagementApiClient meetingManagementApiClient) {
        this.meetingManagementApiClient = meetingManagementApiClient;
    }

    @Override
    public Map<RoomId, Set<IMeeting>> findAll() {
        try {
            return this.meetingManagementApiClient.findAll();
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
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
    public IMeeting findOne(MeetingId meetingId, RoomId roomId) {
        try {
            return this.meetingManagementApiClient.findOne(
                    meetingId.getIdValue(),
                    roomId.getIdValue()).getBody();
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public Set<IMeeting> findMultiple(MeetingId[] meetingIds, RoomId roomId) {
        try {
            return this.meetingManagementApiClient.findMultiple(
                    Arrays.stream(meetingIds).map(MeetingId::getIdValue).toArray(String[]::new),
                    roomId.getIdValue())
                    .getBody();
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                // TODO: Incorporate the IDs of the entities that were not found
                throw new EntityNotFoundException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public void create(IMeeting meeting) {
        try {
            this.meetingManagementApiClient.create(meeting);
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.CONFLICT.value()) {
                // TODO: Incorporate information about the conflict
                throw new MeetingConflictException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public void delete(MeetingId meetingId, RoomId roomId) {
        try {
            this.meetingManagementApiClient.delete(meetingId.getIdValue(), roomId.getIdValue());
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public void shortenMeeting(MeetingId meetingId, RoomId roomId, Duration shortening)
            throws EntityNotFoundException, MinimalDurationReachedException, UnknownErrorException {
        try {
            this.meetingManagementApiClient.shortenMeeting(
                    meetingId.getIdValue(),
                    roomId.getIdValue(),
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
    public void extendMeeting(MeetingId meetingId, RoomId roomId, Duration extension) {
        try {
            this.meetingManagementApiClient.extendMeeting(
                    meetingId.getIdValue(),
                    roomId.getIdValue(),
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
    public void shiftMeeting(MeetingId meetingId, RoomId roomId, Duration shift) {
        try {
            this.meetingManagementApiClient.shiftMeeting(
                    meetingId.getIdValue(),
                    roomId.getIdValue(),
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
