package de.qaware.smartlabcore.workgroup.repository;

import de.qaware.smartlabcommons.api.configprovidermock.client.IWorkgroupConfigProviderMockClient;
import de.qaware.smartlabcommons.api.management.client.IMeetingManagementApiClient;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class WorkgroupManagementRepositoryMock implements IWorkgroupManagementRepository {

    private final IWorkgroupConfigProviderMockClient workgroupConfigProviderMockClient;
    private final IMeetingManagementApiClient meetingManagementApiClient;

    public WorkgroupManagementRepositoryMock(
            IWorkgroupConfigProviderMockClient workgroupConfigProviderMockClient,
            IMeetingManagementApiClient meetingManagementApiClient) {
        this.workgroupConfigProviderMockClient = workgroupConfigProviderMockClient;
        this.meetingManagementApiClient = meetingManagementApiClient;
    }

    @Override
    public List<IWorkgroup> getWorkgroups() {
        return workgroupConfigProviderMockClient.getWorkgroups();
    }

    @Override
    public Optional<IWorkgroup> getWorkgroup(long workgroupId) {
        return Optional.ofNullable(workgroupConfigProviderMockClient.getWorkgroup(workgroupId).getBody());
    }

    @Override
    public boolean createWorkgroup(IWorkgroup workgroup) {
        return workgroupConfigProviderMockClient.createWorkgroup(workgroup);
    }

    @Override
    public void deleteWorkgroup(long workgroupId) {
        workgroupConfigProviderMockClient.deleteWorkgroup(workgroupId);
    }

    @Override
    public List<IMeeting> getMeetingsOfWorkgroup(long workgroupId) {
        return meetingManagementApiClient.getMeetings().stream()
                .filter(meeting -> meeting.getWorkgroupId() == workgroupId)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<IMeeting> getCurrentMeeting(long workgroupId) {
        return getMeetingsOfWorkgroup(workgroupId).stream()
                .filter(meeting -> meeting.getStart().isBefore(Instant.now()) && meeting.getEnd().isAfter(Instant.now()))
                .findFirst();
    }

    @Override
    public boolean extendCurrentMeeting(long workgroupId, Duration extension) {
        return getCurrentMeeting(workgroupId)
                .map(meeting -> meetingManagementApiClient.extendMeeting(meeting.getId(), extension.toMinutes()))
                .orElse(false);
    }
}
