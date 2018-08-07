package de.qaware.smartlab.event.management.service.business;

import de.qaware.smartlab.core.data.event.EventId;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.service.business.AbstractBasicEntityManagementBusinessLogic;
import de.qaware.smartlab.core.result.*;
import de.qaware.smartlab.event.management.service.repository.IEventManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class EventManagementBusinessLogic extends AbstractBasicEntityManagementBusinessLogic<IEvent, EventId> implements IEventManagementBusinessLogic {

    private final IEventManagementRepository eventManagementRepository;
    private final Duration maxEventDuration;

    public EventManagementBusinessLogic(
            IEventManagementRepository eventManagementRepository,
            // TODO: String literal
            @Qualifier("maxEventDuration") Duration maxEventDuration) {
        super(eventManagementRepository);
        this.eventManagementRepository = eventManagementRepository;
        this.maxEventDuration = maxEventDuration;
    }

    @Override
    public Set<IEvent> findAll(LocationId locationId) {
        return this.eventManagementRepository.findAll(locationId);
    }

    @Override
    public Set<IEvent> findAll(WorkgroupId workgroupId) {
        return this.eventManagementRepository.findAll(workgroupId);
    }

    @Override
    public Set<IEvent> findAllCurrent() {
        return this.eventManagementRepository.findAllCurrent();
    }

    @Override
    public Optional<IEvent> findCurrent(LocationId locationId) {
        return this.eventManagementRepository.findCurrent(locationId);
    }

    @Override
    public Optional<IEvent> findCurrent(WorkgroupId workgroupId) {
        return this.eventManagementRepository.findCurrent(workgroupId);
    }

    @Override
    public ShorteningResult shortenEvent(
            EventId eventId,
            Duration shortening) {
        return findOne(eventId).map(event -> {
            Duration shortenedDuration = event.getDuration().minus(shortening);
            if(shortenedDuration.isNegative() || shortenedDuration.isZero()) {
                return ShorteningResult.MINIMUM_REACHED;
            }
            return this.eventManagementRepository.shortenEvent(event, shortening);
        }).orElse(ShorteningResult.NOT_FOUND);
    }

    @Override
    public ExtensionResult extendEvent(
            EventId eventId,
            Duration extension) {
        return findOne(eventId).map(event -> {
            if(event.getDuration().plus(extension).compareTo(this.maxEventDuration) > 0) {
                return ExtensionResult.MAXIMUM_REACHED_REACHED;
            }
            return this.eventManagementRepository.extendEvent(event, extension);
        }).orElse(ExtensionResult.NOT_FOUND);
    }

    @Override
    public ShiftResult shiftEvent(
            EventId eventId,
            Duration shift) {
        return findOne(eventId)
                .map(event -> this.eventManagementRepository.shiftEvent(event, shift))
                .orElse(ShiftResult.NOT_FOUND);
    }
}
