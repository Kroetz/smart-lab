package de.qaware.smartlabcore.workgroup.repository;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.generic.result.CreationResult;
import de.qaware.smartlabcore.generic.result.DeletionResult;
import de.qaware.smartlabcore.generic.result.ExtensionResult;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public interface IWorkgroupManagementRepository {

    List<IWorkgroup> getWorkgroups();
    Optional<IWorkgroup> getWorkgroup(String workgroupId);

    CreationResult createWorkgroup(IWorkgroup workgroup);

    DeletionResult deleteWorkgroup(String workgroupId);

    List<IMeeting> getMeetingsOfWorkgroup(String workgroupId);

    Optional<IMeeting> getCurrentMeeting(String workgroupId);

    ExtensionResult extendCurrentMeeting(String workgroupId, Duration extension);
}
