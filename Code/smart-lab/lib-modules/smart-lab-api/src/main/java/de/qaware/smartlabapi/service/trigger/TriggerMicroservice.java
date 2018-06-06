package de.qaware.smartlabapi.service.trigger;

import de.qaware.smartlabapi.client.ITriggerApiClient;
import de.qaware.smartlabcore.exception.UnknownErrorException;
import de.qaware.smartlabcore.miscellaneous.Property;
import feign.FeignException;
import feign.RetryableException;
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
    public void setUpCurrentMeetingByRoomId(String roomId) {
        try {
            this.triggerApiClient.setUpCurrentMeetingByRoomId(roomId, null);
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
    public void setUpCurrentMeetingByRoomId(String roomId, URL callbackUrl) {
        try {
            this.triggerApiClient.setUpCurrentMeetingByRoomId(roomId, callbackUrl.toString());
        }
        catch(Exception e) {
            throw e;
        }
    }

    @Override
    public void setUpCurrentMeetingByWorkgroupId(String workgroupId) {
        try {
            this.triggerApiClient.setUpCurrentMeetingByWorkgroupId(workgroupId, null);
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public void setUpCurrentMeetingByWorkgroupId(String workgroupId, URL callbackUrl) {
        try {
            this.triggerApiClient.setUpCurrentMeetingByWorkgroupId(workgroupId, callbackUrl.toString());
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
            this.triggerApiClient.cleanUpCurrentMeetingByRoomId(roomId, null);
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public void cleanUpCurrentMeetingByRoomId(String roomId, URL callbackUrl) {
        try {
            this.triggerApiClient.cleanUpCurrentMeetingByRoomId(roomId, callbackUrl.toString());
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
            this.triggerApiClient.cleanUpCurrentMeetingByWorkgroupId(workgroupId, null);
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public void cleanUpCurrentMeetingByWorkgroupId(String workgroupId, URL callbackUrl) {
        try {
            this.triggerApiClient.cleanUpCurrentMeetingByWorkgroupId(workgroupId, callbackUrl.toString());
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
            this.triggerApiClient.startCurrentMeetingByRoomId(roomId, null);
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public void startCurrentMeetingByRoomId(String roomId, URL callbackUrl) {
        try {
            this.triggerApiClient.startCurrentMeetingByRoomId(roomId, callbackUrl.toString());
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
            this.triggerApiClient.startCurrentMeetingByWorkgroupId(workgroupId, null);
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public void startCurrentMeetingByWorkgroupId(String workgroupId, URL callbackUrl) {
        try {
            this.triggerApiClient.startCurrentMeetingByWorkgroupId(workgroupId, callbackUrl.toString());
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
            this.triggerApiClient.stopCurrentMeetingByRoomId(roomId, null);
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public void stopCurrentMeetingByRoomId(String roomId, URL callbackUrl) {
        try {
            this.triggerApiClient.stopCurrentMeetingByRoomId(roomId, callbackUrl.toString());
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
            this.triggerApiClient.stopCurrentMeetingByWorkgroupId(workgroupId, null);
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public void stopCurrentMeetingByWorkgroupId(String workgroupId, URL callbackUrl) {
        try {
            this.triggerApiClient.stopCurrentMeetingByWorkgroupId(workgroupId, callbackUrl.toString());
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }
}
