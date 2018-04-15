package de.qaware.smartlabcore.workgroup.service.mock;

import de.qaware.smartlabcommons.data.workgroup.Workgroup;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.api.client.IMeetingManagementApiClient;
import de.qaware.smartlabcore.workgroup.service.IWorkgroupManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Qualifier("mock")
@Slf4j
public class WorkgroupManagementService implements IWorkgroupManagementService {

    private final IWorkgroupConfigProviderMockClient workgroupConfigProvider;
    private final IMeetingManagementApiClient meetingManagementApiClient;

    public WorkgroupManagementService(
            @Qualifier("mock") IWorkgroupConfigProviderMockClient workgroupConfigProvider,
            IMeetingManagementApiClient meetingManagementApiClient) {
        this.workgroupConfigProvider = workgroupConfigProvider;
        this.meetingManagementApiClient = meetingManagementApiClient;
    }

    @Override
    public List<Workgroup> getWorkgroups() {
        return workgroupConfigProvider.getWorkgroups();
    }

    @Override
    public Optional<Workgroup> getWorkgroup(long workgroupId) {
        return workgroupConfigProvider.getWorkgroup(workgroupId);
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
    public boolean createWorkgroup(Workgroup workgroup) {
        return workgroupConfigProvider.createWorkgroup(workgroup);
    }

    @Override
    public void deleteWorkgroup(long workgroupId) {
        workgroupConfigProvider.deleteWorkgroup(workgroupId);
    }

    @Override
    public boolean extendCurrentMeeting(long workgroupId, long extensionInMinutes) {
        return getCurrentMeeting(workgroupId)
                .map(meeting -> meetingManagementApiClient.extendMeeting(meeting.getId(), extensionInMinutes))
                .orElse(false);
    }
}
