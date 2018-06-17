package de.qaware.smartlabworkgroup.business;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.result.ExtensionResult;
import de.qaware.smartlabcore.generic.business.IEntityManagementBusinessLogic;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface IWorkgroupManagementBusinessLogic extends IEntityManagementBusinessLogic<IWorkgroup, WorkgroupId> {

    Optional<Set<IMeeting>> getMeetingsOfWorkgroup(WorkgroupId workgroupId);
    Optional<IMeeting> getCurrentMeeting(WorkgroupId workgroupId);
    ExtensionResult extendCurrentMeeting(WorkgroupId workgroupId, Duration extension);
}
