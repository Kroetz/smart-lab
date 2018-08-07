package de.qaware.smartlab.integrationtest.workgroup

import de.qaware.smartlab.api.service.connector.event.IEventManagementService
import de.qaware.smartlab.api.service.connector.workgroup.IWorkgroupManagementService
import de.qaware.smartlab.core.data.event.IEvent
import de.qaware.smartlab.core.data.workgroup.IWorkgroup
import de.qaware.smartlab.core.data.workgroup.WorkgroupId
import de.qaware.smartlab.core.data.workgroup.WorkgroupDto
import de.qaware.smartlab.core.exception.EntityConflictException
import de.qaware.smartlab.core.exception.EntityNotFoundException
import de.qaware.smartlab.core.exception.MaximalDurationReachedException

import de.qaware.smartlab.data.set.factory.AstronautsSampleDataSetFactory
import de.qaware.smartlab.data.set.factory.CoastGuardSampleDataSetFactory
import de.qaware.smartlab.data.set.factory.FireFightersSampleDataSetFactory
import de.qaware.smartlab.data.set.factory.ForestRangersSampleDataSetFactory
import de.qaware.smartlab.integrationtest.generic.CrudApiIntegrationTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest

import java.time.Duration

import static java.util.Arrays.asList

@SpringBootTest
class WorkgroupManagementApiIntegrationTest extends CrudApiIntegrationTest<WorkgroupId, WorkgroupDto, IWorkgroup> {

    @Autowired
    private IWorkgroupManagementService workgroupManagementService

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
    // TODO: String literal
    @Qualifier("maxEventDuration")
    private Duration maxEventDuration;

    @Override
    def setupDataForFindAll_withExisting() {
        crudService = workgroupManagementService
        entitiesForFindAll_withExisting = new HashSet<>(asList(
                coastGuardDataFactory.createWorkgroupMap().get(coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD),
                forestRangersDataFactory.createWorkgroupMap().get(forestRangersDataFactory.WORKGROUP_ID_FOREST_RANGERS),
                fireFightersDataFactory.createWorkgroupMap().get(fireFightersDataFactory.WORKGROUP_ID_FIRE_FIGHTERS)))
    }

    @Override
    def setupDataForFindOne_withExisting() {
        crudService = workgroupManagementService
        entityForFindOne_withExisting = coastGuardDataFactory.createWorkgroupMap().get(coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD)
    }

    @Override
    def setupDataForFindOne_withoutExisting() {
        crudService = workgroupManagementService
        entityIdForFindOne_withoutExisting = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
    }

    @Override
    def setupDataForFindMultiple_withExisting() {
        crudService = workgroupManagementService
        def workgroupId1 = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
        def workgroupId2 = forestRangersDataFactory.WORKGROUP_ID_FOREST_RANGERS
        def workgroupId3 = fireFightersDataFactory.WORKGROUP_ID_FIRE_FIGHTERS
        def workgroup1 = coastGuardDataFactory.createWorkgroupMap().get(workgroupId1)
        def workgroup2 = forestRangersDataFactory.createWorkgroupMap().get(workgroupId2)
        def workgroup3 = fireFightersDataFactory.createWorkgroupMap().get(workgroupId3)
        allEntitiesForFindMultiple_withExisting = [workgroup1, workgroup2, workgroup3]
    }

    @Override
    def setupDataForFindMultiple_withoutExisting() {
        crudService = workgroupManagementService
        def workgroupId1 = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
        def workgroupId2 = forestRangersDataFactory.WORKGROUP_ID_FOREST_RANGERS
        def workgroupId3 = fireFightersDataFactory.WORKGROUP_ID_FIRE_FIGHTERS
        def workgroup1 = coastGuardDataFactory.createWorkgroupMap().get(workgroupId1)
        def workgroup2 = forestRangersDataFactory.createWorkgroupMap().get(workgroupId2)
        def workgroup3 = fireFightersDataFactory.createWorkgroupMap().get(workgroupId3)
        allEntitiesForFindMultiple_withoutExisting = [workgroup1, workgroup2]
        requestedEntitiesForFindMultiple_withoutExisting = [workgroup2, workgroup3]
    }

    @Override
    def setupDataForCreate_withoutConflict() {
        crudService = workgroupManagementService
        entityForCreate_withoutConflict = coastGuardDataFactory.createWorkgroupMap().get(coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD)
    }

    @Override
    def setupDataForCreate_withConflict() {
        crudService = workgroupManagementService
        entityForCreate_withConflict = coastGuardDataFactory.createWorkgroupMap().get(coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD)
    }

    @Override
    def setupDataForDelete_withExisting() {
        crudService = workgroupManagementService
        entityForDelete_withExisting = coastGuardDataFactory.createWorkgroupMap().get(coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD)
    }

    @Override
    def setupDataForDelete_withoutExisting() {
        crudService = workgroupManagementService
        entityIdForDelete_withoutExisting = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
    }

    def "Get a set of all events of a specific workgroup when the workgroup has events"() {

        given: "A workgroup with the requested workgroup ID does exist"
        def workgroupId = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
        def workgroup = coastGuardDataFactory.createWorkgroupMap().get(workgroupId)
        workgroupManagementService.create(workgroup)

        and: "The requested workgroup and another workgroup have events"
        def events = new HashSet<IEvent>(asList(
                coastGuardDataFactory.createEventMap().get(coastGuardDataFactory.EVENT_ID_WHALES),
                coastGuardDataFactory.createEventMap().get(coastGuardDataFactory.EVENT_ID_WHIRLPOOLS),
                forestRangersDataFactory.createEventMap().get(forestRangersDataFactory.EVENT_ID_BARK_BEETLE),
                fireFightersDataFactory.createEventMap().get(fireFightersDataFactory.EVENT_ID_TRUCK)))
        def eventsOfWorkgroup = new HashSet<IEvent>(asList(
                coastGuardDataFactory.createEventMap().get(coastGuardDataFactory.EVENT_ID_WHALES),
                coastGuardDataFactory.createEventMap().get(coastGuardDataFactory.EVENT_ID_WHIRLPOOLS)))
        for(def event : events) {
            eventManagementService.create(event)
        }

        when: "The set of events of the workgroup is requested"
        def foundEvents = workgroupManagementService.getEventsOfWorkgroup(workgroupId)

        then: "The returned set equals the appropriate part of that one that was used to populate the repository"
        foundEvents == eventsOfWorkgroup

        cleanup:
        workgroupManagementService.delete(workgroupId)
        for(def event : events) {
            eventManagementService.delete(event.getId())
        }
    }

    def "Get a set of all events of a specific workgroup when the workgroup has no events"() {

        given: "A workgroup with the requested workgroup ID does exist"
        def workgroupId = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
        def workgroup = coastGuardDataFactory.createWorkgroupMap().get(workgroupId)
        workgroupManagementService.create(workgroup)

        and: "Only other workgroups than the requested one have events"
        def events = new HashSet<IEvent>(asList(
                forestRangersDataFactory.createEventMap().get(forestRangersDataFactory.EVENT_ID_BARK_BEETLE),
                fireFightersDataFactory.createEventMap().get(fireFightersDataFactory.EVENT_ID_TRUCK)))
        def eventsOfWorkgroup = new HashSet<IEvent>()
        for(def event : events) {
            eventManagementService.create(event)
        }

        when: "The set of events of the workgroup is requested"
        def foundEvents = workgroupManagementService.getEventsOfWorkgroup(workgroupId)

        then: "The returned set is empty"
        foundEvents == eventsOfWorkgroup

        cleanup:
        workgroupManagementService.delete(workgroupId)
        for(def event : events) {
            eventManagementService.delete(event.getId())
        }
    }

    def "Get a set of all events of a specific workgroup when a workgroup with that ID does not exist"() {

        given: "A workgroup with the requested workgroup ID does not exist"
        def workgroupId = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD

        and: "Several other workgroups have events"
        def events = new HashSet<IEvent>(asList(
                coastGuardDataFactory.createEventMap().get(coastGuardDataFactory.EVENT_ID_WHALES),
                coastGuardDataFactory.createEventMap().get(coastGuardDataFactory.EVENT_ID_WHIRLPOOLS),
                forestRangersDataFactory.createEventMap().get(forestRangersDataFactory.EVENT_ID_BARK_BEETLE),
                fireFightersDataFactory.createEventMap().get(fireFightersDataFactory.EVENT_ID_TRUCK)))
        for(def event : events) {
            eventManagementService.create(event)
        }

        when: "The set of events of the workgroup is requested"
        workgroupManagementService.getEventsOfWorkgroup(workgroupId)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)

        cleanup:
        for(def event : events) {
            eventManagementService.delete(event.getId())
        }
    }

    def "Get the current event of a specific workgroup when the workgroup currently has a event"() {

        given: "A workgroup with the requested workgroup ID does exist"
        def workgroupId = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
        def workgroup = coastGuardDataFactory.createWorkgroupMap().get(workgroupId)
        workgroupManagementService.create(workgroup)

        and: "The requested workgroup and another workgroup currently have a event"
        def events = new HashSet<IEvent>(asList(
                coastGuardDataFactory.createEventMap().get(coastGuardDataFactory.EVENT_ID_WHALES),
                astronautsDataFactory.createEventMap().get(astronautsDataFactory.EVENT_ID_MARS)))
        def currentEvent = coastGuardDataFactory.createEventMap().get(coastGuardDataFactory.EVENT_ID_WHALES)
        for(def event : events) {
            eventManagementService.create(event)
        }

        when: "The current event of the workgroup is requested"
        def foundEvent = workgroupManagementService.getCurrentEvent(workgroupId)

        then: "The event equals the appropriate one that was initially put into the repository"
        foundEvent == currentEvent

        cleanup:
        workgroupManagementService.delete(workgroupId)
        for(def event : events) {
            eventManagementService.delete(event.getId())
        }
    }

    def "Get the current event of a specific workgroup when the workgroup currently has no event"() {

        given: "A workgroup with the requested workgroup ID does exist"
        def workgroupId = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
        def workgroup = coastGuardDataFactory.createWorkgroupMap().get(workgroupId)
        workgroupManagementService.create(workgroup)

        and: "Another workgroup currently have a event"
        def eventId = astronautsDataFactory.EVENT_ID_MARS
        def event = astronautsDataFactory.createEventMap().get(eventId)
        eventManagementService.create(event)

        when: "The current event of the workgroup is requested"
        workgroupManagementService.getCurrentEvent(workgroupId)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)

        cleanup:
        workgroupManagementService.delete(workgroupId)
        eventManagementService.delete(eventId)
    }

    def "Get the current event of a specific workgroup when a workgroup with that ID does not exist"() {

        given: "A workgroup with the requested workgroup ID does not exist"
        def workgroupId = forestRangersDataFactory.WORKGROUP_ID_FOREST_RANGERS

        and: "Several other workgroups currently have events"
        def events = new HashSet<IEvent>(asList(
                coastGuardDataFactory.createEventMap().get(coastGuardDataFactory.EVENT_ID_WHALES),
                astronautsDataFactory.createEventMap().get(astronautsDataFactory.EVENT_ID_MARS)))
        for(def event : events) {
            eventManagementService.create(event)
        }

        when: "The current event of the workgroup is requested"
        workgroupManagementService.getCurrentEvent(workgroupId)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)

        cleanup:
        for(def event : events) {
            eventManagementService.delete(event.getId())
        }
    }

    def "Extend the duration of the current event of a specific workgroup by a valid duration"() {

        given: "A workgroup with the requested workgroup ID does exist"
        def workgroupId = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
        def workgroup = coastGuardDataFactory.createWorkgroupMap().get(workgroupId)
        workgroupManagementService.create(workgroup)

        and: "The requested workgroup and another workgroup currently have a event"
        def eventIdOfWorkgroup = coastGuardDataFactory.EVENT_ID_WHALES
        def eventIdOfOtherWorkgroup = astronautsDataFactory.EVENT_ID_MARS
        def eventOfWorkgroup = coastGuardDataFactory.createEventMap().get(eventIdOfWorkgroup)
        def eventOfOtherWorkgroup = astronautsDataFactory.createEventMap().get(eventIdOfOtherWorkgroup)
        eventManagementService.create(eventOfWorkgroup)
        eventManagementService.create(eventOfOtherWorkgroup)

        and: "The extension of the event is valid"
        def extension = Duration.ofMinutes(1)

        when: "The current event of the workgroup is extended"
        workgroupManagementService.extendCurrentEvent(workgroupId, extension)

        then: "The current event of he workgroup is now longer than it was originally"
        def extendedEvent = workgroupManagementService.getCurrentEvent(workgroupId)
        extendedEvent.getEnd() == eventOfWorkgroup.getEnd() + extension

        and: "All other events still have their original duration"
        def notExtendedEvent = eventManagementService.findOne(eventIdOfOtherWorkgroup)
        notExtendedEvent.getEnd() == eventOfOtherWorkgroup.getEnd()

        cleanup:
        workgroupManagementService.delete(workgroupId)
        eventManagementService.delete(eventIdOfWorkgroup)
        eventManagementService.delete(eventIdOfOtherWorkgroup)
    }

    def "Extend the duration of the current event of a specific workgroup so that it would be longer than the maximal duration"() {

        given: "A workgroup with the requested workgroup ID does exist"
        def workgroupId = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
        def workgroup = coastGuardDataFactory.createWorkgroupMap().get(workgroupId)
        workgroupManagementService.create(workgroup)

        and: "The requested workgroup currently has a event"
        def eventId = coastGuardDataFactory.EVENT_ID_WHALES
        def event = coastGuardDataFactory.createEventMap().get(eventId)
        eventManagementService.create(event)

        and: "The extension of the event is invalid"
        def extension = this.maxEventDuration

        when: "The current event of the workgroup is extended"
        workgroupManagementService.extendCurrentEvent(workgroupId, extension)

        then: "An exception is thrown"
        thrown(MaximalDurationReachedException)

        cleanup:
        workgroupManagementService.delete(workgroupId)
        eventManagementService.delete(eventId)
    }

    def "Extend the duration of the current event of a specific workgroup so that it would conflict with another event"() {

        given: "A workgroup with the requested workgroup ID does exist"
        def workgroupId = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
        def workgroup = coastGuardDataFactory.createWorkgroupMap().get(workgroupId)
        workgroupManagementService.create(workgroup)

        and: "The requested workgroup currently has a event alongside with a follow up event"
        def currentEventId = coastGuardDataFactory.EVENT_ID_WHALES
        def followUpEventId = coastGuardDataFactory.EVENT_ID_WHIRLPOOLS
        def currentEvent = coastGuardDataFactory.createEventMap().get(currentEventId)
        def followUpEvent = coastGuardDataFactory.createEventMap().get(followUpEventId)
        eventManagementService.create(currentEvent)
        eventManagementService.create(followUpEvent)

        and: "The extension of the event would lead to conflicts"
        def extension = Duration.between(currentEvent.getEnd(), followUpEvent.getEnd())

        when: "The current event of the workgroup is extended"
        workgroupManagementService.extendCurrentEvent(workgroupId, extension)

        then: "An exception is thrown"
        thrown(EntityConflictException)

        cleanup:
        workgroupManagementService.delete(workgroupId)
        eventManagementService.delete(currentEventId)
        eventManagementService.delete(followUpEventId)
    }

    def "Extend the duration of the current event of a specific workgroup when there is currently no event"() {

        given: "A workgroup with the requested workgroup ID does exist"
        def workgroupId = forestRangersDataFactory.WORKGROUP_ID_FOREST_RANGERS
        def workgroup = forestRangersDataFactory.createWorkgroupMap().get(workgroupId)
        workgroupManagementService.create(workgroup)

        and: "Several other workgroups currently have a event"
        def eventId1 = coastGuardDataFactory.EVENT_ID_WHALES
        def eventId2 = astronautsDataFactory.EVENT_ID_MARS
        def event1 = coastGuardDataFactory.createEventMap().get(eventId1)
        def event2 = astronautsDataFactory.createEventMap().get(eventId2)
        eventManagementService.create(event1)
        eventManagementService.create(event2)

        and: "The extension of the event is valid"
        def extension = Duration.ofMinutes(1)

        when: "The current event of the workgroup is extended"
        workgroupManagementService.extendCurrentEvent(workgroupId, extension)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)

        cleanup:
        workgroupManagementService.delete(workgroupId)
        eventManagementService.delete(eventId1)
        eventManagementService.delete(eventId2)
    }

    def "Extend the duration of the current event of a specific workgroup when a workgroup with that ID does not exist"() {

        given: "A workgroup with the requested workgroup ID does not exist"
        def workgroupId = forestRangersDataFactory.WORKGROUP_ID_FOREST_RANGERS

        and: "Several other workgroups currently have a event"
        def eventId1 = coastGuardDataFactory.EVENT_ID_WHALES
        def eventId2 = astronautsDataFactory.EVENT_ID_MARS
        def event1 = coastGuardDataFactory.createEventMap().get(eventId1)
        def event2 = astronautsDataFactory.createEventMap().get(eventId2)
        eventManagementService.create(event1)
        eventManagementService.create(event2)

        and: "The extension of the event is valid"
        def extension = Duration.ofMinutes(1)

        when: "The current event of the workgroup is extended"
        workgroupManagementService.extendCurrentEvent(workgroupId, extension)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)

        cleanup:
        eventManagementService.delete(eventId1)
        eventManagementService.delete(eventId2)
    }
}
