package de.qaware.smartlabcore.workgroup.service;

import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.Workgroup;

import java.util.List;
import java.util.Optional;

public interface IWorkgroupManagementService {

    List<Workgroup> getWorkgroups();
    Optional<Workgroup> getWorkgroup(long workgroupId);
    List<IMeeting> getMeetingsOfWorkgroup(long workgroupId);
    Optional<IMeeting> getCurrentMeeting(long workgroupId);

    boolean createWorkgroup(Workgroup workgroup);

    void deleteWorkgroup(long workgroupId);

    boolean extendCurrentMeeting(long workgroupId, long extensionInMinutes);
}
