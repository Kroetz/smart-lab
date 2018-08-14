package de.qaware.smartlab.api.service.connector.event;

import de.qaware.smartlab.api.service.connector.generic.IBasicEntityManagementService;
import de.qaware.smartlab.core.data.event.EventId;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.event.EventDto;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;

import java.time.Duration;
import java.util.Set;

public interface IEventManagementService extends IBasicEntityManagementService<IEvent, EventId, EventDto> {

    Set<IEvent> findAll();
    Set<IEvent> findAll(LocationId locationId);
    Set<IEvent> findAll(WorkgroupId workgroupId);
    Set<IEvent> findAllCurrent();
    IEvent findOne(EventId eventId);
    Set<IEvent> findMultiple(EventId[] eventIds);
    IEvent findCurrent(LocationId locationId);
    IEvent findCurrent(WorkgroupId workgroupId);
    IEvent create(IEvent event);
    void delete(EventId eventId);
    void shortenEvent(EventId eventId, Duration shortening);
    void extendEvent(EventId eventId, Duration extension);
    void shiftEvent(EventId eventId, Duration shift);
}
