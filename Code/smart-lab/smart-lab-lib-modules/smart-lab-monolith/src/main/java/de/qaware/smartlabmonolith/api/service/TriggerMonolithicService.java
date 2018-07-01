package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabapi.service.IServiceBaseUrlGetter;
import de.qaware.smartlabapi.service.trigger.ITriggerService;
import de.qaware.smartlabcore.data.job.IJobInfo;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabtrigger.controller.TriggerController;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class TriggerMonolithicService implements ITriggerService {

    private final TriggerController triggerController;

    public TriggerMonolithicService(TriggerController triggerController) {
        this.triggerController = triggerController;
    }

    @Override
    public IJobInfo setUpCurrentMeetingByRoomId(RoomId roomId) {
        return this.triggerController.setUpCurrentMeetingByRoomId(roomId.getIdValue(), null).getBody();
    }

    @Override
    public IJobInfo setUpCurrentMeetingByRoomId(RoomId roomId, URL callbackUrl) {
        return this.triggerController.setUpCurrentMeetingByRoomId(roomId.getIdValue(), callbackUrl.toString()).getBody();
    }

    @Override
    public IJobInfo setUpCurrentMeetingByWorkgroupId(WorkgroupId workgroupId) {
        return this.triggerController.setUpCurrentMeetingByWorkgroupId(workgroupId.getIdValue(), null).getBody();
    }

    @Override
    public IJobInfo setUpCurrentMeetingByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl) {
        return this.triggerController.setUpCurrentMeetingByWorkgroupId(workgroupId.getIdValue(), callbackUrl.toString()).getBody();
    }

    @Override
    public IJobInfo cleanUpCurrentMeetingByRoomId(RoomId roomId) {
        return this.triggerController.cleanUpCurrentMeetingByRoomId(roomId.getIdValue(), null).getBody();
    }

    @Override
    public IJobInfo cleanUpCurrentMeetingByRoomId(RoomId roomId, URL callbackUrl) {
        return this.triggerController.cleanUpCurrentMeetingByRoomId(roomId.getIdValue(), callbackUrl.toString()).getBody();
    }

    @Override
    public IJobInfo cleanUpCurrentMeetingByWorkgroupId(WorkgroupId workgroupId) {
        return this.triggerController.cleanUpCurrentMeetingByWorkgroupId(workgroupId.getIdValue(), null).getBody();
    }

    @Override
    public IJobInfo cleanUpCurrentMeetingByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl) {
        return this.triggerController.cleanUpCurrentMeetingByWorkgroupId(workgroupId.getIdValue(), callbackUrl.toString()).getBody();
    }

    @Override
    public IJobInfo startCurrentMeetingByRoomId(RoomId roomId) {
        return this.triggerController.startCurrentMeetingByRoomId(roomId.getIdValue(), null).getBody();
    }

    @Override
    public IJobInfo startCurrentMeetingByRoomId(RoomId roomId, URL callbackUrl) {
        return this.triggerController.startCurrentMeetingByRoomId(roomId.getIdValue(), callbackUrl.toString()).getBody();
    }

    @Override
    public IJobInfo startCurrentMeetingByWorkgroupId(WorkgroupId workgroupId) {
        return this.triggerController.startCurrentMeetingByWorkgroupId(workgroupId.getIdValue(), null).getBody();
    }

    @Override
    public IJobInfo startCurrentMeetingByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl) {
        return this.triggerController.startCurrentMeetingByWorkgroupId(workgroupId.getIdValue(), callbackUrl.toString()).getBody();
    }

    @Override
    public IJobInfo stopCurrentMeetingByRoomId(RoomId roomId) {
        return this.triggerController.stopCurrentMeetingByRoomId(roomId.getIdValue(), null).getBody();
    }

    @Override
    public IJobInfo stopCurrentMeetingByRoomId(RoomId roomId, URL callbackUrl) {
        return this.triggerController.stopCurrentMeetingByRoomId(roomId.getIdValue(), callbackUrl.toString()).getBody();
    }

    @Override
    public IJobInfo stopCurrentMeetingByWorkgroupId(WorkgroupId workgroupId) {
        return this.triggerController.stopCurrentMeetingByWorkgroupId(workgroupId.getIdValue(), null).getBody();
    }

    @Override
    public IJobInfo stopCurrentMeetingByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl) {
        return this.triggerController.stopCurrentMeetingByWorkgroupId(workgroupId.getIdValue(), callbackUrl.toString()).getBody();
    }

    @Component
    // TODO: String literal
    @Qualifier("triggerServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MONOLITH)
    public static class BaseUrlGetter implements IServiceBaseUrlGetter {

        private final TriggerController.BaseUrlGetter triggerServiceBaseUrlGetter;

        public BaseUrlGetter(TriggerController.BaseUrlGetter triggerServiceBaseUrlGetter) {
            this.triggerServiceBaseUrlGetter = triggerServiceBaseUrlGetter;
        }

        @Override
        public URL getBaseUrl() {
            return this.triggerServiceBaseUrlGetter.getBaseUrl().getBody();
        }
    }
}
