package de.qaware.smartlabcore.workgroup.service;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.generic.result.CreationResult;
import de.qaware.smartlabcore.generic.result.DeletionResult;
import de.qaware.smartlabcore.generic.result.ExtensionResult;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface IWorkgroupManagementService {

    Set<IWorkgroup> getWorkgroups();
    Optional<IWorkgroup> getWorkgroup(String workgroupId);

    CreationResult createWorkgroup(IWorkgroup workgroup);

    DeletionResult deleteWorkgroup(String workgroupId);

    Set<IMeeting> getMeetingsOfWorkgroup(String workgroupId);

    Optional<IMeeting> getCurrentMeeting(String workgroupId);

    ExtensionResult extendCurrentMeeting(String workgroupId, Duration extension);
}
