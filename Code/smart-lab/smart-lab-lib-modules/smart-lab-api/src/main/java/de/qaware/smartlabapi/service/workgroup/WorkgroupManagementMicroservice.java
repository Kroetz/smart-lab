package de.qaware.smartlabapi.service.workgroup;

import de.qaware.smartlabapi.client.IWorkgroupManagementApiClient;
import de.qaware.smartlabapi.service.generic.AbstractBasicEntityManagementMicroservice;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.exception.EntityNotFoundException;
import de.qaware.smartlabcore.exception.MaximalDurationReachedException;
import de.qaware.smartlabcore.exception.MeetingConflictException;
import de.qaware.smartlabcore.exception.UnknownErrorException;
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
public class WorkgroupManagementMicroservice extends AbstractBasicEntityManagementMicroservice<IWorkgroup, WorkgroupId> implements IWorkgroupManagementService {

    private final IWorkgroupManagementApiClient workgroupManagementApiClient;

    public WorkgroupManagementMicroservice(IWorkgroupManagementApiClient workgroupManagementApiClient) {
        super(workgroupManagementApiClient);
        this.workgroupManagementApiClient = workgroupManagementApiClient;
    }

    @Override
    public Set<IMeeting> getMeetingsOfWorkgroup(WorkgroupId workgroupId) {
        try {
            return this.workgroupManagementApiClient.getMeetingsOfWorkgroup(workgroupId.getIdValue()).getBody();
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public IMeeting getCurrentMeeting(WorkgroupId workgroupId) {
        try {
            return this.workgroupManagementApiClient.getCurrentMeeting(workgroupId.getIdValue()).getBody();
        }
        catch(FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new EntityNotFoundException();
            }
            throw new UnknownErrorException();
        }
    }

    @Override
    public void extendCurrentMeeting(WorkgroupId workgroupId, Duration extension) {
        try {
            this.workgroupManagementApiClient.extendCurrentMeeting(workgroupId.getIdValue(), extension.toMinutes());
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
