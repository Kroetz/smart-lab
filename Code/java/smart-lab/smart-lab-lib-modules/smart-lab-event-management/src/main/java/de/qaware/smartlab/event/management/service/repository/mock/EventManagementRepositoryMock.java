package de.qaware.smartlab.event.management.service.repository.mock;

import de.qaware.smartlab.core.data.event.EventId;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.exception.EntityConflictException;
import de.qaware.smartlab.core.exception.EntityCreationException;
import de.qaware.smartlab.core.miscellaneous.Property;
import de.qaware.smartlab.core.result.DeletionResult;
import de.qaware.smartlab.core.result.ExtensionResult;
import de.qaware.smartlab.core.result.ShiftResult;
import de.qaware.smartlab.core.result.ShorteningResult;
import de.qaware.smartlab.core.service.repository.AbstractBasicEntityManagementRepositoryMock;
import de.qaware.smartlab.event.management.service.repository.IEventManagementRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Repository
@ConditionalOnProperty(
        prefix = Property.Prefix.EVENT_MANAGEMENT_REPOSITORY,
        name = Property.Name.EVENT_MANAGEMENT_REPOSITORY,
        havingValue = Property.Value.EventManagementRepository.MOCK)
@Slf4j
public class EventManagementRepositoryMock extends AbstractBasicEntityManagementRepositoryMock<IEvent, EventId> implements IEventManagementRepository {

    private final Map<LocationId, Set<IEvent>> eventsByLocation;

    public EventManagementRepositoryMock(Set<IEvent> initialEvents) {
        super(initialEvents);
        this.eventsByLocation = new HashMap<>();
    }

    @Override
    public Set<IEvent> findAll() {
        Set<IEvent> allEvents = new HashSet<>();
        this.eventsByLocation.keySet()
                .forEach(locationId -> allEvents.addAll(this.eventsByLocation.get(locationId)));
        return allEvents;
    }

    @Override
    public Set<IEvent> findAll(LocationId locationId) {
        Set<IEvent> events = this.eventsByLocation.get(locationId);
        return isNull(events) ? emptySet() : events;
    }

    @Override
    public Set<IEvent> findAll(WorkgroupId workgroupId) {
        return findAll().stream()
                .filter(event -> event.getWorkgroupId().equals(workgroupId))
                .collect(toSet());
    }

    @Override
    public Set<IEvent> findAllCurrent() {
        return findAll().stream()
                .filter(IEvent::isInProgress)
                .collect(toSet());
    }

    @Override
    public Optional<IEvent> findOne(EventId eventId) {
        Set<IEvent> eventsAtLocation = this.eventsByLocation.get(eventId.getLocationIdPart());
        return isNull(eventsAtLocation) ? Optional.empty() : eventsAtLocation.stream()
                .filter(entity -> entity.getId().equals(eventId))
                .findFirst();
    }

    @Override
    public Map<EventId, Optional<IEvent>> findMultiple(Set<EventId> eventIds) {
        Map<EventId, Optional<IEvent>> eventsById = new HashMap<>();
        eventIds.forEach(eventId -> eventsById.put(eventId, findOne(eventId)));
        return eventsById;
    }

    @Override
    public Optional<IEvent> findCurrent(LocationId locationId) {
        return findAll(locationId).stream()
                .filter(IEvent::isInProgress)
                .findFirst();
    }

    @Override
    public Optional<IEvent> findCurrent(WorkgroupId workgroupId) {
        return findAll().stream()
                .filter(event -> event.getWorkgroupId().equals(workgroupId))
                .filter(IEvent::isInProgress)
                .findFirst();
    }

    @Override
    public synchronized IEvent create(IEvent event) {
        boolean eventCollision = findAll(event.getLocationId()).stream().anyMatch(m -> m.isColliding(event));
        if(eventCollision || exists(event.getId())) {
            log.error("Cannot create event {} because a event with that ID already exists", event);
            // TODO: Meaningful exception message
            throw new EntityConflictException();
        }
        Set<IEvent> eventsAtLocation = this.eventsByLocation.get(event.getLocationId());
        if(isNull(eventsAtLocation)) {
            eventsAtLocation = new HashSet<>();
            this.eventsByLocation.put(event.getLocationId(), eventsAtLocation);
        }
        if(eventsAtLocation.add(event)) {
            return event;
        }
        log.error("Cannot create event {} because of unknown reason", event);
        // TODO: Meaningful exception message
        throw new EntityCreationException();
    }

    @Override
    public synchronized DeletionResult delete(EventId eventId) {
        Set<IEvent> eventsAtLocation = this.eventsByLocation.get(eventId.getLocationIdPart());
        List<IEvent> eventsToDelete = isNull(eventsAtLocation) ? emptyList() : eventsAtLocation.stream()
                .filter(event -> event.getId().equals(eventId))
                .collect(toList());
        if(isNull(eventsAtLocation) || eventsToDelete.isEmpty()) {
            return DeletionResult.NOT_FOUND;
        }
        boolean deleted =  eventsAtLocation.removeAll(eventsToDelete);
        if(deleted) {
            return DeletionResult.SUCCESS;
        }
        return DeletionResult.ERROR;
    }

    @Override
    public synchronized ShorteningResult shortenEvent(@NonNull IEvent event, Duration shortening) {
        if(delete(event.getId()) == DeletionResult.SUCCESS) {
            IEvent shortenedEvent = event.withEnd(event.getEnd().minus(shortening));
            try {
                create(shortenedEvent);
                return ShorteningResult.SUCCESS;
            }
            catch(Exception e) {
                return ShorteningResult.ERROR;
            }
        }
        return ShorteningResult.ERROR;
    }

    @Override
    public synchronized ExtensionResult extendEvent(@NonNull IEvent event, Duration extension) {
        IEvent extendedEvent = event.withEnd(event.getEnd().plus(extension));
        if(delete(event.getId()) == DeletionResult.SUCCESS) {
            try {
                create(extendedEvent);
                return ExtensionResult.SUCCESS;
            }
            catch(EntityConflictException e) {
                create(event);
                return ExtensionResult.CONFLICT;
            }
            catch(Exception e) {
                create(event);
                return ExtensionResult.ERROR;
            }
        }
        return ExtensionResult.ERROR;
    }

    @Override
    public synchronized ShiftResult shiftEvent(@NonNull IEvent event, Duration shift) {
        IEvent shiftedEvent = event.withStartAndEnd(
                event.getStart().plus(shift),
                event.getEnd().plus(shift));
        if(delete(event.getId()) == DeletionResult.SUCCESS) {
            try {
                create(shiftedEvent);
                return ShiftResult.SUCCESS;
            }
            catch(EntityConflictException e) {
                create(event);
                return ShiftResult.CONFLICT;
            }
            catch(Exception e) {
                create(event);
                return ShiftResult.ERROR;
            }
        }
        return ShiftResult.ERROR;
    }
}
