package de.qaware.smartlabworkgroup.service.repository;

import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.result.ExtensionResult;
import de.qaware.smartlabcore.generic.repository.IBasicEntityManagementRepository;
import lombok.NonNull;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface IWorkgroupManagementRepository extends IBasicEntityManagementRepository<IWorkgroup, WorkgroupId> {

    Set<IMeeting> getMeetingsOfWorkgroup(@NonNull IWorkgroup workgroup);
    Optional<IMeeting> getCurrentMeeting(@NonNull IWorkgroup workgroup);
    ExtensionResult extendCurrentMeeting(@NonNull IWorkgroup workgroup, Duration extension);
}
