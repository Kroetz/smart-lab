package de.qaware.smartlab.api.service.connector.workgroup;

import de.qaware.smartlab.api.service.connector.generic.IBasicEntityManagementService;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.data.workgroup.WorkgroupDto;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;

import java.time.Duration;
import java.util.Set;

public interface IWorkgroupManagementService extends IBasicEntityManagementService<IWorkgroup, WorkgroupId, WorkgroupDto> {

    Set<IEvent> getEventsOfWorkgroup(WorkgroupId workgroupId);
    IEvent getCurrentEvent(WorkgroupId workgroupId);
    void extendCurrentEvent(WorkgroupId workgroupId, Duration extension);
}
