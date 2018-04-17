package de.qaware.smartlabworkgroupconfigprovidermock.service;

import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;

import java.util.List;
import java.util.Optional;

public interface IWorkgroupConfigProviderMockService {

    List<IWorkgroup> getWorkgroups();
    Optional<IWorkgroup> getWorkgroup(long workgroupId);
    boolean createWorkgroup(IWorkgroup workgroup);
    boolean deleteWorkgroup(long workgroupId);
}
