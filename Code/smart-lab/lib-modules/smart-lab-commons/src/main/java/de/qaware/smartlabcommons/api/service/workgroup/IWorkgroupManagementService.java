package de.qaware.smartlabcommons.api.service.workgroup;

import de.qaware.smartlabcommons.api.service.generic.IEntityManagementService;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;

import java.time.Duration;
import java.util.Set;

public interface IWorkgroupManagementService extends IEntityManagementService<IWorkgroup> {

    Set<IMeeting> getMeetingsOfWorkgroup(String workgroupId);
    IMeeting getCurrentMeeting(String workgroupId);
    void extendCurrentMeeting(String workgroupId, Duration extension);
}
