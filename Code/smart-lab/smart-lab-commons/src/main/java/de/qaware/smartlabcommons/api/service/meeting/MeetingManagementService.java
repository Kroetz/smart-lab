package de.qaware.smartlabcommons.api.service.meeting;

import de.qaware.smartlabcommons.api.client.IMeetingManagementApiClient;
import de.qaware.smartlabcommons.api.service.generic.AbstractEntityManagementService;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.exception.*;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class MeetingManagementService extends AbstractEntityManagementService<IMeeting> implements IMeetingManagementService {

    private final IMeetingManagementApiClient meetingManagementApiClient;

    public MeetingManagementService(IMeetingManagementApiClient meetingManagementApiClient) {
        super(meetingManagementApiClient);
        this.meetingManagementApiClient = meetingManagementApiClient;
    }

    @Override
    public void shortenMeeting(String meetingId, Duration shortening)
            throws EntityNotFoundException, MinimalDurationReachedException, UnknownErrorException {
        try {
            this.meetingManagementApiClient.shortenMeeting(meetingId, shortening.toMinutes());
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
    public void extendMeeting(String meetingId, Duration extension) {
        try {
            this.meetingManagementApiClient.extendMeeting(meetingId, extension.toMinutes());
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
    public void shiftMeeting(String meetingId, Duration shift) {
        try {
            this.meetingManagementApiClient.shiftMeeting(meetingId, shift.toMinutes());
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
