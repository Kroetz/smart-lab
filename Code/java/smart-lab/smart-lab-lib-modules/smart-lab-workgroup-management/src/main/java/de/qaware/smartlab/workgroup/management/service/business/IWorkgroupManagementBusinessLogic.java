package de.qaware.smartlab.workgroup.management.service.business;

import de.qaware.smartlab.core.data.meeting.IMeeting;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.result.ExtensionResult;
import de.qaware.smartlab.core.service.business.IBasicEntityManagementBusinessLogic;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface IWorkgroupManagementBusinessLogic extends IBasicEntityManagementBusinessLogic<IWorkgroup, WorkgroupId> {

    Optional<Set<IMeeting>> getMeetingsOfWorkgroup(WorkgroupId workgroupId);
    Optional<IMeeting> getCurrentMeeting(WorkgroupId workgroupId);
    ExtensionResult extendCurrentMeeting(WorkgroupId workgroupId, Duration extension);
}
