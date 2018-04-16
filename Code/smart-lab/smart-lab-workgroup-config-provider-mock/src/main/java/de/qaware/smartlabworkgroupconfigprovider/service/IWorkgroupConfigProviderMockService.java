package de.qaware.smartlabworkgroupconfigprovider.service;

import de.qaware.smartlabcommons.data.workgroup.Workgroup;

import java.util.List;
import java.util.Optional;

public interface IWorkgroupConfigProviderMockService {

    List<Workgroup> getWorkgroups();
    Optional<Workgroup> getWorkgroup(long workgroupId);
    boolean createWorkgroup(Workgroup workgroup);
    boolean deleteWorkgroup(long workgroupId);
}
