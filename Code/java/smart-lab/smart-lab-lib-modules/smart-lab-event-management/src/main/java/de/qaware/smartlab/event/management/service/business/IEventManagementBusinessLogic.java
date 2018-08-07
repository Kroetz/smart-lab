package de.qaware.smartlab.event.management.service.business;

import de.qaware.smartlab.core.data.event.EventId;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.result.ExtensionResult;
import de.qaware.smartlab.core.result.ShiftResult;
import de.qaware.smartlab.core.result.ShorteningResult;
import de.qaware.smartlab.core.service.business.IBasicEntityManagementBusinessLogic;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface IEventManagementBusinessLogic extends IBasicEntityManagementBusinessLogic<IEvent, EventId> {

    Set<IEvent> findAll(LocationId locationId);
    Set<IEvent> findAll(WorkgroupId workgroupId);
    Set<IEvent> findAllCurrent();
    Optional<IEvent> findCurrent(LocationId locationId);
    Optional<IEvent> findCurrent(WorkgroupId workgroupId);
    ShorteningResult shortenEvent(
            EventId eventId,
            Duration shortening);
    ExtensionResult extendEvent(
            EventId eventId,
            Duration extension);
    ShiftResult shiftEvent(
            EventId eventId,
            Duration shift);
}
