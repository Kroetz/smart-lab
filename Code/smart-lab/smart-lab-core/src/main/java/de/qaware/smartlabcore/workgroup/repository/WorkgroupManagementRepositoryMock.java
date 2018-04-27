package de.qaware.smartlabcore.workgroup.repository;

import de.qaware.smartlabcommons.api.client.IMeetingManagementApiClient;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.sample.provider.ISampleDataProvider;
import de.qaware.smartlabcore.generic.repository.AbstractEntityManagementRepositoryMock;
import de.qaware.smartlabcore.generic.result.ExtensionResult;
import feign.FeignException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class WorkgroupManagementRepositoryMock extends AbstractEntityManagementRepositoryMock<IWorkgroup> implements IWorkgroupManagementRepository {

    private final IMeetingManagementApiClient meetingManagementApiClient;

    public WorkgroupManagementRepositoryMock(
            IMeetingManagementApiClient meetingManagementApiClient,
            ISampleDataProvider sampleDataProvider) {
        this.meetingManagementApiClient = meetingManagementApiClient;
        this.entities = new HashSet<>(sampleDataProvider.getWorkgroups());
    }

    @Override
    public Set<IMeeting> getMeetingsOfWorkgroup(@NonNull IWorkgroup workgroup) {
        return meetingManagementApiClient.findAll().stream()
                .filter(meeting -> meeting.getWorkgroupId().equals(workgroup.getId()))
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<IMeeting> getCurrentMeeting(@NonNull IWorkgroup workgroup) {
        return getMeetingsOfWorkgroup(workgroup).stream()
                .filter(meeting -> meeting.getStart().isBefore(Instant.now()) && meeting.getEnd().isAfter(Instant.now()))
                .findFirst();
    }

    @Override
    public ExtensionResult extendCurrentMeeting(@NonNull IWorkgroup workgroup, Duration extension) {
        try {
            return getCurrentMeeting(workgroup)
                    .map(meeting -> ExtensionResult.fromHttpStatus(meetingManagementApiClient.extendMeeting(meeting.getId(), extension.toMinutes()).getStatusCode()))
                    .orElse(ExtensionResult.NOT_FOUND);
        }
        catch(FeignException e) {
            return ExtensionResult.fromHttpStatus(HttpStatus.valueOf(e.status()));
        }
    }
}
