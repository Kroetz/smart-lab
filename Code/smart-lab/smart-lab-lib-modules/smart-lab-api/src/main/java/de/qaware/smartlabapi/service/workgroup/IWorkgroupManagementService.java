package de.qaware.smartlabapi.service.workgroup;

import de.qaware.smartlabapi.service.generic.IEntityManagementService;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;

import java.time.Duration;
import java.util.Set;

public interface IWorkgroupManagementService extends IEntityManagementService<IWorkgroup> {

    Set<IMeeting> getMeetingsOfWorkgroup(String workgroupId);
    IMeeting getCurrentMeeting(String workgroupId);
    void extendCurrentMeeting(String workgroupId, Duration extension);
}
