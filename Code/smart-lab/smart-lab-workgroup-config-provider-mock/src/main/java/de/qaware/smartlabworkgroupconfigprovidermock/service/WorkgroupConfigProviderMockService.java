package de.qaware.smartlabworkgroupconfigprovidermock.service;

import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.sample.ISampleDataFactory;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkgroupConfigProviderMockService implements IWorkgroupConfigProviderMockService {

    private List<IWorkgroup> workgroups;

    public WorkgroupConfigProviderMockService(
            ISampleDataFactory coastGuardDataFactory,
            ISampleDataFactory forestRangersDataFactory,
            ISampleDataFactory fireFightersDataFactory
    ) throws MalformedURLException {
        this.workgroups = new ArrayList<>();
        this.workgroups.addAll(coastGuardDataFactory.createWorkgroups());
        this.workgroups.addAll(forestRangersDataFactory.createWorkgroups());
        this.workgroups.addAll(fireFightersDataFactory.createWorkgroups());
        sortWorkgroupsById();
    }

    private boolean exists(long workgroupId) {
        return workgroups.stream()
                .anyMatch(workgroup -> workgroup.getId() == workgroupId);
    }

    @Override
    public List<IWorkgroup> getWorkgroups() {
        return this.workgroups;
    }

    @Override
    public Optional<IWorkgroup> getWorkgroup(long workgroupId) {
        return workgroups.stream()
                .filter(workgroup -> workgroup.getId() == workgroupId)
                .findFirst();
    }

    @Override
    public boolean createWorkgroup(IWorkgroup workgroup) {
        return !exists(workgroup.getId()) && workgroups.add(workgroup);
    }

    @Override
    public boolean deleteWorkgroup(long workgroupId) {
        return workgroups.removeAll(workgroups.stream()
                .filter(workgroup -> workgroup.getId() == workgroupId)
                .collect(Collectors.toList()));
    }

    private void sortWorkgroupsById() {
        workgroups.sort(Comparator.comparingLong(IWorkgroup::getId));
    }
}
