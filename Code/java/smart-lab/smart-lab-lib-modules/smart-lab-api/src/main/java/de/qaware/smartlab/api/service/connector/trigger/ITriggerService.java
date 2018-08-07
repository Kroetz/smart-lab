package de.qaware.smartlab.api.service.connector.trigger;

import de.qaware.smartlab.core.data.job.IJobInfo;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;

import java.net.URL;

public interface ITriggerService {

    IJobInfo setUpCurrentEventByLocationId(LocationId locationId);
    IJobInfo setUpCurrentEventByLocationId(LocationId locationId, URL callbackUrl);
    IJobInfo setUpCurrentEventByWorkgroupId(WorkgroupId workgroupId);
    IJobInfo setUpCurrentEventByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl);

    IJobInfo cleanUpCurrentEventByLocationId(LocationId locationId);
    IJobInfo cleanUpCurrentEventByLocationId(LocationId locationId, URL callbackUrl);
    IJobInfo cleanUpCurrentEventByWorkgroupId(WorkgroupId workgroupId);
    IJobInfo cleanUpCurrentEventByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl);

    IJobInfo startCurrentEventByLocationId(LocationId locationId);
    IJobInfo startCurrentEventByLocationId(LocationId locationId, URL callbackUrl);
    IJobInfo startCurrentEventByWorkgroupId(WorkgroupId workgroupId);
    IJobInfo startCurrentEventByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl);

    IJobInfo stopCurrentEventByLocationId(LocationId locationId);
    IJobInfo stopCurrentEventByLocationId(LocationId locationId, URL callbackUrl);
    IJobInfo stopCurrentEventByWorkgroupId(WorkgroupId workgroupId);
    IJobInfo stopCurrentEventByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl);
}
