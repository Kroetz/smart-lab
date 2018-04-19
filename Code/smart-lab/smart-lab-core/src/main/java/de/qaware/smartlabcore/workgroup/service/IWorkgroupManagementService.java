package de.qaware.smartlabcore.workgroup.service;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public interface IWorkgroupManagementService {

    List<IWorkgroup> getWorkgroups();
    Optional<IWorkgroup> getWorkgroup(long workgroupId);

    boolean createWorkgroup(IWorkgroup workgroup);

    boolean deleteWorkgroup(long workgroupId);

    List<IMeeting> getMeetingsOfWorkgroup(long workgroupId);

    Optional<IMeeting> getCurrentMeeting(long workgroupId);

    boolean extendCurrentMeeting(long workgroupId, Duration extension);
}
