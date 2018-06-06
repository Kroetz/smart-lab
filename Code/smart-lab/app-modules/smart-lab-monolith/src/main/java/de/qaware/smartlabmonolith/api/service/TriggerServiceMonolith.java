package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabapi.service.trigger.ITriggerService;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabtrigger.controller.TriggerController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class TriggerServiceMonolith implements ITriggerService {

    private final TriggerController triggerController;

    public TriggerServiceMonolith(TriggerController triggerController) {
        this.triggerController = triggerController;
    }

    @Override
    public void setUpCurrentMeetingByRoomId(String roomId) {
        this.triggerController.setUpCurrentMeetingByRoomId(roomId, null);
    }

    @Override
    public void setUpCurrentMeetingByRoomId(String roomId, URL callbackUrl) {
        this.triggerController.setUpCurrentMeetingByRoomId(roomId, callbackUrl.toString());
    }

    @Override
    public void setUpCurrentMeetingByWorkgroupId(String workgroupId) {
        this.triggerController.setUpCurrentMeetingByWorkgroupId(workgroupId, null);
    }

    @Override
    public void setUpCurrentMeetingByWorkgroupId(String workgroupId, URL callbackUrl) {
        this.triggerController.setUpCurrentMeetingByWorkgroupId(workgroupId, callbackUrl.toString());
    }

    @Override
    public void cleanUpCurrentMeetingByRoomId(String roomId) {
        this.triggerController.cleanUpCurrentMeetingByRoomId(roomId, null);
    }

    @Override
    public void cleanUpCurrentMeetingByRoomId(String roomId, URL callbackUrl) {
        this.triggerController.cleanUpCurrentMeetingByRoomId(roomId, callbackUrl.toString());

    }

    @Override
    public void cleanUpCurrentMeetingByWorkgroupId(String workgroupId) {
        this.triggerController.cleanUpCurrentMeetingByWorkgroupId(workgroupId, null);
    }

    @Override
    public void cleanUpCurrentMeetingByWorkgroupId(String workgroupId, URL callbackUrl) {
        this.triggerController.cleanUpCurrentMeetingByWorkgroupId(workgroupId, callbackUrl.toString());
    }

    @Override
    public void startCurrentMeetingByRoomId(String roomId) {
        this.triggerController.startCurrentMeetingByRoomId(roomId, null);
    }

    @Override
    public void startCurrentMeetingByRoomId(String roomId, URL callbackUrl) {
        this.triggerController.startCurrentMeetingByRoomId(roomId, callbackUrl.toString());
    }

    @Override
    public void startCurrentMeetingByWorkgroupId(String workgroupId) {
        this.triggerController.startCurrentMeetingByWorkgroupId(workgroupId, null);
    }

    @Override
    public void startCurrentMeetingByWorkgroupId(String workgroupId, URL callbackUrl) {
        this.triggerController.startCurrentMeetingByWorkgroupId(workgroupId, callbackUrl.toString());
    }

    @Override
    public void stopCurrentMeetingByRoomId(String roomId) {
        this.triggerController.stopCurrentMeetingByRoomId(roomId, null);
    }

    @Override
    public void stopCurrentMeetingByRoomId(String roomId, URL callbackUrl) {
        this.triggerController.stopCurrentMeetingByRoomId(roomId, callbackUrl.toString());
    }

    @Override
    public void stopCurrentMeetingByWorkgroupId(String workgroupId) {
        this.triggerController.stopCurrentMeetingByWorkgroupId(workgroupId, null);
    }

    @Override
    public void stopCurrentMeetingByWorkgroupId(String workgroupId, URL callbackUrl) {
        this.triggerController.stopCurrentMeetingByWorkgroupId(workgroupId, callbackUrl.toString());
    }
}
