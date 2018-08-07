package de.qaware.smartlab.event.management.service.repository;

import de.qaware.smartlab.core.data.event.EventId;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.result.ExtensionResult;
import de.qaware.smartlab.core.result.ShiftResult;
import de.qaware.smartlab.core.result.ShorteningResult;
import de.qaware.smartlab.core.service.repository.IBasicEntityManagementRepository;
import lombok.NonNull;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface IEventManagementRepository extends IBasicEntityManagementRepository<IEvent, EventId> {

    Set<IEvent> findAll(LocationId locationId);
    Set<IEvent> findAll(WorkgroupId workgroupId);
    Set<IEvent> findAllCurrent();
    Optional<IEvent> findCurrent(LocationId locationId);
    Optional<IEvent> findCurrent(WorkgroupId workgroupId);
    ShorteningResult shortenEvent(
            @NonNull IEvent event,
            Duration shortening);
    ExtensionResult extendEvent(
            @NonNull IEvent event,
            Duration extension);
    ShiftResult shiftEvent(
            @NonNull IEvent event,
            Duration shift);
}
