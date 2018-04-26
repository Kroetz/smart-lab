package de.qaware.smartlabcore.workgroup.repository;

import de.qaware.smartlabcommons.api.client.IMeetingManagementApiClient;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.sample.provider.ISampleDataProvider;
import de.qaware.smartlabcore.generic.result.CreationResult;
import de.qaware.smartlabcore.generic.result.DeletionResult;
import de.qaware.smartlabcore.generic.result.ExtensionResult;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class WorkgroupManagementRepositoryMock implements IWorkgroupManagementRepository {

    private final IMeetingManagementApiClient meetingManagementApiClient;
    private Set<IWorkgroup> workgroups;

    public WorkgroupManagementRepositoryMock(
            IMeetingManagementApiClient meetingManagementApiClient,
            ISampleDataProvider sampleDataProvider) {
        this.meetingManagementApiClient = meetingManagementApiClient;
        this.workgroups = new HashSet<>(sampleDataProvider.getWorkgroups());
    }

    private boolean exists(String workgroupId) {
        return workgroups.stream()
                .anyMatch(workgroup -> workgroup.getId().equals(workgroupId));
    }

    @Override
    public Set<IWorkgroup> getWorkgroups() {
        return this.workgroups;
    }

    @Override
    public Optional<IWorkgroup> getWorkgroup(String workgroupId) {
        return workgroups.stream()
                .filter(workgroup -> workgroup.getId().equals(workgroupId))
                .findFirst();
    }

    @Override
    public CreationResult createWorkgroup(IWorkgroup workgroup) {
        if (exists(workgroup.getId())) {
            return CreationResult.CONFLICT;
        }
        if(workgroups.add(workgroup)) {
            return CreationResult.SUCCESS;
        }
        return CreationResult.ERROR;
    }

    @Override
    public DeletionResult deleteWorkgroup(String workgroupId) {
        val workgroupsToDelete = workgroups.stream()
                .filter(workgroup -> workgroup.getId().equals(workgroupId))
                .collect(Collectors.toSet());
        if(workgroupsToDelete.isEmpty()) {
            return DeletionResult.NOT_FOUND;
        }
        val deleted = workgroups.removeAll(workgroupsToDelete);
        if(deleted) {
            return DeletionResult.SUCCESS;
        }
        return DeletionResult.ERROR;
    }

    @Override
    public Set<IMeeting> getMeetingsOfWorkgroup(String workgroupId) {
        return meetingManagementApiClient.findAll().stream()
                .filter(meeting -> meeting.getWorkgroupId().equals(workgroupId))
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<IMeeting> getCurrentMeeting(String workgroupId) {
        return getMeetingsOfWorkgroup(workgroupId).stream()
                .filter(meeting -> meeting.getStart().isBefore(Instant.now()) && meeting.getEnd().isAfter(Instant.now()))
                .findFirst();
    }

    @Override
    public ExtensionResult extendCurrentMeeting(String workgroupId, Duration extension) {
        return getCurrentMeeting(workgroupId)
                .map(meeting -> ExtensionResult.fromHttpStatus(meetingManagementApiClient.extendMeeting(meeting.getId(), extension.toMinutes()).getStatusCode()))
                .orElse(ExtensionResult.NOT_FOUND);
    }

    private void sortWorkgroupsById(List<IWorkgroup> workgroups) {
        workgroups.sort(Comparator.comparing(IWorkgroup::getId));
    }
}
