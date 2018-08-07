package de.qaware.smartlab.integrationtest.location

import de.qaware.smartlab.api.service.connector.location.ILocationManagementService
import de.qaware.smartlab.api.service.connector.meeting.IMeetingManagementService
import de.qaware.smartlab.core.data.location.ILocation
import de.qaware.smartlab.core.data.location.LocationId
import de.qaware.smartlab.core.data.location.LocationDto
import de.qaware.smartlab.core.data.meeting.IMeeting
import de.qaware.smartlab.core.exception.EntityConflictException
import de.qaware.smartlab.core.exception.EntityNotFoundException
import de.qaware.smartlab.core.exception.MaximalDurationReachedException

import de.qaware.smartlab.data.set.factory.AstronautsSampleDataSetFactory
import de.qaware.smartlab.data.set.factory.CoastGuardSampleDataSetFactory
import de.qaware.smartlab.data.set.factory.FireFightersSampleDataSetFactory
import de.qaware.smartlab.data.set.factory.ForestRangersSampleDataSetFactory
import de.qaware.smartlab.integrationtest.generic.CrudApiIntegrationTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import java.time.Duration

import static java.util.Arrays.asList

@SpringBootTest
class LocationManagementApiIntegrationTest extends CrudApiIntegrationTest<LocationId, LocationDto, ILocation> {

    @Autowired
    private ILocationManagementService locationManagementService

    @Autowired
    private IMeetingManagementService meetingManagementService

    @Autowired
    private CoastGuardSampleDataSetFactory coastGuardDataFactory

    @Autowired
    private ForestRangersSampleDataSetFactory forestRangersDataFactory

    @Autowired
    private FireFightersSampleDataSetFactory fireFightersDataFactory

    @Autowired
    private AstronautsSampleDataSetFactory astronautsDataFactory

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

    def "Get a set of all meetings at a specific location when there are meetings"() {

        given: "A location with the requested location ID does exist"
        def locationId = coastGuardDataFactory.LOCATION_ID_BLUE
        def location = coastGuardDataFactory.createLocationMap().get(locationId)
        locationManagementService.create(location)

        and: "There are meetings at the requested location and other locations"
        def meetings = new HashSet<IMeeting>(asList(
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES),
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHIRLPOOLS),
                forestRangersDataFactory.createMeetingMap().get(forestRangersDataFactory.MEETING_ID_BARK_BEETLE),
                fireFightersDataFactory.createMeetingMap().get(fireFightersDataFactory.MEETING_ID_TRUCK)))
        def meetingsAtLocation = new HashSet<IMeeting>(asList(
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES),
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHIRLPOOLS)))
        for(def meeting : meetings) {
            meetingManagementService.create(meeting)
        }

        when: "The set of meetings at the location is requested"
        def foundMeetings = locationManagementService.getMeetingsAtLocation(locationId)

        then: "The returned set equals the appropriate part of that one that was used to populate the repository"
        foundMeetings == meetingsAtLocation

        cleanup:
        locationManagementService.delete(locationId)
        for(def meeting : meetings) {
            meetingManagementService.delete(meeting.getId())
        }
    }

    def "Get a set of all meetings at a specific location when there are no meetings"() {

        given: "A location with the requested location ID does exist"
        def locationId = coastGuardDataFactory.LOCATION_ID_BLUE
        def location = coastGuardDataFactory.createLocationMap().get(locationId)
        locationManagementService.create(location)

        and: "There are only meetings at other locations than the requested one"
        def meetings = new HashSet<IMeeting>(asList(
                forestRangersDataFactory.createMeetingMap().get(forestRangersDataFactory.MEETING_ID_BARK_BEETLE),
                fireFightersDataFactory.createMeetingMap().get(fireFightersDataFactory.MEETING_ID_TRUCK)))
        def meetingsAtLocation = new HashSet<IMeeting>()
        for(def meeting : meetings) {
            meetingManagementService.create(meeting)
        }

        when: "The set of meetings at the location is requested"
        def foundMeetings = locationManagementService.getMeetingsAtLocation(locationId)

        then: "The returned set is empty"
        foundMeetings == meetingsAtLocation

        cleanup:
        locationManagementService.delete(locationId)
        for(def meeting : meetings) {
            meetingManagementService.delete(meeting.getId())
        }
    }

    def "Get a set of all meetings at a specific location when a location with that ID does not exist"() {

        given: "A location with the requested location ID does not exist"
        def locationId = coastGuardDataFactory.LOCATION_ID_BLUE

        and: "There are meetings at several locations"
        def meetings = new HashSet<IMeeting>(asList(
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES),
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHIRLPOOLS),
                forestRangersDataFactory.createMeetingMap().get(forestRangersDataFactory.MEETING_ID_BARK_BEETLE),
                fireFightersDataFactory.createMeetingMap().get(fireFightersDataFactory.MEETING_ID_TRUCK)))
        for(def meeting : meetings) {
            meetingManagementService.create(meeting)
        }

        when: "The set of meetings at the location is requested"
        locationManagementService.getMeetingsAtLocation(locationId)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)

        cleanup:
        for(def meeting : meetings) {
            meetingManagementService.delete(meeting.getId())
        }
    }

    def "Get the current meeting at a specific location when there is currently a meeting"() {

        given: "A location with the requested location ID does exist"
        def locationId = coastGuardDataFactory.LOCATION_ID_BLUE
        def location = coastGuardDataFactory.createLocationMap().get(locationId)
        locationManagementService.create(location)

        and: "There is currently a meeting at the requested location and another location"
        def meetings = new HashSet<IMeeting>(asList(
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES),
                astronautsDataFactory.createMeetingMap().get(astronautsDataFactory.MEETING_ID_MARS)))
        def currentMeeting = coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES)
        for(def meeting : meetings) {
            meetingManagementService.create(meeting)
        }

        when: "The current meeting at the location is requested"
        def foundMeeting = locationManagementService.getCurrentMeeting(locationId)

        then: "The meeting equals the appropriate one that was initially put into the repository"
        foundMeeting == currentMeeting

        cleanup:
        locationManagementService.delete(locationId)
        for(def meeting : meetings) {
            meetingManagementService.delete(meeting.getId())
        }
    }

    def "Get the current meeting at a specific location when there is currently no meeting"() {

        given: "A location with the requested location ID does exist"
        def locationId = coastGuardDataFactory.LOCATION_ID_BLUE
        def location = coastGuardDataFactory.createLocationMap().get(locationId)
        locationManagementService.create(location)

        and: "There is currently a meeting at another location"
        def meetingId = astronautsDataFactory.MEETING_ID_MARS
        def meeting = astronautsDataFactory.createMeetingMap().get(meetingId)
        meetingManagementService.create(meeting)

        when: "The current meeting at the location is requested"
        locationManagementService.getCurrentMeeting(locationId)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)

        cleanup:
        locationManagementService.delete(locationId)
        meetingManagementService.delete(meetingId)
    }

    def "Get the current meeting at a specific location when a location with that ID does not exist"() {

        given: "A location with the requested location ID does not exist"
        def locationId = forestRangersDataFactory.LOCATION_ID_GREEN

        and: "There are currently meetings at several locations"
        def meetings = new HashSet<IMeeting>(asList(
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES),
                astronautsDataFactory.createMeetingMap().get(astronautsDataFactory.MEETING_ID_MARS)))
        for(def meeting : meetings) {
            meetingManagementService.create(meeting)
        }

        when: "The current meeting at the location is requested"
        locationManagementService.getCurrentMeeting(locationId)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)

        cleanup:
        for(def meeting : meetings) {
            meetingManagementService.delete(meeting.getId())
        }
    }

    def "Extend the duration of the current meeting at a specific location by a valid duration"() {

        given: "A location with the requested location ID does exist"
        def locationId = coastGuardDataFactory.LOCATION_ID_BLUE
        def location = coastGuardDataFactory.createLocationMap().get(locationId)
        locationManagementService.create(location)

        and: "There is currently a meeting at the requested location and another location"
        def meetingIdAtLocation = coastGuardDataFactory.MEETING_ID_WHALES
        def meetingIdAtOtherLocation = astronautsDataFactory.MEETING_ID_MARS
        def meetingAtLocation = coastGuardDataFactory.createMeetingMap().get(meetingIdAtLocation)
        def meetingAtOtherLocation = astronautsDataFactory.createMeetingMap().get(meetingIdAtOtherLocation)
        meetingManagementService.create(meetingAtLocation)
        meetingManagementService.create(meetingAtOtherLocation)

        and: "The extension of the meeting is valid"
        def extension = Duration.ofMinutes(1)

        when: "The current meeting at the location is extended"
        locationManagementService.extendCurrentMeeting(locationId, extension)

        then: "The current meeting at the location is now longer than it was originally"
        def extendedMeeting = locationManagementService.getCurrentMeeting(locationId)
        extendedMeeting.getEnd() == meetingAtLocation.getEnd() + extension

        and: "All other meetings still have their original duration"
        def notExtendedMeeting = meetingManagementService.findOne(meetingIdAtOtherLocation)
        notExtendedMeeting.getEnd() == meetingAtOtherLocation.getEnd()

        cleanup:
        locationManagementService.delete(locationId)
        meetingManagementService.delete(meetingIdAtLocation)
        meetingManagementService.delete(meetingIdAtOtherLocation)
    }

    def "Extend the duration of the current meeting at a specific location so that it would be longer than the maximal duration"() {

        given: "A location with the requested location ID does exist"
        def locationId = coastGuardDataFactory.LOCATION_ID_BLUE
        def location = coastGuardDataFactory.createLocationMap().get(locationId)
        locationManagementService.create(location)

        and: "There is currently a meeting at the requested location"
        def meetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def meeting = coastGuardDataFactory.createMeetingMap().get(meetingId)
        meetingManagementService.create(meeting)

        and: "The extension of the meeting is invalid"
        def extension = Constants.MAXIMAL_MEETING_DURATION

        when: "The current meeting at the location is extended"
        locationManagementService.extendCurrentMeeting(locationId, extension)

        then: "An exception is thrown"
        thrown(MaximalDurationReachedException)

        cleanup:
        locationManagementService.delete(locationId)
        meetingManagementService.delete(meetingId)
    }

    def "Extend the duration of the current meeting at a specific location so that it would conflict with another meeting"() {

        given: "A location with the requested location ID does exist"
        def locationId = coastGuardDataFactory.LOCATION_ID_BLUE
        def location = coastGuardDataFactory.createLocationMap().get(locationId)
        locationManagementService.create(location)

        and: "There is currently a meeting at the requested location alongside with a follow up meeting"
        def currentMeetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def followUpMeetingId = coastGuardDataFactory.MEETING_ID_WHIRLPOOLS
        def currentMeeting = coastGuardDataFactory.createMeetingMap().get(currentMeetingId)
        def followUpMeeting = coastGuardDataFactory.createMeetingMap().get(followUpMeetingId)
        meetingManagementService.create(currentMeeting)
        meetingManagementService.create(followUpMeeting)

        and: "The extension of the meeting would lead to conflicts"
        def extension = Duration.between(currentMeeting.getEnd(), followUpMeeting.getEnd())

        when: "The current meeting at the location is extended"
        locationManagementService.extendCurrentMeeting(locationId, extension)

        then: "An exception is thrown"
        thrown(EntityConflictException)

        cleanup:
        locationManagementService.delete(locationId)
        meetingManagementService.delete(currentMeetingId)
        meetingManagementService.delete(followUpMeetingId)
    }

    def "Extend the duration of the current meeting at a specific location when there is currently no meeting"() {

        given: "A location with the requested location ID does exist"
        def locationId = forestRangersDataFactory.LOCATION_ID_GREEN
        def location = forestRangersDataFactory.createLocationMap().get(locationId)
        locationManagementService.create(location)

        and: "There are currently meeting at several other locations"
        def meetingId1 = coastGuardDataFactory.MEETING_ID_WHALES
        def meetingId2 = astronautsDataFactory.MEETING_ID_MARS
        def meeting1 = coastGuardDataFactory.createMeetingMap().get(meetingId1)
        def meeting2 = astronautsDataFactory.createMeetingMap().get(meetingId2)
        meetingManagementService.create(meeting1)
        meetingManagementService.create(meeting2)

        and: "The extension of the meeting is valid"
        def extension = Duration.ofMinutes(1)

        when: "The current meeting at the location is extended"
        locationManagementService.extendCurrentMeeting(locationId, extension)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)

        cleanup:
        locationManagementService.delete(locationId)
        meetingManagementService.delete(meetingId1)
        meetingManagementService.delete(meetingId2)
    }

    def "Extend the duration of the current meeting at a specific location when a location with that ID does not exist"() {

        given: "A location with the requested location ID does not exist"
        def locationId = forestRangersDataFactory.LOCATION_ID_GREEN

        and: "There are currently meeting at several other locations"
        def meetingId1 = coastGuardDataFactory.MEETING_ID_WHALES
        def meetingId2 = astronautsDataFactory.MEETING_ID_MARS
        def meeting1 = coastGuardDataFactory.createMeetingMap().get(meetingId1)
        def meeting2 = astronautsDataFactory.createMeetingMap().get(meetingId2)
        meetingManagementService.create(meeting1)
        meetingManagementService.create(meeting2)

        and: "The extension of the meeting is valid"
        def extension = Duration.ofMinutes(1)

        when: "The current meeting at the location is extended"
        locationManagementService.extendCurrentMeeting(locationId, extension)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)

        cleanup:
        meetingManagementService.delete(meetingId1)
        meetingManagementService.delete(meetingId2)
    }
}
