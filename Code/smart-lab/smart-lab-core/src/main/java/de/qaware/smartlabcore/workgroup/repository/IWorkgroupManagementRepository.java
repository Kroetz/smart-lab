package de.qaware.smartlabcore.workgroup.repository;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.generic.repository.IEntityManagementRepository;
import de.qaware.smartlabcore.generic.result.ExtensionResult;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface IWorkgroupManagementRepository extends IEntityManagementRepository<IWorkgroup> {

    Set<IMeeting> getMeetingsOfWorkgroup(String workgroupId);
    Optional<IMeeting> getCurrentMeeting(String workgroupId);
    ExtensionResult extendCurrentMeeting(String workgroupId, Duration extension);
}
