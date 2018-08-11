package de.qaware.smartlab.event.management.service.business;

import de.qaware.smartlab.core.data.event.EventId;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.location.LocationId;
import de.qaware.smartlab.core.data.workgroup.WorkgroupId;
import de.qaware.smartlab.core.exception.data.NotFoundException;
import de.qaware.smartlab.core.exception.data.MaximalDurationReachedException;
import de.qaware.smartlab.core.exception.data.MinimalDurationReachedException;
import de.qaware.smartlab.core.service.business.AbstractBasicEntityManagementBusinessLogic;
import de.qaware.smartlab.event.management.configuration.EventManagementServiceConfiguration;
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
            @Qualifier(EventManagementServiceConfiguration.QUALIFIER_MAX_EVENT_DURATION) Duration maxEventDuration) {
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
    public void shortenEvent(
            EventId eventId,
            Duration shortening) {
        // TODO: Simpler with "ifPresentOrElse" in Java 9 (See https://stackoverflow.com/questions/23773024/functional-style-of-java-8s-optional-ifpresent-and-if-not-present)
        Optional<IEvent> eventOptional = findOne(eventId);
        if(eventOptional.isPresent()) {
            IEvent event = eventOptional.get();
            Duration shortenedDuration = event.getDuration().minus(shortening);
            if(shortenedDuration.isNegative() || shortenedDuration.isZero()) {
                throw new MinimalDurationReachedException(eventId.getIdValue());
            }
            this.eventManagementRepository.shortenEvent(event, shortening);
            return;
        }
        throw new NotFoundException(eventId.getIdValue());
    }

    @Override
    public void extendEvent(
            EventId eventId,
            Duration extension) {
        // TODO: Simpler with "ifPresentOrElse" in Java 9 (See https://stackoverflow.com/questions/23773024/functional-style-of-java-8s-optional-ifpresent-and-if-not-present)
        Optional<IEvent> eventOptional = findOne(eventId);
        if(eventOptional.isPresent()) {
            IEvent event = eventOptional.get();
            if(event.getDuration().plus(extension).compareTo(this.maxEventDuration) > 0) {
                throw new MaximalDurationReachedException(eventId.getIdValue());
            }
            this.eventManagementRepository.extendEvent(event, extension);
            return;
        }
        throw new NotFoundException(eventId.getIdValue());
    }

    @Override
    public void shiftEvent(
            EventId eventId,
            Duration shift) {
        // TODO: Simpler with "ifPresentOrElse" in Java 9 (See https://stackoverflow.com/questions/23773024/functional-style-of-java-8s-optional-ifpresent-and-if-not-present)
        Optional<IEvent> eventOptional = findOne(eventId);
        if(eventOptional.isPresent()) {
            IEvent event = eventOptional.get();
            this.eventManagementRepository.shiftEvent(event, shift);
            return;
        }
        throw new NotFoundException(eventId.getIdValue());
    }
}
