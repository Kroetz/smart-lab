package de.qaware.smartlab.workgroup.management.service.business;

import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.service.business.IBasicEntityManagementBusinessLogic;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface IWorkgroupManagementBusinessLogic extends IBasicEntityManagementBusinessLogic<IWorkgroup, WorkgroupId> {

    Optional<Set<IEvent>> getEventsOfWorkgroup(WorkgroupId workgroupId);
    Optional<IEvent> getCurrentEvent(WorkgroupId workgroupId);
    void extendCurrentEvent(WorkgroupId workgroupId, Duration extension);
}
