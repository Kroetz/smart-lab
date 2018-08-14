package de.qaware.smartlab.integrationtest.location

import de.qaware.smartlab.api.service.connector.location.ILocationManagementService
import de.qaware.smartlab.api.service.connector.event.IEventManagementService
import de.qaware.smartlab.core.data.location.ILocation
import de.qaware.smartlab.core.data.location.LocationId
import de.qaware.smartlab.core.data.location.LocationDto
import de.qaware.smartlab.core.data.event.IEvent
import de.qaware.smartlab.core.exception.data.ConflictException
import de.qaware.smartlab.core.exception.data.NotFoundException
import de.qaware.smartlab.core.exception.data.MaximalDurationReachedException

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
class LocationManagementApiIntegrationTest extends CrudApiIntegrationTest<LocationId, LocationDto, ILocation> {

    @Autowired
    private ILocationManagementService locationManagementService

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
        crudService = locationManagementService
        entitiesForFindAll_withExisting = new HashSet<>(asList(
                coastGuardDataFactory.createLocationMap().get(coastGuardDataFactory.LOCATION_ID_BLUE),
                forestRangersDataFactory.createLocationMap().get(forestRangersDataFactory.LOCATION_ID_GREEN),
                fireFightersDataFactory.createLocationMap().get(fireFightersDataFactory.LOCATION_ID_RED)))
    }

    @Override
    def setupDataForFindOne_withExisting() {
        crudService = locationManagementService
        entityForFindOne_withExisting = coastGuardDataFactory.createLocationMap().get(coastGuardDataFactory.LOCATION_ID_BLUE)
    }

    @Override
    def setupDataForFindOne_withoutExisting() {
        crudService = locationManagementService
        entityIdForFindOne_withoutExisting = coastGuardDataFactory.LOCATION_ID_BLUE
    }

    @Override
    def setupDataForFindMultiple_withExisting() {
        crudService = locationManagementService
        def locationId1 = coastGuardDataFactory.LOCATION_ID_BLUE
        def locationId2 = forestRangersDataFactory.LOCATION_ID_GREEN
        def locationId3 = fireFightersDataFactory.LOCATION_ID_RED
        def location1 = coastGuardDataFactory.createLocationMap().get(locationId1)
        def location2 = forestRangersDataFactory.createLocationMap().get(locationId2)
        def location3 = fireFightersDataFactory.createLocationMap().get(locationId3)
        allEntitiesForFindMultiple_withExisting = [location1, location2, location3]
    }

    @Override
    def setupDataForFindMultiple_withoutExisting() {
        crudService = locationManagementService
        def locationId1 = coastGuardDataFactory.LOCATION_ID_BLUE
        def locationId2 = forestRangersDataFactory.LOCATION_ID_GREEN
        def locationId3 = fireFightersDataFactory.LOCATION_ID_RED
        def location1 = coastGuardDataFactory.createLocationMap().get(locationId1)
        def location2 = forestRangersDataFactory.createLocationMap().get(locationId2)
        def location3 = fireFightersDataFactory.createLocationMap().get(locationId3)
        allEntitiesForFindMultiple_withoutExisting = [location1, location2]
        requestedEntitiesForFindMultiple_withoutExisting = [location2, location3]
    }

    @Override
    def setupDataForCreate_withoutConflict() {
        crudService = locationManagementService
        entityForCreate_withoutConflict = coastGuardDataFactory.createLocationMap().get(coastGuardDataFactory.LOCATION_ID_BLUE)
    }

    @Override
    def setupDataForCreate_withConflict() {
        crudService = locationManagementService
        entityForCreate_withConflict = coastGuardDataFactory.createLocationMap().get(coastGuardDataFactory.LOCATION_ID_BLUE)
    }

    @Override
    def setupDataForDelete_withExisting() {
        crudService = locationManagementService
        entityForDelete_withExisting = coastGuardDataFactory.createLocationMap().get(coastGuardDataFactory.LOCATION_ID_BLUE)
    }

    @Override
    def setupDataForDelete_withoutExisting() {
        crudService = locationManagementService
        entityIdForDelete_withoutExisting = coastGuardDataFactory.LOCATION_ID_BLUE
    }

    def "Get a set of all events at a specific location when there are events"() {

        given: "A location with the requested location ID does exist"
        def locationId = coastGuardDataFactory.LOCATION_ID_BLUE
        def location = coastGuardDataFactory.createLocationMap().get(locationId)
        locationManagementService.create(location)

        and: "There are events at the requested location and other locations"
        def events = new HashSet<IEvent>(asList(
                coastGuardDataFactory.createEventMap().get(coastGuardDataFactory.EVENT_ID_WHALES),
                coastGuardDataFactory.createEventMap().get(coastGuardDataFactory.EVENT_ID_WHIRLPOOLS),
                forestRangersDataFactory.createEventMap().get(forestRangersDataFactory.EVENT_ID_BARK_BEETLE),
                fireFightersDataFactory.createEventMap().get(fireFightersDataFactory.EVENT_ID_TRUCK)))
        def eventsAtLocation = new HashSet<IEvent>(asList(
                coastGuardDataFactory.createEventMap().get(coastGuardDataFactory.EVENT_ID_WHALES),
                coastGuardDataFactory.createEventMap().get(coastGuardDataFactory.EVENT_ID_WHIRLPOOLS)))
        for(def event : events) {
            eventManagementService.create(event)
        }

        when: "The set of events at the location is requested"
        def foundEvents = locationManagementService.getEventsAtLocation(locationId)

        then: "The returned set equals the appropriate part of that one that was used to populate the repository"
        foundEvents == eventsAtLocation

        cleanup:
        locationManagementService.delete(locationId)
        for(def event : events) {
            eventManagementService.delete(event.getId())
        }
    }

    def "Get a set of all events at a specific location when there are no events"() {

        given: "A location with the requested location ID does exist"
        def locationId = coastGuardDataFactory.LOCATION_ID_BLUE
        def location = coastGuardDataFactory.createLocationMap().get(locationId)
        locationManagementService.create(location)

        and: "There are only events at other locations than the requested one"
        def events = new HashSet<IEvent>(asList(
                forestRangersDataFactory.createEventMap().get(forestRangersDataFactory.EVENT_ID_BARK_BEETLE),
                fireFightersDataFactory.createEventMap().get(fireFightersDataFactory.EVENT_ID_TRUCK)))
        def eventsAtLocation = new HashSet<IEvent>()
        for(def event : events) {
            eventManagementService.create(event)
        }

        when: "The set of events at the location is requested"
        def foundEvents = locationManagementService.getEventsAtLocation(locationId)

        then: "The returned set is empty"
        foundEvents == eventsAtLocation

        cleanup:
        locationManagementService.delete(locationId)
        for(def event : events) {
            eventManagementService.delete(event.getId())
        }
    }

    def "Get a set of all events at a specific location when a location with that ID does not exist"() {

        given: "A location with the requested location ID does not exist"
        def locationId = coastGuardDataFactory.LOCATION_ID_BLUE

        and: "There are events at several locations"
        def events = new HashSet<IEvent>(asList(
                coastGuardDataFactory.createEventMap().get(coastGuardDataFactory.EVENT_ID_WHALES),
                coastGuardDataFactory.createEventMap().get(coastGuardDataFactory.EVENT_ID_WHIRLPOOLS),
                forestRangersDataFactory.createEventMap().get(forestRangersDataFactory.EVENT_ID_BARK_BEETLE),
                fireFightersDataFactory.createEventMap().get(fireFightersDataFactory.EVENT_ID_TRUCK)))
        for(def event : events) {
            eventManagementService.create(event)
        }

        when: "The set of events at the location is requested"
        locationManagementService.getEventsAtLocation(locationId)

        then: "An exception is thrown"
        thrown(NotFoundException)

        cleanup:
        for(def event : events) {
            eventManagementService.delete(event.getId())
        }
    }

    def "Get the current event at a specific location when there is currently an event"() {

        given: "A location with the requested location ID does exist"
        def locationId = coastGuardDataFactory.LOCATION_ID_BLUE
        def location = coastGuardDataFactory.createLocationMap().get(locationId)
        locationManagementService.create(location)

        and: "There is currently an event at the requested location and another location"
        def events = new HashSet<IEvent>(asList(
                coastGuardDataFactory.createEventMap().get(coastGuardDataFactory.EVENT_ID_WHALES),
                astronautsDataFactory.createEventMap().get(astronautsDataFactory.EVENT_ID_MARS)))
        def currentEvent = coastGuardDataFactory.createEventMap().get(coastGuardDataFactory.EVENT_ID_WHALES)
        for(def event : events) {
            eventManagementService.create(event)
        }

        when: "The current event at the location is requested"
        def foundEvent = locationManagementService.getCurrentEvent(locationId)

        then: "The event equals the appropriate one that was initially put into the repository"
        foundEvent == currentEvent

        cleanup:
        locationManagementService.delete(locationId)
        for(def event : events) {
            eventManagementService.delete(event.getId())
        }
    }

    def "Get the current event at a specific location when there is currently no event"() {

        given: "A location with the requested location ID does exist"
        def locationId = coastGuardDataFactory.LOCATION_ID_BLUE
        def location = coastGuardDataFactory.createLocationMap().get(locationId)
        locationManagementService.create(location)

        and: "There is currently an event at another location"
        def eventId = astronautsDataFactory.EVENT_ID_MARS
        def event = astronautsDataFactory.createEventMap().get(eventId)
        eventManagementService.create(event)

        when: "The current event at the location is requested"
        locationManagementService.getCurrentEvent(locationId)

        then: "An exception is thrown"
        thrown(NotFoundException)

        cleanup:
        locationManagementService.delete(locationId)
        eventManagementService.delete(eventId)
    }

    def "Get the current event at a specific location when a location with that ID does not exist"() {

        given: "A location with the requested location ID does not exist"
        def locationId = forestRangersDataFactory.LOCATION_ID_GREEN

        and: "There are currently events at several locations"
        def events = new HashSet<IEvent>(asList(
                coastGuardDataFactory.createEventMap().get(coastGuardDataFactory.EVENT_ID_WHALES),
                astronautsDataFactory.createEventMap().get(astronautsDataFactory.EVENT_ID_MARS)))
        for(def event : events) {
            eventManagementService.create(event)
        }

        when: "The current event at the location is requested"
        locationManagementService.getCurrentEvent(locationId)

        then: "An exception is thrown"
        thrown(NotFoundException)

        cleanup:
        for(def event : events) {
            eventManagementService.delete(event.getId())
        }
    }

    def "Extend the duration of the current event at a specific location by a valid duration"() {

        given: "A location with the requested location ID does exist"
        def locationId = coastGuardDataFactory.LOCATION_ID_BLUE
        def location = coastGuardDataFactory.createLocationMap().get(locationId)
        locationManagementService.create(location)

        and: "There is currently an event at the requested location and another location"
        def eventIdAtLocation = coastGuardDataFactory.EVENT_ID_WHALES
        def eventIdAtOtherLocation = astronautsDataFactory.EVENT_ID_MARS
        def eventAtLocation = coastGuardDataFactory.createEventMap().get(eventIdAtLocation)
        def eventAtOtherLocation = astronautsDataFactory.createEventMap().get(eventIdAtOtherLocation)
        eventManagementService.create(eventAtLocation)
        eventManagementService.create(eventAtOtherLocation)

        and: "The extension of the event is valid"
        def extension = ofMinutes(1)

        when: "The current event at the location is extended"
        locationManagementService.extendCurrentEvent(locationId, extension)

        then: "The current event at the location is now longer than it was originally"
        def extendedEvent = locationManagementService.getCurrentEvent(locationId)
        extendedEvent.getEnd() == eventAtLocation.getEnd() + extension

        and: "All other events still have their original duration"
        def notExtendedEvent = eventManagementService.findOne(eventIdAtOtherLocation)
        notExtendedEvent.getEnd() == eventAtOtherLocation.getEnd()

        cleanup:
        locationManagementService.delete(locationId)
        eventManagementService.delete(eventIdAtLocation)
        eventManagementService.delete(eventIdAtOtherLocation)
    }

    def "Extend the duration of the current event at a specific location so that it would be longer than the maximal duration"() {

        given: "A location with the requested location ID does exist"
        def locationId = coastGuardDataFactory.LOCATION_ID_BLUE
        def location = coastGuardDataFactory.createLocationMap().get(locationId)
        locationManagementService.create(location)

        and: "There is currently an event at the requested location"
        def eventId = coastGuardDataFactory.EVENT_ID_WHALES
        def event = coastGuardDataFactory.createEventMap().get(eventId)
        eventManagementService.create(event)

        and: "The extension of the event is invalid"
        def extension = this.maxEventDuration

        when: "The current event at the location is extended"
        locationManagementService.extendCurrentEvent(locationId, extension)

        then: "An exception is thrown"
        thrown(MaximalDurationReachedException)

        cleanup:
        locationManagementService.delete(locationId)
        eventManagementService.delete(eventId)
    }

    def "Extend the duration of the current event at a specific location so that it would conflict with another event"() {

        given: "A location with the requested location ID does exist"
        def locationId = coastGuardDataFactory.LOCATION_ID_BLUE
        def location = coastGuardDataFactory.createLocationMap().get(locationId)
        locationManagementService.create(location)

        and: "There is currently an event at the requested location alongside with a follow up event"
        def currentEventId = coastGuardDataFactory.EVENT_ID_WHALES
        def followUpEventId = coastGuardDataFactory.EVENT_ID_WHIRLPOOLS
        def currentEvent = coastGuardDataFactory.createEventMap().get(currentEventId)
        def followUpEvent = coastGuardDataFactory.createEventMap().get(followUpEventId)
        eventManagementService.create(currentEvent)
        eventManagementService.create(followUpEvent)

        and: "The extension of the event would lead to conflicts"
        def extension = between(currentEvent.getEnd(), followUpEvent.getEnd())

        when: "The current event at the location is extended"
        locationManagementService.extendCurrentEvent(locationId, extension)

        then: "An exception is thrown"
        thrown(ConflictException)

        cleanup:
        locationManagementService.delete(locationId)
        eventManagementService.delete(currentEventId)
        eventManagementService.delete(followUpEventId)
    }

    def "Extend the duration of the current event at a specific location when there is currently no event"() {

        given: "A location with the requested location ID does exist"
        def locationId = forestRangersDataFactory.LOCATION_ID_GREEN
        def location = forestRangersDataFactory.createLocationMap().get(locationId)
        locationManagementService.create(location)

        and: "There are currently event at several other locations"
        def eventId1 = coastGuardDataFactory.EVENT_ID_WHALES
        def eventId2 = astronautsDataFactory.EVENT_ID_MARS
        def event1 = coastGuardDataFactory.createEventMap().get(eventId1)
        def event2 = astronautsDataFactory.createEventMap().get(eventId2)
        eventManagementService.create(event1)
        eventManagementService.create(event2)

        and: "The extension of the event is valid"
        def extension = ofMinutes(1)

        when: "The current event at the location is extended"
        locationManagementService.extendCurrentEvent(locationId, extension)

        then: "An exception is thrown"
        thrown(NotFoundException)

        cleanup:
        locationManagementService.delete(locationId)
        eventManagementService.delete(eventId1)
        eventManagementService.delete(eventId2)
    }

    def "Extend the duration of the current event at a specific location when a location with that ID does not exist"() {

        given: "A location with the requested location ID does not exist"
        def locationId = forestRangersDataFactory.LOCATION_ID_GREEN

        and: "There are currently event at several other locations"
        def eventId1 = coastGuardDataFactory.EVENT_ID_WHALES
        def eventId2 = astronautsDataFactory.EVENT_ID_MARS
        def event1 = coastGuardDataFactory.createEventMap().get(eventId1)
        def event2 = astronautsDataFactory.createEventMap().get(eventId2)
        eventManagementService.create(event1)
        eventManagementService.create(event2)

        and: "The extension of the event is valid"
        def extension = ofMinutes(1)

        when: "The current event at the location is extended"
        locationManagementService.extendCurrentEvent(locationId, extension)

        then: "An exception is thrown"
        thrown(NotFoundException)

        cleanup:
        eventManagementService.delete(eventId1)
        eventManagementService.delete(eventId2)
    }
}
