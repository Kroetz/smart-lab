package de.qaware.smartlabworkgroup.repository;

import de.qaware.smartlabapi.service.meeting.IMeetingManagementService;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.result.ExtensionResult;
import de.qaware.smartlabcore.generic.repository.AbstractBasicEntityManagementRepositoryMock;
import de.qaware.smartlabsampledata.provider.ISampleDataProvider;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class WorkgroupManagementRepositoryMock extends AbstractBasicEntityManagementRepositoryMock<IWorkgroup, WorkgroupId> implements IWorkgroupManagementRepository {

    private final IMeetingManagementService meetingManagementService;

    public WorkgroupManagementRepositoryMock(
            IMeetingManagementService meetingManagementService,
            ISampleDataProvider sampleDataProvider) {
        this.meetingManagementService = meetingManagementService;
        this.entities = new HashSet<>(sampleDataProvider.getWorkgroups());
    }

    @Override
    public Set<IMeeting> getMeetingsOfWorkgroup(@NonNull IWorkgroup workgroup) {
        return this.meetingManagementService.findAll().values().stream()
                .flatMap(Set::stream)
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
                    .map(meeting -> {
                        this.meetingManagementService.extendMeeting(meeting.getId(), meeting.getRoomId(), extension);
                        return ExtensionResult.SUCCESS;})
                    .orElse(ExtensionResult.NOT_FOUND);
        }
        catch(Exception e) {
            return ExtensionResult.fromException(e);
        }
    }
}
