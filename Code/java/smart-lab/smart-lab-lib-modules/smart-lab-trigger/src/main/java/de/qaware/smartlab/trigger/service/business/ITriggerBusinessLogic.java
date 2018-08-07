package de.qaware.smartlab.trigger.service.business;

import de.qaware.smartlab.core.data.job.IJobInfo;
import de.qaware.smartlab.core.data.meeting.IMeeting;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import org.springframework.lang.Nullable;

import java.net.URL;

public interface ITriggerBusinessLogic {

    IJobInfo setUpCurrentMeetingByLocationId(LocationId locationId, @Nullable URL callbackUrl);
    IJobInfo setUpMeeting(IMeeting meeting, @Nullable URL callbackUrl);
    IJobInfo setUpCurrentMeeting(ILocation location, @Nullable URL callbackUrl);
    IJobInfo setUpCurrentMeetingByWorkgroupId(WorkgroupId workgroupId, @Nullable URL callbackUrl);
    IJobInfo setUpCurrentMeeting(IWorkgroup workgroup, @Nullable URL callbackUrl);

    IJobInfo cleanUpCurrentMeetingByLocationId(LocationId locationId, @Nullable URL callbackUrl);
    IJobInfo cleanUpMeeting(IMeeting meeting, @Nullable URL callbackUrl);
    IJobInfo cleanUpCurrentMeeting(ILocation location, @Nullable URL callbackUrl);
    IJobInfo cleanUpCurrentMeetingByWorkgroupId(WorkgroupId workgroupId, @Nullable URL callbackUrl);
    IJobInfo cleanUpCurrentMeeting(IWorkgroup workgroup, @Nullable URL callbackUrl);

    IJobInfo startCurrentMeetingByLocationId(LocationId locationId, @Nullable URL callbackUrl);
    IJobInfo startMeeting(IMeeting meeting, @Nullable URL callbackUrl);
    IJobInfo startCurrentMeeting(ILocation location, @Nullable URL callbackUrl);
    IJobInfo startCurrentMeetingByWorkgroupId(WorkgroupId workgroupId, @Nullable URL callbackUrl);
    IJobInfo startCurrentMeeting(IWorkgroup workgroup, @Nullable URL callbackUrl);

    IJobInfo stopCurrentMeetingByLocationId(LocationId locationId, @Nullable URL callbackUrl);
    IJobInfo stopMeeting(IMeeting meeting, @Nullable URL callbackUrl);
    IJobInfo stopCurrentMeeting(ILocation location, @Nullable URL callbackUrl);
    IJobInfo stopCurrentMeetingByWorkgroupId(WorkgroupId workgroupId, @Nullable URL callbackUrl);
    IJobInfo stopCurrentMeeting(IWorkgroup workgroup, @Nullable URL callbackUrl);
}
