package de.qaware.smartlabcommons.api.service.trigger;

import de.qaware.smartlabcommons.api.client.ITriggerApiClient;
import de.qaware.smartlabcommons.exception.UnknownErrorException;
import feign.FeignException;
import org.springframework.stereotype.Component;

@Component
public class TriggerService implements ITriggerService {

    private final ITriggerApiClient triggerApiClient;

    public TriggerService(ITriggerApiClient triggerApiClient) {
        this.triggerApiClient = triggerApiClient;
    }

    @Override
    public void setUpCurrentMeetingByRoomId(String roomId) {
        try {
            this.triggerApiClient.setUpCurrentMeetingByRoomId(roomId);
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public void setUpCurrentMeetingByWorkgroupId(String workgroupId) {
        try {
            this.triggerApiClient.setUpCurrentMeetingByWorkgroupId(workgroupId);
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
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public void cleanUpCurrentMeetingByWorkgroupId(String workgroupId) {
        try {
            this.triggerApiClient.cleanUpCurrentMeetingByWorkgroupId(workgroupId);
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
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public void startCurrentMeetingByWorkgroupId(String workgroupId) {
        try {
            this.triggerApiClient.startCurrentMeetingByWorkgroupId(workgroupId);
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
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }

    @Override
    public void stopCurrentMeetingByWorkgroupId(String workgroupId) {
        try {
            this.triggerApiClient.stopCurrentMeetingByWorkgroupId(workgroupId);
        }
        catch(FeignException e) {
            throw new UnknownErrorException();
        }
    }
}
