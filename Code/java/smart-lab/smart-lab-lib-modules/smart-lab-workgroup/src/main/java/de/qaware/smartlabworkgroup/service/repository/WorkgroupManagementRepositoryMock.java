package de.qaware.smartlabworkgroup.service.repository;

import de.qaware.smartlabapi.service.connector.meeting.IMeetingManagementService;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.result.ExtensionResult;
import de.qaware.smartlabcore.service.repository.AbstractBasicEntityManagementRepositoryMock;
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

    public WorkgroupManagementRepositoryMock(
            Set<IWorkgroup> initialWorkgroups) {
        super(initialWorkgroups);
        this.entities = new HashSet<>();
    }
}
