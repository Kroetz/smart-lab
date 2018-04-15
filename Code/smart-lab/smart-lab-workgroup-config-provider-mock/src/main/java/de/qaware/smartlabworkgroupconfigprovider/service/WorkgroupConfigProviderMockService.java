package de.qaware.smartlabworkgroupconfigprovider.service;

import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import lombok.val;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkgroupConfigProviderMockService implements IWorkgroupConfigProviderMockService {

    private List<Workgroup> workgroups;

    public WorkgroupConfigProviderMockService() throws MalformedURLException {
        this.workgroups = new ArrayList<>();

        val coastGuardMembers = new ArrayList<Long>();
        coastGuardMembers.add(0L);
        coastGuardMembers.add(1L);
        coastGuardMembers.add(2L);
        workgroups.add(Workgroup.builder()
                .id(0)
                .name("Coast Guard")
                .memberIds(coastGuardMembers)
                .knowledgeBase(new URL("http", "coast-guard.com", 80, "/wiki"))
                .codeRepository(new URL("http", "coast-guard.com", 80, "/git"))
                .build());

        val forestRangerMembers = new ArrayList<Long>();
        forestRangerMembers.add(3L);
        forestRangerMembers.add(4L);
        forestRangerMembers.add(5L);
        workgroups.add(Workgroup.builder()
                .id(1)
                .name("Forest Rangers")
                .memberIds(coastGuardMembers)
                .knowledgeBase(new URL("http", "forest-rangers.com", 80, "/wiki"))
                .codeRepository(new URL("http", "forest-rangers.com", 80, "/git"))
                .build());

        val fireFighterMembers = new ArrayList<Long>();
        fireFighterMembers.add(6L);
        fireFighterMembers.add(7L);
        fireFighterMembers.add(8L);
        workgroups.add(Workgroup.builder()
                .id(2)
                .name("Fire Fighters")
                .memberIds(coastGuardMembers)
                .knowledgeBase(new URL("http", "fire-fighters.com", 80, "/wiki"))
                .codeRepository(new URL("http", "fire-fighters.com", 80, "/git"))
                .build());

        sortWorkgroupsById();
    }

    @Override
    public boolean exists(long workgroupId) {
        return workgroups.stream()
                .anyMatch(workgroup -> workgroup.getId() == workgroupId);
    }

    @Override
    public List<Workgroup> getWorkgroups() {
        return this.workgroups;
    }

    @Override
    public Optional<Workgroup> getWorkgroup(long workgroupId) {
        return workgroups.stream()
                .filter(workgroup -> workgroup.getId() == workgroupId)
                .findFirst();
    }

    @Override
    public boolean createWorkgroup(Workgroup workgroup) {
        return !exists(workgroup.getId()) && workgroups.add(workgroup);
    }

    @Override
    public boolean deleteWorkgroup(long workgroupId) {
        return workgroups.removeAll(workgroups.stream()
                .filter(workgroup -> workgroup.getId() == workgroupId)
                .collect(Collectors.toList()));
    }

    private void sortWorkgroupsById() {
        workgroups.sort(Comparator.comparingLong(Workgroup::getId));
    }
}
