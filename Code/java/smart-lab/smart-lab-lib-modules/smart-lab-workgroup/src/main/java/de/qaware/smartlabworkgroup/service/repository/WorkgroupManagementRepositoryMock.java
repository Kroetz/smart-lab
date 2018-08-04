package de.qaware.smartlabworkgroup.service.repository;

import de.qaware.smartlabcore.data.workgroup.IWorkgroup;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;
import de.qaware.smartlabcore.service.repository.AbstractBasicEntityManagementRepositoryMock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
@Slf4j
public class WorkgroupManagementRepositoryMock extends AbstractBasicEntityManagementRepositoryMock<IWorkgroup, WorkgroupId> implements IWorkgroupManagementRepository {

    public WorkgroupManagementRepositoryMock(
            Set<IWorkgroup> initialWorkgroups) {
        super(initialWorkgroups);
        this.entities = new HashSet<>();
    }
}
