package de.qaware.smartlabcommons.api.service.workgroup;

import de.qaware.smartlabcommons.api.client.IWorkgroupManagementApiClient;
import de.qaware.smartlabcommons.api.service.generic.AbstractEntityManagementService;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcommons.exception.EntityNotFoundException;
import de.qaware.smartlabcommons.exception.MaximalDurationReachedException;
import de.qaware.smartlabcommons.exception.MeetingConflictException;
import de.qaware.smartlabcommons.exception.UnknownErrorException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

@Component
public class WorkgroupManagementService extends AbstractEntityManagementService<IWorkgroup> implements IWorkgroupManagementService {

    private final IWorkgroupManagementApiClient workgroupManagementApiClient;

    public WorkgroupManagementService(IWorkgroupManagementApiClient workgroupManagementApiClient) {
        super(workgroupManagementApiClient);
        this.workgroupManagementApiClient = workgroupManagementApiClient;
    }

    @Override
    public Set<IMeeting> getMeetingsOfWorkgroup(String workgroupId) {
        try {
            return this.workgroupManagementApiClient.getMeetingsOfWorkgroup(workgroupId).getBody();
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public IMeeting getCurrentMeeting(String workgroupId) {
        try {
            return this.workgroupManagementApiClient.getCurrentMeeting(workgroupId).getBody();
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public void extendCurrentMeeting(String workgroupId, Duration extension) {
        try {
            this.workgroupManagementApiClient.extendCurrentMeeting(workgroupId, extension.toMinutes());
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
