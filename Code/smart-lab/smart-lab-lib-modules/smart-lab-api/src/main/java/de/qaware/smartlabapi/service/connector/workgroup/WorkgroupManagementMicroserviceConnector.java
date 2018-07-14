package de.qaware.smartlabapi.service.connector.workgroup;

import de.qaware.smartlabapi.service.client.workgroup.IWorkgroupManagementApiClient;
import de.qaware.smartlabapi.service.connector.generic.AbstractBasicEntityManagementMicroserviceConnector;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.exception.EntityConflictException;
import de.qaware.smartlabcore.exception.EntityNotFoundException;
import de.qaware.smartlabcore.exception.MaximalDurationReachedException;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabcore.service.url.IServiceBaseUrlGetter;
import feign.FeignException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.Duration;
import java.util.Set;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
public class WorkgroupManagementMicroserviceConnector extends AbstractBasicEntityManagementMicroserviceConnector<IWorkgroup, WorkgroupId> implements IWorkgroupManagementService {

    private final IWorkgroupManagementApiClient workgroupManagementApiClient;

    public WorkgroupManagementMicroserviceConnector(IWorkgroupManagementApiClient workgroupManagementApiClient) {
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
    @Qualifier("workgroupManagementServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MICROSERVICE)
    @Slf4j
    public static class BaseUrlGetter implements IServiceBaseUrlGetter {

        private final IWorkgroupManagementApiClient workgroupManagementApiClient;

        public BaseUrlGetter(IWorkgroupManagementApiClient workgroupManagementApiClient) {
            this.workgroupManagementApiClient = workgroupManagementApiClient;
        }

        @Override
        public URL getBaseUrl() {
            // TODO: Exceptions
            try {
                return this.workgroupManagementApiClient.getBaseUrl().getBody();
            }
            catch(RetryableException e) {
                throw e;
            }
            catch(FeignException e) {
                throw new UnknownErrorException();
            }
        }
    }
}