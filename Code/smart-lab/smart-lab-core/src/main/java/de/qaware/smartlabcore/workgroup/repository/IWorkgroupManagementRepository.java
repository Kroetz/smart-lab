package de.qaware.smartlabcore.workgroup.repository;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.Workgroup;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public interface IWorkgroupManagementRepository {

    List<Workgroup> getWorkgroups();
    Optional<Workgroup> getWorkgroup(long workgroupId);

    boolean createWorkgroup(Workgroup workgroup);

    void deleteWorkgroup(long workgroupId);

    List<IMeeting> getMeetingsOfWorkgroup(long workgroupId);

    Optional<IMeeting> getCurrentMeeting(long workgroupId);

    boolean extendCurrentMeeting(long workgroupId, Duration extension);
}
