package de.qaware.smartlab.monolith.service.connector.trigger;

import de.qaware.smartlab.api.service.connector.trigger.ITriggerService;
import de.qaware.smartlab.core.data.job.IJobInfo;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.miscellaneous.Property;
import de.qaware.smartlab.core.service.url.AbstractMonolithicBaseUrlGetter;
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
    public IJobInfo setUpCurrentMeetingByLocationId(LocationId locationId) {
        return this.triggerController.setUpCurrentMeetingByLocationId(locationId.getIdValue(), null).getBody();
    }

    @Override
    public IJobInfo setUpCurrentMeetingByLocationId(LocationId locationId, URL callbackUrl) {
        return this.triggerController.setUpCurrentMeetingByLocationId(locationId.getIdValue(), callbackUrl.toString()).getBody();
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
    public IJobInfo cleanUpCurrentMeetingByLocationId(LocationId locationId) {
        return this.triggerController.cleanUpCurrentMeetingByLocationId(locationId.getIdValue(), null).getBody();
    }

    @Override
    public IJobInfo cleanUpCurrentMeetingByLocationId(LocationId locationId, URL callbackUrl) {
        return this.triggerController.cleanUpCurrentMeetingByLocationId(locationId.getIdValue(), callbackUrl.toString()).getBody();
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
    public IJobInfo startCurrentMeetingByLocationId(LocationId locationId) {
        return this.triggerController.startCurrentMeetingByLocationId(locationId.getIdValue(), null).getBody();
    }

    @Override
    public IJobInfo startCurrentMeetingByLocationId(LocationId locationId, URL callbackUrl) {
        return this.triggerController.startCurrentMeetingByLocationId(locationId.getIdValue(), callbackUrl.toString()).getBody();
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
    public IJobInfo stopCurrentMeetingByLocationId(LocationId locationId) {
        return this.triggerController.stopCurrentMeetingByLocationId(locationId.getIdValue(), null).getBody();
    }

    @Override
    public IJobInfo stopCurrentMeetingByLocationId(LocationId locationId, URL callbackUrl) {
        return this.triggerController.stopCurrentMeetingByLocationId(locationId.getIdValue(), callbackUrl.toString()).getBody();
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
