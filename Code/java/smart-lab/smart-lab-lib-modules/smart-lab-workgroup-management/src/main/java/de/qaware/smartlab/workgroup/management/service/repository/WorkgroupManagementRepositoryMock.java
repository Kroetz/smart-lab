package de.qaware.smartlab.workgroup.management.service.repository;

import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.service.repository.AbstractBasicEntityManagementRepositoryMock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@Slf4j
public class WorkgroupManagementRepositoryMock extends AbstractBasicEntityManagementRepositoryMock<IWorkgroup, WorkgroupId> implements IWorkgroupManagementRepository {

    public WorkgroupManagementRepositoryMock(
            Set<IWorkgroup> initialWorkgroups) {
        super(initialWorkgroups);
    }
}
