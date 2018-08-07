package de.qaware.smartlab.api.service.connector.trigger;

import de.qaware.smartlab.core.data.job.IJobInfo;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;

import java.net.URL;

public interface ITriggerService {

    IJobInfo setUpCurrentMeetingByLocationId(LocationId locationId);
    IJobInfo setUpCurrentMeetingByLocationId(LocationId locationId, URL callbackUrl);
    IJobInfo setUpCurrentMeetingByWorkgroupId(WorkgroupId workgroupId);
    IJobInfo setUpCurrentMeetingByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl);

    IJobInfo cleanUpCurrentMeetingByLocationId(LocationId locationId);
    IJobInfo cleanUpCurrentMeetingByLocationId(LocationId locationId, URL callbackUrl);
    IJobInfo cleanUpCurrentMeetingByWorkgroupId(WorkgroupId workgroupId);
    IJobInfo cleanUpCurrentMeetingByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl);

    IJobInfo startCurrentMeetingByLocationId(LocationId locationId);
    IJobInfo startCurrentMeetingByLocationId(LocationId locationId, URL callbackUrl);
    IJobInfo startCurrentMeetingByWorkgroupId(WorkgroupId workgroupId);
    IJobInfo startCurrentMeetingByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl);

    IJobInfo stopCurrentMeetingByLocationId(LocationId locationId);
    IJobInfo stopCurrentMeetingByLocationId(LocationId locationId, URL callbackUrl);
    IJobInfo stopCurrentMeetingByWorkgroupId(WorkgroupId workgroupId);
    IJobInfo stopCurrentMeetingByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl);
}
