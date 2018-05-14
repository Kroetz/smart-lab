package de.qaware.smartlabmonolith.service;

import de.qaware.smartlabcommons.api.service.trigger.ITriggerService;
import de.qaware.smartlabcommons.miscellaneous.Constants;
import de.qaware.smartlabtrigger.controller.TriggerController;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(Constants.PROFILE_NAME_MONOLITH)
public class TriggerServiceMonolith implements ITriggerService {

    private final TriggerController triggerController;

    public TriggerServiceMonolith(TriggerController triggerController) {
        this.triggerController = triggerController;
    }

    @Override
    public void setUpCurrentMeetingByRoomId(String roomId) {
        this.triggerController.setUpCurrentMeetingByRoomId(roomId);
    }

    @Override
    public void setUpCurrentMeetingByWorkgroupId(String workgroupId) {
        this.triggerController.setUpCurrentMeetingByWorkgroupId(workgroupId);
    }

    @Override
    public void cleanUpCurrentMeetingByRoomId(String roomId) {
        this.triggerController.cleanUpCurrentMeetingByRoomId(roomId);
    }

    @Override
    public void cleanUpCurrentMeetingByWorkgroupId(String workgroupId) {
        this.triggerController.cleanUpCurrentMeetingByWorkgroupId(workgroupId);
    }

    @Override
    public void startCurrentMeetingByRoomId(String roomId) {
        this.triggerController.startCurrentMeetingByRoomId(roomId);
    }

    @Override
    public void startCurrentMeetingByWorkgroupId(String workgroupId) {
        this.triggerController.startCurrentMeetingByWorkgroupId(workgroupId);
    }

    @Override
    public void stopCurrentMeetingByRoomId(String roomId) {
        this.triggerController.stopCurrentMeetingByRoomId(roomId);
    }

    @Override
    public void stopCurrentMeetingByWorkgroupId(String workgroupId) {
        this.triggerController.stopCurrentMeetingByWorkgroupId(workgroupId);
    }
}
