package de.qaware.smartlab.microservice.service.connector.trigger;

import de.qaware.smartlab.api.service.client.trigger.ITriggerApiClient;
import de.qaware.smartlab.api.service.connector.trigger.ITriggerService;
import de.qaware.smartlab.core.data.job.IJobInfo;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.exception.SmartLabException;
import de.qaware.smartlab.core.exception.UnknownErrorException;
import de.qaware.smartlab.core.miscellaneous.Property;
import de.qaware.smartlab.core.service.url.IServiceBaseUrlGetter;
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
    public IJobInfo setUpCurrentEventByLocationId(LocationId locationId) {
        try {
            return this.triggerApiClient.setUpCurrentEventByLocationId(locationId.getIdValue(), null).getBody();
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public IJobInfo setUpCurrentEventByLocationId(LocationId locationId, URL callbackUrl) {
        try {
            return this.triggerApiClient.setUpCurrentEventByLocationId(locationId.getIdValue(), callbackUrl.toString()).getBody();
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public IJobInfo setUpCurrentEventByWorkgroupId(WorkgroupId workgroupId) {
        try {
            return this.triggerApiClient.setUpCurrentEventByWorkgroupId(workgroupId.getIdValue(), null).getBody();
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public IJobInfo setUpCurrentEventByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl) {
        try {
            return this.triggerApiClient.setUpCurrentEventByWorkgroupId(workgroupId.getIdValue(), callbackUrl.toString()).getBody();
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public IJobInfo cleanUpCurrentEventByLocationId(LocationId locationId) {
        try {
            return this.triggerApiClient.cleanUpCurrentEventByLocationId(locationId.getIdValue(), null).getBody();
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public IJobInfo cleanUpCurrentEventByLocationId(LocationId locationId, URL callbackUrl) {
        try {
            return this.triggerApiClient.cleanUpCurrentEventByLocationId(locationId.getIdValue(), callbackUrl.toString()).getBody();
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public IJobInfo cleanUpCurrentEventByWorkgroupId(WorkgroupId workgroupId) {
        try {
            return this.triggerApiClient.cleanUpCurrentEventByWorkgroupId(workgroupId.getIdValue(), null).getBody();
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public IJobInfo cleanUpCurrentEventByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl) {
        try {
            return this.triggerApiClient.cleanUpCurrentEventByWorkgroupId(workgroupId.getIdValue(), callbackUrl.toString()).getBody();
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public IJobInfo startCurrentEventByLocationId(LocationId locationId) {
        try {
            return this.triggerApiClient.startCurrentEventByLocationId(locationId.getIdValue(), null).getBody();
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public IJobInfo startCurrentEventByLocationId(LocationId locationId, URL callbackUrl) {
        try {
            return this.triggerApiClient.startCurrentEventByLocationId(locationId.getIdValue(), callbackUrl.toString()).getBody();
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public IJobInfo startCurrentEventByWorkgroupId(WorkgroupId workgroupId) {
        try {
            return this.triggerApiClient.startCurrentEventByWorkgroupId(workgroupId.getIdValue(), null).getBody();
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public IJobInfo startCurrentEventByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl) {
        try {
            return this.triggerApiClient.startCurrentEventByWorkgroupId(workgroupId.getIdValue(), callbackUrl.toString()).getBody();
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public IJobInfo stopCurrentEventByLocationId(LocationId locationId) {
        try {
            return this.triggerApiClient.stopCurrentEventByLocationId(locationId.getIdValue(), null).getBody();
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public IJobInfo stopCurrentEventByLocationId(LocationId locationId, URL callbackUrl) {
        try {
            return this.triggerApiClient.stopCurrentEventByLocationId(locationId.getIdValue(), callbackUrl.toString()).getBody();
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public IJobInfo stopCurrentEventByWorkgroupId(WorkgroupId workgroupId) {
        try {
            return this.triggerApiClient.stopCurrentEventByWorkgroupId(workgroupId.getIdValue(), null).getBody();
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
        }
    }

    @Override
    public IJobInfo stopCurrentEventByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl) {
        try {
            return this.triggerApiClient.stopCurrentEventByWorkgroupId(workgroupId.getIdValue(), callbackUrl.toString()).getBody();
        }
        // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
        catch (Exception e) {
            throw new SmartLabException(e);
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
            try {
                return this.triggerApiClient.getBaseUrl().getBody();
            }
            // TODO: Use a Feign ErrorDecoder for mapping exceptions appropriately. Manual mapping of all exceptions to SmartLabException is just a workaround.
            catch (Exception e) {
                throw new SmartLabException(e);
            }
        }
    }
}
