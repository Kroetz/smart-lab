package de.qaware.smartlabcore.workgroup.business;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcommons.result.ExtensionResult;
import de.qaware.smartlabcore.generic.business.IEntityManagementBusinessLogic;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface IWorkgroupManagementBusinessLogic extends IEntityManagementBusinessLogic<IWorkgroup> {

    Optional<Set<IMeeting>> getMeetingsOfWorkgroup(String workgroupId);
    Optional<IMeeting> getCurrentMeeting(String workgroupId);
    ExtensionResult extendCurrentMeeting(String workgroupId, Duration extension);
}
