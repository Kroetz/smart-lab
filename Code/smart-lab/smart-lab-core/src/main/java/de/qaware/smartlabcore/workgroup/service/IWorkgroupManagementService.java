package de.qaware.smartlabcore.workgroup.service;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.generic.result.ExtensionResult;
import de.qaware.smartlabcore.generic.service.IEntityManagementService;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface IWorkgroupManagementService extends IEntityManagementService<IWorkgroup> {

    Set<IMeeting> getMeetingsOfWorkgroup(String workgroupId);
    Optional<IMeeting> getCurrentMeeting(String workgroupId);
    ExtensionResult extendCurrentMeeting(String workgroupId, Duration extension);
}
