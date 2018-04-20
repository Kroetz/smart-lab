package de.qaware.smartlabcore.workgroup.repository;

import de.qaware.smartlabcommons.api.client.IMeetingManagementApiClient;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.sample.ISampleDataFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.net.MalformedURLException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class WorkgroupManagementRepositoryMock implements IWorkgroupManagementRepository {

    private final IMeetingManagementApiClient meetingManagementApiClient;
    private List<IWorkgroup> workgroups;

    public WorkgroupManagementRepositoryMock(
            IMeetingManagementApiClient meetingManagementApiClient,
            ISampleDataFactory coastGuardDataFactory,
            ISampleDataFactory forestRangersDataFactory,
            ISampleDataFactory fireFightersDataFactory
    ) throws MalformedURLException {
        this.meetingManagementApiClient = meetingManagementApiClient;
        this.workgroups = new ArrayList<>();
        this.workgroups.addAll(new ArrayList<>(coastGuardDataFactory.createWorkgroups().values()));
        this.workgroups.addAll(new ArrayList<>(forestRangersDataFactory.createWorkgroups().values()));
        this.workgroups.addAll(new ArrayList<>(fireFightersDataFactory.createWorkgroups().values()));
        sortWorkgroupsById();
    }

    private boolean exists(String workgroupId) {
        return workgroups.stream()
                .anyMatch(workgroup -> workgroup.getId().equals(workgroupId));
    }

    @Override
    public List<IWorkgroup> getWorkgroups() {
        return this.workgroups;
    }

    @Override
    public Optional<IWorkgroup> getWorkgroup(String workgroupId) {
        return workgroups.stream()
                .filter(workgroup -> workgroup.getId().equals(workgroupId))
                .findFirst();
    }

    @Override
    public boolean createWorkgroup(IWorkgroup workgroup) {
        return !exists(workgroup.getId()) && workgroups.add(workgroup);
    }

    @Override
    public boolean deleteWorkgroup(String workgroupId) {
        return workgroups.removeAll(workgroups.stream()
                .filter(workgroup -> workgroup.getId().equals(workgroupId))
                .collect(Collectors.toList()));
    }

    @Override
    public List<IMeeting> getMeetingsOfWorkgroup(String workgroupId) {
        return meetingManagementApiClient.getMeetings().stream()
                .filter(meeting -> meeting.getWorkgroupId().equals(workgroupId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<IMeeting> getCurrentMeeting(String workgroupId) {
        return getMeetingsOfWorkgroup(workgroupId).stream()
                .filter(meeting -> meeting.getStart().isBefore(Instant.now()) && meeting.getEnd().isAfter(Instant.now()))
                .findFirst();
    }

    @Override
    public boolean extendCurrentMeeting(String workgroupId, Duration extension) {
        return getCurrentMeeting(workgroupId)
                .map(meeting -> meetingManagementApiClient.extendMeeting(meeting.getId(), extension.toMinutes()))
                .orElse(false);
    }

    private void sortWorkgroupsById() {
        workgroups.sort(Comparator.comparing(IWorkgroup::getId));
    }
}
