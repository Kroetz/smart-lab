package de.qaware.smartlabapi.service.trigger;

import de.qaware.smartlabapi.client.ITriggerApiClient;
import de.qaware.smartlabapi.service.IServiceBaseUrlGetter;
import de.qaware.smartlabcore.data.job.IJobInfo;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import de.qaware.smartlabcore.miscellaneous.Property;
import feign.FeignException;
import feign.RetryableException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
public class TriggerMicroservice implements ITriggerService {

    private final ITriggerApiClient triggerApiClient;

    public TriggerMicroservice(ITriggerApiClient triggerApiClient) {
        this.triggerApiClient = triggerApiClient;
    }

    @Override
    public IJobInfo setUpCurrentMeetingByRoomId(RoomId roomId) {
        try {
            return this.triggerApiClient.setUpCurrentMeetingByRoomId(roomId.getIdValue(), null).getBody();
        }
        catch(Exception e) {
            throw e;
        }


        /*catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }*/
    }

    @Override
    public IJobInfo setUpCurrentMeetingByRoomId(RoomId roomId, URL callbackUrl) {
        try {
            return this.triggerApiClient.setUpCurrentMeetingByRoomId(roomId.getIdValue(), callbackUrl.toString()).getBody();
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
    public IJobInfo cleanUpCurrentMeetingByRoomId(RoomId roomId) {
        try {
            return this.triggerApiClient.cleanUpCurrentMeetingByRoomId(roomId.getIdValue(), null).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo cleanUpCurrentMeetingByRoomId(RoomId roomId, URL callbackUrl) {
        try {
            return this.triggerApiClient.cleanUpCurrentMeetingByRoomId(roomId.getIdValue(), callbackUrl.toString()).getBody();
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
    public IJobInfo startCurrentMeetingByRoomId(RoomId roomId) {
        try {
            return this.triggerApiClient.startCurrentMeetingByRoomId(roomId.getIdValue(), null).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo startCurrentMeetingByRoomId(RoomId roomId, URL callbackUrl) {
        try {
            return this.triggerApiClient.startCurrentMeetingByRoomId(roomId.getIdValue(), callbackUrl.toString()).getBody();
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
    public IJobInfo stopCurrentMeetingByRoomId(RoomId roomId) {
        try {
            return this.triggerApiClient.stopCurrentMeetingByRoomId(roomId.getIdValue(), null).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo stopCurrentMeetingByRoomId(RoomId roomId, URL callbackUrl) {
        try {
            return this.triggerApiClient.stopCurrentMeetingByRoomId(roomId.getIdValue(), callbackUrl.toString()).getBody();
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
            catch(RetryableException e) {
                throw e;
            }
            catch(FeignException e) {
                throw new UnknownErrorException();
            }
        }
    }
}
