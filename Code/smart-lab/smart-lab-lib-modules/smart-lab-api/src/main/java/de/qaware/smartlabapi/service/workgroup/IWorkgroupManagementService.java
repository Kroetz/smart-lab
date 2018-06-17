package de.qaware.smartlabapi.service.workgroup;

import de.qaware.smartlabapi.service.generic.IBasicEntityManagementService;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;

import java.time.Duration;
import java.util.Set;

public interface IWorkgroupManagementService extends IBasicEntityManagementService<IWorkgroup, WorkgroupId> {

    Set<IMeeting> getMeetingsOfWorkgroup(WorkgroupId workgroupId);
    IMeeting getCurrentMeeting(WorkgroupId workgroupId);
    void extendCurrentMeeting(WorkgroupId workgroupId, Duration extension);
}
