package de.qaware.smartlabapi.service.trigger;

import de.qaware.smartlabapi.client.ITriggerApiClient;
import de.qaware.smartlabcore.data.job.IJobInfo;
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
    public IJobInfo setUpCurrentMeetingByRoomId(String roomId) {
        try {
            return this.triggerApiClient.setUpCurrentMeetingByRoomId(roomId, null).getBody();
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
    public IJobInfo setUpCurrentMeetingByRoomId(String roomId, URL callbackUrl) {
        try {
            return this.triggerApiClient.setUpCurrentMeetingByRoomId(roomId, callbackUrl.toString()).getBody();
        }
        catch(Exception e) {
            throw e;
        }
    }

    @Override
    public IJobInfo setUpCurrentMeetingByWorkgroupId(String workgroupId) {
        try {
            return this.triggerApiClient.setUpCurrentMeetingByWorkgroupId(workgroupId, null).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo setUpCurrentMeetingByWorkgroupId(String workgroupId, URL callbackUrl) {
        try {
            return this.triggerApiClient.setUpCurrentMeetingByWorkgroupId(workgroupId, callbackUrl.toString()).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo cleanUpCurrentMeetingByRoomId(String roomId) {
        try {
            return this.triggerApiClient.cleanUpCurrentMeetingByRoomId(roomId, null).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo cleanUpCurrentMeetingByRoomId(String roomId, URL callbackUrl) {
        try {
            return this.triggerApiClient.cleanUpCurrentMeetingByRoomId(roomId, callbackUrl.toString()).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo cleanUpCurrentMeetingByWorkgroupId(String workgroupId) {
        try {
            return this.triggerApiClient.cleanUpCurrentMeetingByWorkgroupId(workgroupId, null).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo cleanUpCurrentMeetingByWorkgroupId(String workgroupId, URL callbackUrl) {
        try {
            return this.triggerApiClient.cleanUpCurrentMeetingByWorkgroupId(workgroupId, callbackUrl.toString()).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo startCurrentMeetingByRoomId(String roomId) {
        try {
            return this.triggerApiClient.startCurrentMeetingByRoomId(roomId, null).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo startCurrentMeetingByRoomId(String roomId, URL callbackUrl) {
        try {
            return this.triggerApiClient.startCurrentMeetingByRoomId(roomId, callbackUrl.toString()).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo startCurrentMeetingByWorkgroupId(String workgroupId) {
        try {
            return this.triggerApiClient.startCurrentMeetingByWorkgroupId(workgroupId, null).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo startCurrentMeetingByWorkgroupId(String workgroupId, URL callbackUrl) {
        try {
            return this.triggerApiClient.startCurrentMeetingByWorkgroupId(workgroupId, callbackUrl.toString()).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo stopCurrentMeetingByRoomId(String roomId) {
        try {
            return this.triggerApiClient.stopCurrentMeetingByRoomId(roomId, null).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo stopCurrentMeetingByRoomId(String roomId, URL callbackUrl) {
        try {
            return this.triggerApiClient.stopCurrentMeetingByRoomId(roomId, callbackUrl.toString()).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo stopCurrentMeetingByWorkgroupId(String workgroupId) {
        try {
            return this.triggerApiClient.stopCurrentMeetingByWorkgroupId(workgroupId, null).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public IJobInfo stopCurrentMeetingByWorkgroupId(String workgroupId, URL callbackUrl) {
        try {
            return this.triggerApiClient.stopCurrentMeetingByWorkgroupId(workgroupId, callbackUrl.toString()).getBody();
        }
        catch(RetryableException e) {
            throw e;
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }
}
