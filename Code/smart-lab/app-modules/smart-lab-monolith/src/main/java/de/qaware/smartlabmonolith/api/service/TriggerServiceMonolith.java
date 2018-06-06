package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabapi.service.trigger.ITriggerService;
import de.qaware.smartlabcore.data.job.IJobInfo;
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
    public IJobInfo setUpCurrentMeetingByRoomId(String roomId) {
        return this.triggerController.setUpCurrentMeetingByRoomId(roomId, null).getBody();
    }

    @Override
    public IJobInfo setUpCurrentMeetingByRoomId(String roomId, URL callbackUrl) {
        return this.triggerController.setUpCurrentMeetingByRoomId(roomId, callbackUrl.toString()).getBody();
    }

    @Override
    public IJobInfo setUpCurrentMeetingByWorkgroupId(String workgroupId) {
        return this.triggerController.setUpCurrentMeetingByWorkgroupId(workgroupId, null).getBody();
    }

    @Override
    public IJobInfo setUpCurrentMeetingByWorkgroupId(String workgroupId, URL callbackUrl) {
        return this.triggerController.setUpCurrentMeetingByWorkgroupId(workgroupId, callbackUrl.toString()).getBody();
    }

    @Override
    public IJobInfo cleanUpCurrentMeetingByRoomId(String roomId) {
        return this.triggerController.cleanUpCurrentMeetingByRoomId(roomId, null).getBody();
    }

    @Override
    public IJobInfo cleanUpCurrentMeetingByRoomId(String roomId, URL callbackUrl) {
        return this.triggerController.cleanUpCurrentMeetingByRoomId(roomId, callbackUrl.toString()).getBody();
    }

    @Override
    public IJobInfo cleanUpCurrentMeetingByWorkgroupId(String workgroupId) {
        return this.triggerController.cleanUpCurrentMeetingByWorkgroupId(workgroupId, null).getBody();
    }

    @Override
    public IJobInfo cleanUpCurrentMeetingByWorkgroupId(String workgroupId, URL callbackUrl) {
        return this.triggerController.cleanUpCurrentMeetingByWorkgroupId(workgroupId, callbackUrl.toString()).getBody();
    }

    @Override
    public IJobInfo startCurrentMeetingByRoomId(String roomId) {
        return this.triggerController.startCurrentMeetingByRoomId(roomId, null).getBody();
    }

    @Override
    public IJobInfo startCurrentMeetingByRoomId(String roomId, URL callbackUrl) {
        return this.triggerController.startCurrentMeetingByRoomId(roomId, callbackUrl.toString()).getBody();
    }

    @Override
    public IJobInfo startCurrentMeetingByWorkgroupId(String workgroupId) {
        return this.triggerController.startCurrentMeetingByWorkgroupId(workgroupId, null).getBody();
    }

    @Override
    public IJobInfo startCurrentMeetingByWorkgroupId(String workgroupId, URL callbackUrl) {
        return this.triggerController.startCurrentMeetingByWorkgroupId(workgroupId, callbackUrl.toString()).getBody();
    }

    @Override
    public IJobInfo stopCurrentMeetingByRoomId(String roomId) {
        return this.triggerController.stopCurrentMeetingByRoomId(roomId, null).getBody();
    }

    @Override
    public IJobInfo stopCurrentMeetingByRoomId(String roomId, URL callbackUrl) {
        return this.triggerController.stopCurrentMeetingByRoomId(roomId, callbackUrl.toString()).getBody();
    }

    @Override
    public IJobInfo stopCurrentMeetingByWorkgroupId(String workgroupId) {
        return this.triggerController.stopCurrentMeetingByWorkgroupId(workgroupId, null).getBody();
    }

    @Override
    public IJobInfo stopCurrentMeetingByWorkgroupId(String workgroupId, URL callbackUrl) {
        return this.triggerController.stopCurrentMeetingByWorkgroupId(workgroupId, callbackUrl.toString()).getBody();
    }
}
