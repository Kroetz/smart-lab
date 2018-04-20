package de.qaware.smartlabcore.workgroup.service;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public interface IWorkgroupManagementService {

    List<IWorkgroup> getWorkgroups();
    Optional<IWorkgroup> getWorkgroup(String workgroupId);

    boolean createWorkgroup(IWorkgroup workgroup);

    boolean deleteWorkgroup(String workgroupId);

    List<IMeeting> getMeetingsOfWorkgroup(String workgroupId);

    Optional<IMeeting> getCurrentMeeting(String workgroupId);

    boolean extendCurrentMeeting(String workgroupId, Duration extension);
}
