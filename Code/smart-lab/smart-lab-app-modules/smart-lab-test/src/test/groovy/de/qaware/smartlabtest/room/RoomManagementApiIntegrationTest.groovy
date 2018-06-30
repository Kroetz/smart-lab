package de.qaware.smartlabtest.room

import de.qaware.smartlabapi.service.meeting.IMeetingManagementService
import de.qaware.smartlabapi.service.room.IRoomManagementService
import de.qaware.smartlabcore.data.meeting.IMeeting
import de.qaware.smartlabcore.data.room.IRoom
import de.qaware.smartlabcore.data.room.RoomId
import de.qaware.smartlabcore.exception.EntityNotFoundException
import de.qaware.smartlabcore.exception.MaximalDurationReachedException
import de.qaware.smartlabcore.exception.EntityConflictException
import de.qaware.smartlabcore.miscellaneous.Constants
import de.qaware.smartlabsampledata.factory.AstronautsDataFactory
import de.qaware.smartlabsampledata.factory.CoastGuardDataFactory
import de.qaware.smartlabsampledata.factory.FireFightersDataFactory
import de.qaware.smartlabsampledata.factory.ForestRangersDataFactory
import de.qaware.smartlabtest.generic.CrudApiIntegrationTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import java.time.Duration

@SpringBootTest
class RoomManagementApiIntegrationTest extends CrudApiIntegrationTest<RoomId, IRoom> {

    @Autowired
    private IRoomManagementService roomManagementService

    @Autowired
    private IMeetingManagementService meetingManagementService

    @Autowired
    private CoastGuardDataFactory coastGuardDataFactory

    @Autowired
    private ForestRangersDataFactory forestRangersDataFactory

    @Autowired
    private FireFightersDataFactory fireFightersDataFactory

    @Autowired
    private AstronautsDataFactory astronautsDataFactory

    @Override
    def setupDataForFindAll_withExisting() {
        crudService = roomManagementService
        entitiesForFindAll_withExisting = new HashSet<>(Arrays.asList(
                coastGuardDataFactory.createRoomMap().get(coastGuardDataFactory.ROOM_ID_BLUE),
                forestRangersDataFactory.createRoomMap().get(forestRangersDataFactory.ROOM_ID_GREEN),
                fireFightersDataFactory.createRoomMap().get(fireFightersDataFactory.ROOM_ID_RED)))
    }

    @Override
    def setupDataForFindOne_withExisting() {
        crudService = roomManagementService
        entityForFindOne_withExisting = coastGuardDataFactory.createRoomMap().get(coastGuardDataFactory.ROOM_ID_BLUE)
    }

    @Override
    def setupDataForFindOne_withoutExisting() {
        crudService = roomManagementService
        entityIdForFindOne_withoutExisting = coastGuardDataFactory.ROOM_ID_BLUE
    }

    @Override
    def setupDataForFindMultiple_withExisting() {
        crudService = roomManagementService
        def roomId1 = coastGuardDataFactory.ROOM_ID_BLUE
        def roomId2 = forestRangersDataFactory.ROOM_ID_GREEN
        def roomId3 = fireFightersDataFactory.ROOM_ID_RED
        def room1 = coastGuardDataFactory.createRoomMap().get(roomId1)
        def room2 = forestRangersDataFactory.createRoomMap().get(roomId2)
        def room3 = fireFightersDataFactory.createRoomMap().get(roomId3)
        allEntitiesForFindMultiple_withExisting = [room1, room2, room3]
        requestedEntitiesForFindMultiple_withExisting = [room1, room2]
    }

    @Override
    def setupDataForFindMultiple_withoutExisting() {
        crudService = roomManagementService
        def roomId1 = coastGuardDataFactory.ROOM_ID_BLUE
        def roomId2 = forestRangersDataFactory.ROOM_ID_GREEN
        def roomId3 = fireFightersDataFactory.ROOM_ID_RED
        def room1 = coastGuardDataFactory.createRoomMap().get(roomId1)
        def room2 = forestRangersDataFactory.createRoomMap().get(roomId2)
        def room3 = fireFightersDataFactory.createRoomMap().get(roomId3)
        allEntitiesForFindMultiple_withoutExisting = [room1, room2]
        requestedEntitiesForFindMultiple_withoutExisting = [room2, room3]
    }

    @Override
    def setupDataForCreate_withoutConflict() {
        crudService = roomManagementService
        entityForCreate_withoutConflict = coastGuardDataFactory.createRoomMap().get(coastGuardDataFactory.ROOM_ID_BLUE)
    }

    @Override
    def setupDataForCreate_withConflict() {
        crudService = roomManagementService
        entityForCreate_withConflict = coastGuardDataFactory.createRoomMap().get(coastGuardDataFactory.ROOM_ID_BLUE)
    }

    @Override
    def setupDataForDelete_withExisting() {
        crudService = roomManagementService
        entityForDelete_withExisting = coastGuardDataFactory.createRoomMap().get(coastGuardDataFactory.ROOM_ID_BLUE)
    }

    @Override
    def setupDataForDelete_withoutExisting() {
        crudService = roomManagementService
        entityIdForDelete_withoutExisting = coastGuardDataFactory.ROOM_ID_BLUE
    }

    def "Get a set of all meetings in a specific room when there are meetings"() {

        given: "A room with the requested room ID does exist"
        def roomId = coastGuardDataFactory.ROOM_ID_BLUE
        def room = coastGuardDataFactory.createRoomMap().get(roomId)
        roomManagementService.create(room)

        and: "There are meetings in the requested room and other rooms"
        def meetings = new HashSet<IMeeting>(Arrays.asList(
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES),
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHIRLPOOLS),
                forestRangersDataFactory.createMeetingMap().get(forestRangersDataFactory.MEETING_ID_BARK_BEETLE),
                fireFightersDataFactory.createMeetingMap().get(fireFightersDataFactory.MEETING_ID_TRUCK)))
        def meetingsInRoom = new HashSet<IMeeting>(Arrays.asList(
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES),
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHIRLPOOLS)))
        for(def meeting : meetings) {
            meetingManagementService.create(meeting)
        }

        when: "The set of meetings in the room is requested"
        def foundMeetings = roomManagementService.getMeetingsInRoom(roomId)

        then: "The returned set equals the appropriate part of that one that was used to populate the repository"
        foundMeetings == meetingsInRoom

        cleanup:
        roomManagementService.delete(roomId)
        for(def meeting : meetings) {
            meetingManagementService.delete(meeting.getId())
        }
    }

    def "Get a set of all meetings in a specific room when there are no meetings"() {

        given: "A room with the requested room ID does exist"
        def roomId = coastGuardDataFactory.ROOM_ID_BLUE
        def room = coastGuardDataFactory.createRoomMap().get(roomId)
        roomManagementService.create(room)

        and: "There are only meetings in other rooms than the requested one"
        def meetings = new HashSet<IMeeting>(Arrays.asList(
                forestRangersDataFactory.createMeetingMap().get(forestRangersDataFactory.MEETING_ID_BARK_BEETLE),
                fireFightersDataFactory.createMeetingMap().get(fireFightersDataFactory.MEETING_ID_TRUCK)))
        def meetingsInRoom = new HashSet<IMeeting>()
        for(def meeting : meetings) {
            meetingManagementService.create(meeting)
        }

        when: "The set of meetings in the room is requested"
        def foundMeetings = roomManagementService.getMeetingsInRoom(roomId)

        then: "The returned set is empty"
        foundMeetings == meetingsInRoom

        cleanup:
        roomManagementService.delete(roomId)
        for(def meeting : meetings) {
            meetingManagementService.delete(meeting.getId())
        }
    }

    def "Get a set of all meetings in a specific room when a room with that ID does not exist"() {

        given: "A room with the requested room ID does not exist"
        def roomId = coastGuardDataFactory.ROOM_ID_BLUE

        and: "There are meetings in several rooms"
        def meetings = new HashSet<IMeeting>(Arrays.asList(
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES),
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHIRLPOOLS),
                forestRangersDataFactory.createMeetingMap().get(forestRangersDataFactory.MEETING_ID_BARK_BEETLE),
                fireFightersDataFactory.createMeetingMap().get(fireFightersDataFactory.MEETING_ID_TRUCK)))
        for(def meeting : meetings) {
            meetingManagementService.create(meeting)
        }

        when: "The set of meetings in the room is requested"
        roomManagementService.getMeetingsInRoom(roomId)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)

        cleanup:
        for(def meeting : meetings) {
            meetingManagementService.delete(meeting.getId())
        }
    }

    def "Get the current meeting in a specific room when there is currently a meeting"() {

        given: "A room with the requested room ID does exist"
        def roomId = coastGuardDataFactory.ROOM_ID_BLUE
        def room = coastGuardDataFactory.createRoomMap().get(roomId)
        roomManagementService.create(room)

        and: "There is currently a meeting in the requested room and another room"
        def meetings = new HashSet<IMeeting>(Arrays.asList(
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES),
                astronautsDataFactory.createMeetingMap().get(astronautsDataFactory.MEETING_ID_MARS)))
        def currentMeeting = coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES)
        for(def meeting : meetings) {
            meetingManagementService.create(meeting)
        }

        when: "The current meeting in the room is requested"
        def foundMeeting = roomManagementService.getCurrentMeeting(roomId)

        then: "The meeting equals the appropriate one that was initially put into the repository"
        foundMeeting == currentMeeting

        cleanup:
        roomManagementService.delete(roomId)
        for(def meeting : meetings) {
            meetingManagementService.delete(meeting.getId())
        }
    }

    def "Get the current meeting in a specific room when there is currently no meeting"() {

        given: "A room with the requested room ID does exist"
        def roomId = coastGuardDataFactory.ROOM_ID_BLUE
        def room = coastGuardDataFactory.createRoomMap().get(roomId)
        roomManagementService.create(room)

        and: "There is currently a meeting in another room"
        def meetingId = astronautsDataFactory.MEETING_ID_MARS
        def meeting = astronautsDataFactory.createMeetingMap().get(meetingId)
        meetingManagementService.create(meeting)

        when: "The current meeting in the room is requested"
        roomManagementService.getCurrentMeeting(roomId)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)

        cleanup:
        roomManagementService.delete(roomId)
        meetingManagementService.delete(meetingId)
    }

    def "Get the current meeting in a specific room when a room with that ID does not exist"() {

        given: "A room with the requested room ID does not exist"
        def roomId = forestRangersDataFactory.ROOM_ID_GREEN

        and: "There are currently meetings in several room"
        def meetings = new HashSet<IMeeting>(Arrays.asList(
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES),
                astronautsDataFactory.createMeetingMap().get(astronautsDataFactory.MEETING_ID_MARS)))
        for(def meeting : meetings) {
            meetingManagementService.create(meeting)
        }

        when: "The current meeting in the room is requested"
        roomManagementService.getCurrentMeeting(roomId)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)

        cleanup:
        for(def meeting : meetings) {
            meetingManagementService.delete(meeting.getId())
        }
    }

    def "Extend the duration of the current meeting in a specific room by a valid duration"() {

        given: "A room with the requested room ID does exist"
        def roomId = coastGuardDataFactory.ROOM_ID_BLUE
        def room = coastGuardDataFactory.createRoomMap().get(roomId)
        roomManagementService.create(room)

        and: "There is currently a meeting in the requested room and another room"
        def meetingIdInRoom = coastGuardDataFactory.MEETING_ID_WHALES
        def meetingIdInOtherRoom = astronautsDataFactory.MEETING_ID_MARS
        def meetingInRoom = coastGuardDataFactory.createMeetingMap().get(meetingIdInRoom)
        def meetingInOtherRoom = astronautsDataFactory.createMeetingMap().get(meetingIdInOtherRoom)
        meetingManagementService.create(meetingInRoom)
        meetingManagementService.create(meetingInOtherRoom)

        and: "The extension of the meeting is valid"
        def extension = Duration.ofMinutes(1)

        when: "The current meeting in the room is extended"
        roomManagementService.extendCurrentMeeting(roomId, extension)

        then: "The current meeting in the room is now longer than it was originally"
        def extendedMeeting = roomManagementService.getCurrentMeeting(roomId)
        extendedMeeting.getEnd() == meetingInRoom.getEnd() + extension

        and: "All other meetings still have their original duration"
        def notExtendedMeeting = meetingManagementService.findOne(meetingIdInOtherRoom)
        notExtendedMeeting.getEnd() == meetingInOtherRoom.getEnd()

        cleanup:
        roomManagementService.delete(roomId)
        meetingManagementService.delete(meetingIdInRoom)
        meetingManagementService.delete(meetingIdInOtherRoom)
    }

    def "Extend the duration of the current meeting in a specific room so that it would be longer than the maximal duration"() {

        given: "A room with the requested room ID does exist"
        def roomId = coastGuardDataFactory.ROOM_ID_BLUE
        def room = coastGuardDataFactory.createRoomMap().get(roomId)
        roomManagementService.create(room)

        and: "There is currently a meeting in the requested room"
        def meetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def meeting = coastGuardDataFactory.createMeetingMap().get(meetingId)
        meetingManagementService.create(meeting)

        and: "The extension of the meeting is invalid"
        def extension = Constants.MAXIMAL_MEETING_DURATION

        when: "The current meeting in the room is extended"
        roomManagementService.extendCurrentMeeting(roomId, extension)

        then: "An exception is thrown"
        thrown(MaximalDurationReachedException)

        cleanup:
        roomManagementService.delete(roomId)
        meetingManagementService.delete(meetingId)
    }

    def "Extend the duration of the current meeting in a specific room so that it would conflict with another meeting"() {

        given: "A room with the requested room ID does exist"
        def roomId = coastGuardDataFactory.ROOM_ID_BLUE
        def room = coastGuardDataFactory.createRoomMap().get(roomId)
        roomManagementService.create(room)

        and: "There is currently a meeting in the requested room alongside with a follow up meeting"
        def currentMeetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def followUpMeetingId = coastGuardDataFactory.MEETING_ID_WHIRLPOOLS
        def currentMeeting = coastGuardDataFactory.createMeetingMap().get(currentMeetingId)
        def followUpMeeting = coastGuardDataFactory.createMeetingMap().get(followUpMeetingId)
        meetingManagementService.create(currentMeeting)
        meetingManagementService.create(followUpMeeting)

        and: "The extension of the meeting would lead to conflicts"
        def extension = Duration.between(currentMeeting.getEnd(), followUpMeeting.getEnd())

        when: "The current meeting in the room is extended"
        roomManagementService.extendCurrentMeeting(roomId, extension)

        then: "An exception is thrown"
        thrown(EntityConflictException)

        cleanup:
        roomManagementService.delete(roomId)
        meetingManagementService.delete(currentMeetingId)
        meetingManagementService.delete(followUpMeetingId)
    }

    def "Extend the duration of the current meeting in a specific room when there is currently no meeting"() {

        given: "A room with the requested room ID does exist"
        def roomId = forestRangersDataFactory.ROOM_ID_GREEN
        def room = forestRangersDataFactory.createRoomMap().get(roomId)
        roomManagementService.create(room)

        and: "There are currently meeting in several other rooms"
        def meetingId1 = coastGuardDataFactory.MEETING_ID_WHALES
        def meetingId2 = astronautsDataFactory.MEETING_ID_MARS
        def meeting1 = coastGuardDataFactory.createMeetingMap().get(meetingId1)
        def meeting2 = astronautsDataFactory.createMeetingMap().get(meetingId2)
        meetingManagementService.create(meeting1)
        meetingManagementService.create(meeting2)

        and: "The extension of the meeting is valid"
        def extension = Duration.ofMinutes(1)

        when: "The current meeting in the room is extended"
        roomManagementService.extendCurrentMeeting(roomId, extension)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)

        cleanup:
        roomManagementService.delete(roomId)
        meetingManagementService.delete(meetingId1)
        meetingManagementService.delete(meetingId2)
    }

    def "Extend the duration of the current meeting in a specific room when a room with that ID does not exist"() {

        given: "A room with the requested room ID does not exist"
        def roomId = forestRangersDataFactory.ROOM_ID_GREEN

        and: "There are currently meeting in several other rooms"
        def meetingId1 = coastGuardDataFactory.MEETING_ID_WHALES
        def meetingId2 = astronautsDataFactory.MEETING_ID_MARS
        def meeting1 = coastGuardDataFactory.createMeetingMap().get(meetingId1)
        def meeting2 = astronautsDataFactory.createMeetingMap().get(meetingId2)
        meetingManagementService.create(meeting1)
        meetingManagementService.create(meeting2)

        and: "The extension of the meeting is valid"
        def extension = Duration.ofMinutes(1)

        when: "The current meeting in the room is extended"
        roomManagementService.extendCurrentMeeting(roomId, extension)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)

        cleanup:
        meetingManagementService.delete(meetingId1)
        meetingManagementService.delete(meetingId2)
    }

    def "Get the status page of the current meeting in a specific room when there is currently a meeting"() {

        // TODO

        /*given: "A room with the requested room ID does exist"
        def roomId = coastGuardDataFactory.ROOM_ID_BLUE
        def room = coastGuardDataFactory.createRoomMap().get(roomId)
        roomManagementService.create(room)

        and: "There is currently a meeting in the requested room and another room"
        def meetings = Arrays.asList(
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES),
                astronautsDataFactory.createMeetingMap().get(astronautsDataFactory.MEETING_ID_MARS))
        def currentMeeting = coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES)
        for(def meeting : meetings) {
            meetingManagementService.create(meeting)
        }

        when: "The current meeting in the room is requested"
        def response = roomManagementService.getCurrentMeeting(roomId)

        then: "The returned HTTP status code is 200 (OK)"
        response.statusCodeValue == HttpStatus.OK.value()

        and: "The meeting equals the appropriate one that was initially put into the repository"
        response.getBody() == currentMeeting

        cleanup:
        roomManagementService.delete(roomId)
        for(def meeting : meetings) {
            meetingManagementService.delete(meeting.getId())
        }*/
    }

    def "Get the status page of the current meeting in a specific room when there is currently no meeting"() {

        // TODO

        /*given: "A room with the requested room ID does exist"
        def roomId = forestRangersDataFactory.ROOM_ID_GREEN
        def room = forestRangersDataFactory.createRoomMap().get(roomId)
        roomManagementService.create(room)

        and: "There are currently meeting in several other rooms"
        def meetingId1 = coastGuardDataFactory.MEETING_ID_WHALES
        def meetingId2 = astronautsDataFactory.MEETING_ID_MARS
        def meeting1 = coastGuardDataFactory.createMeetingMap().get(meetingId1)
        def meeting2 = astronautsDataFactory.createMeetingMap().get(meetingId2)
        meetingManagementService.create(meeting1)
        meetingManagementService.create(meeting2)

        and: "The extension of the meeting is valid"
        def extensionInMinutes = 1

        when: "The current meeting in the room is extended"
        roomManagementService.extendCurrentMeeting(roomId, extensionInMinutes)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 404 (Not found)"
        e.status() == HttpStatus.NOT_FOUND.value()

        cleanup:
        roomManagementService.delete(roomId)
        meetingManagementService.delete(meetingId1)
        meetingManagementService.delete(meetingId2)*/
    }

    def "Get the status page of the current meeting in a specific room when a room with that ID does not exist"() {

        // TODO

        /*given: "A room with the requested room ID does not exist"
        def roomId = forestRangersDataFactory.ROOM_ID_GREEN

        and: "There are currently meeting in several other rooms"
        def meetingId1 = coastGuardDataFactory.MEETING_ID_WHALES
        def meetingId2 = astronautsDataFactory.MEETING_ID_MARS
        def meeting1 = coastGuardDataFactory.createMeetingMap().get(meetingId1)
        def meeting2 = astronautsDataFactory.createMeetingMap().get(meetingId2)
        meetingManagementService.create(meeting1)
        meetingManagementService.create(meeting2)

        when: "The status page of the current meeting in the room is requested"
        roomManagementService.getCurrentMeetingStatusPage(roomId)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 404 (Not found)"
        e.status() == HttpStatus.NOT_FOUND.value()

        cleanup:
        meetingManagementService.delete(meetingId1)
        meetingManagementService.delete(meetingId2)*/
    }
}
