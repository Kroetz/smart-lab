package de.qaware.smartlab.monolith.service.connector.trigger;

import de.qaware.smartlab.api.service.connector.trigger.ITriggerService;
import de.qaware.smartlab.core.configuration.ModularityConfiguration;
import de.qaware.smartlab.core.data.job.IJobInfo;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.service.url.IServiceBaseUrlGetter;
import de.qaware.smartlab.monolith.service.url.AbstractMonolithicBaseUrlGetter;
import de.qaware.smartlab.trigger.service.controller.TriggerController;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@ConditionalOnProperty(
        prefix = ModularityConfiguration.Properties.PREFIX,
        name = ModularityConfiguration.Properties.MODULARITY,
        havingValue = ModularityConfiguration.Properties.MONOLITH)
public class TriggerMonolithicServiceConnector implements ITriggerService {

    private final TriggerController triggerController;

    public TriggerMonolithicServiceConnector(TriggerController triggerController) {
        this.triggerController = triggerController;
    }

    @Override
    public IJobInfo setUpCurrentEventByLocationId(LocationId locationId) {
        return this.triggerController.setUpCurrentEventByLocationId(locationId.getIdValue(), null).getBody();
    }

    @Override
    public IJobInfo setUpCurrentEventByLocationId(LocationId locationId, URL callbackUrl) {
        return this.triggerController.setUpCurrentEventByLocationId(locationId.getIdValue(), callbackUrl.toString()).getBody();
    }

    @Override
    public IJobInfo setUpCurrentEventByWorkgroupId(WorkgroupId workgroupId) {
        return this.triggerController.setUpCurrentEventByWorkgroupId(workgroupId.getIdValue(), null).getBody();
    }

    @Override
    public IJobInfo setUpCurrentEventByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl) {
        return this.triggerController.setUpCurrentEventByWorkgroupId(workgroupId.getIdValue(), callbackUrl.toString()).getBody();
    }

    @Override
    public IJobInfo cleanUpCurrentEventByLocationId(LocationId locationId) {
        return this.triggerController.cleanUpCurrentEventByLocationId(locationId.getIdValue(), null).getBody();
    }

    @Override
    public IJobInfo cleanUpCurrentEventByLocationId(LocationId locationId, URL callbackUrl) {
        return this.triggerController.cleanUpCurrentEventByLocationId(locationId.getIdValue(), callbackUrl.toString()).getBody();
    }

    @Override
    public IJobInfo cleanUpCurrentEventByWorkgroupId(WorkgroupId workgroupId) {
        return this.triggerController.cleanUpCurrentEventByWorkgroupId(workgroupId.getIdValue(), null).getBody();
    }

    @Override
    public IJobInfo cleanUpCurrentEventByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl) {
        return this.triggerController.cleanUpCurrentEventByWorkgroupId(workgroupId.getIdValue(), callbackUrl.toString()).getBody();
    }

    @Override
    public IJobInfo startCurrentEventByLocationId(LocationId locationId) {
        return this.triggerController.startCurrentEventByLocationId(locationId.getIdValue(), null).getBody();
    }

    @Override
    public IJobInfo startCurrentEventByLocationId(LocationId locationId, URL callbackUrl) {
        return this.triggerController.startCurrentEventByLocationId(locationId.getIdValue(), callbackUrl.toString()).getBody();
    }

    @Override
    public IJobInfo startCurrentEventByWorkgroupId(WorkgroupId workgroupId) {
        return this.triggerController.startCurrentEventByWorkgroupId(workgroupId.getIdValue(), null).getBody();
    }

    @Override
    public IJobInfo startCurrentEventByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl) {
        return this.triggerController.startCurrentEventByWorkgroupId(workgroupId.getIdValue(), callbackUrl.toString()).getBody();
    }

    @Override
    public IJobInfo stopCurrentEventByLocationId(LocationId locationId) {
        return this.triggerController.stopCurrentEventByLocationId(locationId.getIdValue(), null).getBody();
    }

    @Override
    public IJobInfo stopCurrentEventByLocationId(LocationId locationId, URL callbackUrl) {
        return this.triggerController.stopCurrentEventByLocationId(locationId.getIdValue(), callbackUrl.toString()).getBody();
    }

    @Override
    public IJobInfo stopCurrentEventByWorkgroupId(WorkgroupId workgroupId) {
        return this.triggerController.stopCurrentEventByWorkgroupId(workgroupId.getIdValue(), null).getBody();
    }

    @Override
    public IJobInfo stopCurrentEventByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl) {
        return this.triggerController.stopCurrentEventByWorkgroupId(workgroupId.getIdValue(), callbackUrl.toString()).getBody();
    }

    @Component
    @Qualifier(IServiceBaseUrlGetter.QUALIFIER_TRIGGER_SERVICE_BASE_URL_GETTER)
    @ConditionalOnProperty(
            prefix = ModularityConfiguration.Properties.PREFIX,
            name = ModularityConfiguration.Properties.MODULARITY,
            havingValue = ModularityConfiguration.Properties.MONOLITH)
    public static class BaseUrlGetter extends AbstractMonolithicBaseUrlGetter {

        public BaseUrlGetter(TriggerController.BaseUrlController baseUrlController) {
            super(baseUrlController);
        }
    }
}
