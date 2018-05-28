package de.qaware.smartlabcommons.api.internal.service.trigger;

import de.qaware.smartlabcommons.api.internal.client.ITriggerApiClient;
import de.qaware.smartlabcommons.exception.UnknownErrorException;
import de.qaware.smartlabcommons.miscellaneous.Property;
import feign.FeignException;
import feign.RetryableException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

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
    public void setUpCurrentMeetingByRoomId(String roomId) {
        try {
            this.triggerApiClient.setUpCurrentMeetingByRoomId(roomId);
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
    public void setUpCurrentMeetingByWorkgroupId(String workgroupId) {
        try {
            this.triggerApiClient.setUpCurrentMeetingByWorkgroupId(workgroupId);
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public void cleanUpCurrentMeetingByRoomId(String roomId) {
        try {
            this.triggerApiClient.cleanUpCurrentMeetingByRoomId(roomId);
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public void cleanUpCurrentMeetingByWorkgroupId(String workgroupId) {
        try {
            this.triggerApiClient.cleanUpCurrentMeetingByWorkgroupId(workgroupId);
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public void startCurrentMeetingByRoomId(String roomId) {
        try {
            this.triggerApiClient.startCurrentMeetingByRoomId(roomId);
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public void startCurrentMeetingByWorkgroupId(String workgroupId) {
        try {
            this.triggerApiClient.startCurrentMeetingByWorkgroupId(workgroupId);
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public void stopCurrentMeetingByRoomId(String roomId) {
        try {
            this.triggerApiClient.stopCurrentMeetingByRoomId(roomId);
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public void stopCurrentMeetingByWorkgroupId(String workgroupId) {
        try {
            this.triggerApiClient.stopCurrentMeetingByWorkgroupId(workgroupId);
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }
}
