package de.qaware.smartlabworkgroup.repository;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcommons.result.ExtensionResult;
import de.qaware.smartlabcore.generic.repository.IEntityManagementRepository;
import lombok.NonNull;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface IWorkgroupManagementRepository extends IEntityManagementRepository<IWorkgroup> {

    Set<IMeeting> getMeetingsOfWorkgroup(@NonNull IWorkgroup workgroup);
    Optional<IMeeting> getCurrentMeeting(@NonNull IWorkgroup workgroup);
    ExtensionResult extendCurrentMeeting(@NonNull IWorkgroup workgroup, Duration extension);
}
