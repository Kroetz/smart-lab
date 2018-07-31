package de.qaware.smartlabapi.service.connector.trigger;

import de.qaware.smartlabapi.service.client.trigger.ITriggerApiClient;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.service.url.IServiceBaseUrlGetter;
import de.qaware.smartlabcore.data.job.IJobInfo;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import de.qaware.smartlabcore.miscellaneous.Property;
import feign.FeignException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
public class TriggerMicroserviceConnector implements ITriggerService {

    private final ITriggerApiClient triggerApiClient;

    public TriggerMicroserviceConnector(ITriggerApiClient triggerApiClient) {
        this.triggerApiClient = triggerApiClient;
    }

    @Override
    public IJobInfo setUpCurrentMeetingByLocationId(LocationId locationId) {
        try {
            return this.triggerApiClient.setUpCurrentMeetingByLocationId(locationId.getIdValue(), null).getBody();
        }
        catch(Exception e) {
            throw e;
        }

        // TODO
        /*catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }*/
    }

    @Override
    public IJobInfo setUpCurrentMeetingByLocationId(LocationId locationId, URL callbackUrl) {
        try {
            return this.triggerApiClient.setUpCurrentMeetingByLocationId(locationId.getIdValue(), callbackUrl.toString()).getBody();
        }
        catch(Exception e) {
            throw e;
        }
    }

    @Override
    public IJobInfo setUpCurrentMeetingByWorkgroupId(WorkgroupId workgroupId) {
        try {
            return this.triggerApiClient.setUpCurrentMeetingByWorkgroupId(workgroupId.getIdValue(), null).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo setUpCurrentMeetingByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl) {
        try {
            return this.triggerApiClient.setUpCurrentMeetingByWorkgroupId(workgroupId.getIdValue(), callbackUrl.toString()).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo cleanUpCurrentMeetingByLocationId(LocationId locationId) {
        try {
            return this.triggerApiClient.cleanUpCurrentMeetingByLocationId(locationId.getIdValue(), null).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo cleanUpCurrentMeetingByLocationId(LocationId locationId, URL callbackUrl) {
        try {
            return this.triggerApiClient.cleanUpCurrentMeetingByLocationId(locationId.getIdValue(), callbackUrl.toString()).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo cleanUpCurrentMeetingByWorkgroupId(WorkgroupId workgroupId) {
        try {
            return this.triggerApiClient.cleanUpCurrentMeetingByWorkgroupId(workgroupId.getIdValue(), null).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo cleanUpCurrentMeetingByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl) {
        try {
            return this.triggerApiClient.cleanUpCurrentMeetingByWorkgroupId(workgroupId.getIdValue(), callbackUrl.toString()).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo startCurrentMeetingByLocationId(LocationId locationId) {
        try {
            return this.triggerApiClient.startCurrentMeetingByLocationId(locationId.getIdValue(), null).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo startCurrentMeetingByLocationId(LocationId locationId, URL callbackUrl) {
        try {
            return this.triggerApiClient.startCurrentMeetingByLocationId(locationId.getIdValue(), callbackUrl.toString()).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo startCurrentMeetingByWorkgroupId(WorkgroupId workgroupId) {
        try {
            return this.triggerApiClient.startCurrentMeetingByWorkgroupId(workgroupId.getIdValue(), null).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo startCurrentMeetingByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl) {
        try {
            return this.triggerApiClient.startCurrentMeetingByWorkgroupId(workgroupId.getIdValue(), callbackUrl.toString()).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo stopCurrentMeetingByLocationId(LocationId locationId) {
        try {
            return this.triggerApiClient.stopCurrentMeetingByLocationId(locationId.getIdValue(), null).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo stopCurrentMeetingByLocationId(LocationId locationId, URL callbackUrl) {
        try {
            return this.triggerApiClient.stopCurrentMeetingByLocationId(locationId.getIdValue(), callbackUrl.toString()).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo stopCurrentMeetingByWorkgroupId(WorkgroupId workgroupId) {
        try {
            return this.triggerApiClient.stopCurrentMeetingByWorkgroupId(workgroupId.getIdValue(), null).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo stopCurrentMeetingByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl) {
        try {
            return this.triggerApiClient.stopCurrentMeetingByWorkgroupId(workgroupId.getIdValue(), callbackUrl.toString()).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Component
    // TODO: String literal
    @Qualifier("triggerServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MICROSERVICE)
    @Slf4j
    public static class BaseUrlGetter implements IServiceBaseUrlGetter {

        private final ITriggerApiClient triggerApiClient;

        public BaseUrlGetter(ITriggerApiClient triggerApiClient) {
            this.triggerApiClient = triggerApiClient;
        }

        @Override
        public URL getBaseUrl() {
            // TODO: Exceptions
            try {
                return this.triggerApiClient.getBaseUrl().getBody();
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
