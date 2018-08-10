package de.qaware.smartlab.integrationtest.event

import de.qaware.smartlab.api.service.connector.event.IEventManagementService
import de.qaware.smartlab.core.data.event.IEvent
import de.qaware.smartlab.core.data.event.EventId
import de.qaware.smartlab.core.data.event.EventDto
import de.qaware.smartlab.core.exception.EntityConflictException
import de.qaware.smartlab.core.exception.EntityNotFoundException
import de.qaware.smartlab.core.exception.MaximalDurationReachedException
import de.qaware.smartlab.core.exception.MinimalDurationReachedException

import de.qaware.smartlab.data.set.factory.AstronautsSampleDataSetFactory
import de.qaware.smartlab.data.set.factory.CoastGuardSampleDataSetFactory
import de.qaware.smartlab.data.set.factory.FireFightersSampleDataSetFactory
import de.qaware.smartlab.data.set.factory.ForestRangersSampleDataSetFactory
import de.qaware.smartlab.event.management.configuration.EventManagementServiceConfiguration
import de.qaware.smartlab.integrationtest.generic.CrudApiIntegrationTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest

import java.time.Duration

import static java.time.Duration.between
import static java.time.Duration.ofMinutes
import static java.util.Arrays.asList

@SpringBootTest
class EventManagementApiIntegrationTest extends CrudApiIntegrationTest<EventId, EventDto, IEvent> {

    @Autowired
    private IEventManagementService eventManagementService

    @Autowired
    private CoastGuardSampleDataSetFactory coastGuardDataFactory

    @Autowired
    private ForestRangersSampleDataSetFactory forestRangersDataFactory

    @Autowired
    private FireFightersSampleDataSetFactory fireFightersDataFactory

    @Autowired
    private AstronautsSampleDataSetFactory astronautsDataFactory

    @Autowired
    @Qualifier(EventManagementServiceConfiguration.QUALIFIER_MAX_EVENT_DURATION)
    private Duration maxEventDuration;

    @Override
    def setupDataForFindAll_withExisting() {
        crudService = eventManagementService
        entitiesForFindAll_withExisting = new HashSet<>(asList(
                coastGuardDataFactory.createEventMap().get(coastGuardDataFactory.EVENT_ID_WHALES),
                forestRangersDataFactory.createEventMap().get(forestRangersDataFactory.EVENT_ID_BARK_BEETLE),
                fireFightersDataFactory.createEventMap().get(fireFightersDataFactory.EVENT_ID_TRUCK)))
    }

    @Override
    def setupDataForFindOne_withExisting() {
        crudService = eventManagementService
        entityForFindOne_withExisting = coastGuardDataFactory.createEventMap().get(coastGuardDataFactory.EVENT_ID_WHALES)
    }

    @Override
    def setupDataForFindOne_withoutExisting() {
        crudService = eventManagementService
        entityIdForFindOne_withoutExisting = coastGuardDataFactory.EVENT_ID_WHALES
    }

    @Override
    def setupDataForFindMultiple_withExisting() {
        crudService = eventManagementService
        def eventId1 = coastGuardDataFactory.EVENT_ID_WHALES
        def eventId2 = forestRangersDataFactory.EVENT_ID_BARK_BEETLE
        def eventId3 = fireFightersDataFactory.EVENT_ID_TRUCK
        def event1 = coastGuardDataFactory.createEventMap().get(eventId1)
        def event2 = forestRangersDataFactory.createEventMap().get(eventId2)
        def event3 = fireFightersDataFactory.createEventMap().get(eventId3)
        allEntitiesForFindMultiple_withExisting = [event1, event2, event3]
    }

    @Override
    def setupDataForFindMultiple_withoutExisting() {
        crudService = eventManagementService
        def eventId1 = coastGuardDataFactory.EVENT_ID_WHALES
        def eventId2 = forestRangersDataFactory.EVENT_ID_BARK_BEETLE
        def eventId3 = fireFightersDataFactory.EVENT_ID_TRUCK
        def event1 = coastGuardDataFactory.createEventMap().get(eventId1)
        def event2 = forestRangersDataFactory.createEventMap().get(eventId2)
        def event3 = fireFightersDataFactory.createEventMap().get(eventId3)
        allEntitiesForFindMultiple_withoutExisting = [event1, event2]
        requestedEntitiesForFindMultiple_withoutExisting = [event2, event3]
    }

    @Override
    def setupDataForCreate_withoutConflict() {
        crudService = eventManagementService
        entityForCreate_withoutConflict = coastGuardDataFactory.createEventMap().get(coastGuardDataFactory.EVENT_ID_WHALES)
    }

    @Override
    def setupDataForCreate_withConflict() {
        crudService = eventManagementService
        entityForCreate_withConflict = coastGuardDataFactory.createEventMap().get(coastGuardDataFactory.EVENT_ID_WHALES)
    }

    @Override
    def setupDataForDelete_withExisting() {
        crudService = eventManagementService
        entityForDelete_withExisting = coastGuardDataFactory.createEventMap().get(coastGuardDataFactory.EVENT_ID_WHALES)
    }

    @Override
    def setupDataForDelete_withoutExisting() {
        crudService = eventManagementService
        entityIdForDelete_withoutExisting = coastGuardDataFactory.EVENT_ID_WHALES
    }

    def "Shorten an existing event by a valid duration"() {

        given: "A event with the requested event ID does exist"
        def eventId = coastGuardDataFactory.EVENT_ID_WHALES
        def event = coastGuardDataFactory.createEventMap().get(eventId)
        eventManagementService.create(event)
        def shortening = (event.getDuration() - ofMinutes(1))

        when: "The event is shortened"
        eventManagementService.shortenEvent(eventId, shortening)

        then: "The event is now shorter than it was originally"
        def shortenedEvent = eventManagementService.findOne(eventId)
        shortenedEvent.getEnd() == event.getEnd() - shortening

        cleanup:
        eventManagementService.delete(eventId)
    }

    def "Shorten an existing event so that it would be shorter than the minimal duration"() {

        given: "The event to shorten does exist"
        def eventId = coastGuardDataFactory.EVENT_ID_WHALES
        def event = coastGuardDataFactory.createEventMap().get(eventId)
        eventManagementService.create(event)
        def shortening = event.getDuration()

        when: "The event is shortened beyond the minimum"
        eventManagementService.shortenEvent(eventId, shortening)

        then: "An exception is thrown"
        thrown(MinimalDurationReachedException)

        cleanup:
        eventManagementService.delete(eventId)
    }

    def "Shorten a event with an ID that does not exist"() {

        given: "The event to shorten does not exist"
        def eventId = coastGuardDataFactory.EVENT_ID_WHALES
        def event = coastGuardDataFactory.createEventMap().get(eventId)
        def shortening = (event.getDuration() - ofMinutes(1))

        when: "The event is shortened"
        eventManagementService.shortenEvent(eventId, shortening)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)
    }

    def "Extend an existing event by a valid duration"() {

        given: "The event to shorten does exist"
        def eventId = coastGuardDataFactory.EVENT_ID_WHALES
        def event = coastGuardDataFactory.createEventMap().get(eventId)
        eventManagementService.create(event)
        def extension = ofMinutes(1)

        when: "The event is extended"
        eventManagementService.extendEvent(eventId, extension)

        then: "The event is now longer than it was originally"
        def extendedEvent = eventManagementService.findOne(eventId)
        extendedEvent.getEnd() == event.getEnd() + extension

        cleanup:
        eventManagementService.delete(eventId)
    }

    def "Extend an existing event so that it would be longer than the maximal duration"() {

        given: "The event to extend does exist"
        def eventId = coastGuardDataFactory.EVENT_ID_WHALES
        def event = coastGuardDataFactory.createEventMap().get(eventId)
        eventManagementService.create(event)
        def extension = this.maxEventDuration

        when: "The event is extended beyond the maximum"
        eventManagementService.extendEvent(eventId, extension)

        then: "An exception is thrown"
        thrown(MaximalDurationReachedException)

        cleanup:
        eventManagementService.delete(eventId)
    }

    def "Extend an existing event so that it would conflict with another event"() {

        given: "The event to extend does exist alongside with a follow up event"
        def eventToExtendId = coastGuardDataFactory.EVENT_ID_WHALES
        def followUpEventId = coastGuardDataFactory.EVENT_ID_WHIRLPOOLS
        def eventToExtend = coastGuardDataFactory.createEventMap().get(eventToExtendId)
        def followUpEvent = coastGuardDataFactory.createEventMap().get(followUpEventId)
        eventManagementService.create(eventToExtend)
        eventManagementService.create(followUpEvent)
        def extension = between(eventToExtend.getEnd(), followUpEvent.getEnd())

        when: "The event is extended"
        eventManagementService.extendEvent(eventToExtendId, extension)

        then: "An exception is thrown"
        thrown(EntityConflictException)

        cleanup:
        eventManagementService.delete(eventToExtendId)
        eventManagementService.delete(followUpEventId)
    }

    def "Extend a event with an ID that does not exist"() {

        given: "The event to extend does not exist"
        def eventId = coastGuardDataFactory.EVENT_ID_WHALES
        def extension = ofMinutes(1)

        when: "The event is extended"
        eventManagementService.extendEvent(eventId, extension)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)
    }

    def "Shift an existing event by a valid duration"() {

        given: "The event to shift does exist"
        def eventId = coastGuardDataFactory.EVENT_ID_WHALES
        def event = coastGuardDataFactory.createEventMap().get(eventId)
        eventManagementService.create(event)
        def shift = ofMinutes(1)

        when: "The event is shifted"
        eventManagementService.shiftEvent(eventId, shift)

        then: "The event is now longer than it was originally"
        def shiftedEvent = eventManagementService.findOne(eventId)
        shiftedEvent.getStart() == event.getStart() + shift
        shiftedEvent.getEnd() == event.getEnd() + shift

        cleanup:
        eventManagementService.delete(eventId)
    }

    def "Shift an existing event so that it would conflict with another event"() {

        given: "The event to shift does exist alongside with a follow up event"
        def eventToShiftId = coastGuardDataFactory.EVENT_ID_WHALES
        def followUpEventId = coastGuardDataFactory.EVENT_ID_WHIRLPOOLS
        def eventToShift = coastGuardDataFactory.createEventMap().get(eventToShiftId)
        def followUpEvent = coastGuardDataFactory.createEventMap().get(followUpEventId)
        eventManagementService.create(eventToShift)
        eventManagementService.create(followUpEvent)
        def shift = between(eventToShift.getEnd(), followUpEvent.getEnd())

        when: "The event is shifted"
        eventManagementService.shiftEvent(eventToShiftId, shift)

        then: "An exception is thrown"
        thrown(EntityConflictException)

        cleanup:
        eventManagementService.delete(eventToShiftId)
        eventManagementService.delete(followUpEventId)
    }

    def "Shift a event with an ID that does not exist"() {

        given: "The event to shift does not exist"
        def eventId = coastGuardDataFactory.EVENT_ID_WHALES
        def shift = ofMinutes(1)

        when: "The event is shifted"
        eventManagementService.shiftEvent(eventId, shift)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)
    }
}
