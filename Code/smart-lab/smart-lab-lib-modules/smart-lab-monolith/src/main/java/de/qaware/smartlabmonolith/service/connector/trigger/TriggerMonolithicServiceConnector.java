package de.qaware.smartlabmonolith.service.connector.trigger;

import de.qaware.smartlabapi.service.connector.trigger.ITriggerService;
import de.qaware.smartlabcore.data.job.IJobInfo;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabcore.service.url.AbstractMonolithicBaseUrlGetter;
import de.qaware.smartlabtrigger.service.controller.TriggerController;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class TriggerMonolithicServiceConnector implements ITriggerService {

    private final TriggerController triggerController;

    public TriggerMonolithicServiceConnector(TriggerController triggerController) {
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
    public static class BaseUrlGetter extends AbstractMonolithicBaseUrlGetter {

        public BaseUrlGetter(TriggerController.BaseUrlController baseUrlController) {
            super(baseUrlController);
        }
    }
}
