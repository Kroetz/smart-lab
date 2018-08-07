package de.qaware.smartlab.trigger.service.business;

import de.qaware.smartlab.core.data.job.IJobInfo;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import org.springframework.lang.Nullable;

import java.net.URL;

public interface ITriggerBusinessLogic {

    IJobInfo setUpCurrentEventByLocationId(LocationId locationId, @Nullable URL callbackUrl);
    IJobInfo setUpEvent(IEvent event, @Nullable URL callbackUrl);
    IJobInfo setUpCurrentEvent(ILocation location, @Nullable URL callbackUrl);
    IJobInfo setUpCurrentEventByWorkgroupId(WorkgroupId workgroupId, @Nullable URL callbackUrl);
    IJobInfo setUpCurrentEvent(IWorkgroup workgroup, @Nullable URL callbackUrl);

    IJobInfo cleanUpCurrentEventByLocationId(LocationId locationId, @Nullable URL callbackUrl);
    IJobInfo cleanUpEvent(IEvent event, @Nullable URL callbackUrl);
    IJobInfo cleanUpCurrentEvent(ILocation location, @Nullable URL callbackUrl);
    IJobInfo cleanUpCurrentEventByWorkgroupId(WorkgroupId workgroupId, @Nullable URL callbackUrl);
    IJobInfo cleanUpCurrentEvent(IWorkgroup workgroup, @Nullable URL callbackUrl);

    IJobInfo startCurrentEventByLocationId(LocationId locationId, @Nullable URL callbackUrl);
    IJobInfo startEvent(IEvent event, @Nullable URL callbackUrl);
    IJobInfo startCurrentEvent(ILocation location, @Nullable URL callbackUrl);
    IJobInfo startCurrentEventByWorkgroupId(WorkgroupId workgroupId, @Nullable URL callbackUrl);
    IJobInfo startCurrentEvent(IWorkgroup workgroup, @Nullable URL callbackUrl);

    IJobInfo stopCurrentEventByLocationId(LocationId locationId, @Nullable URL callbackUrl);
    IJobInfo stopEvent(IEvent event, @Nullable URL callbackUrl);
    IJobInfo stopCurrentEvent(ILocation location, @Nullable URL callbackUrl);
    IJobInfo stopCurrentEventByWorkgroupId(WorkgroupId workgroupId, @Nullable URL callbackUrl);
    IJobInfo stopCurrentEvent(IWorkgroup workgroup, @Nullable URL callbackUrl);
}
