package de.qaware.smartlabcore.room

import de.qaware.smartlabcommons.api.client.IMeetingManagementApiClient
import de.qaware.smartlabcommons.api.client.IRoomManagementApiClient
import de.qaware.smartlabcommons.data.meeting.IMeeting
import de.qaware.smartlabcommons.data.room.IRoom
import de.qaware.smartlabcore.data.sample.factory.AstronautsDataFactory
import de.qaware.smartlabcore.data.sample.factory.CoastGuardDataFactory
import de.qaware.smartlabcore.data.sample.factory.FireFightersDataFactory
import de.qaware.smartlabcore.data.sample.factory.ForestRangersDataFactory
import de.qaware.smartlabcore.data.sample.provider.EmptySampleDataProvider
import de.qaware.smartlabcore.generic.CrudApiIntegrationTest
import de.qaware.smartlabcore.meeting.service.MeetingManagementService
import feign.FeignException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles

import java.time.Duration

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles(EmptySampleDataProvider.PROFILE_NAME)
class RoomManagementApiIntegrationTest extends CrudApiIntegrationTest<IRoom> {

    @Autowired
    private IRoomManagementApiClient roomManagementApiClient

    @Autowired
    private IMeetingManagementApiClient meetingManagementApiClient

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
        crudApiClient = roomManagementApiClient
        entitiesForFindAll_withExisting = new HashSet<>(Arrays.asList(
                coastGuardDataFactory.createRoomMap().get(coastGuardDataFactory.ROOM_ID_BLUE),
                forestRangersDataFactory.createRoomMap().get(forestRangersDataFactory.ROOM_ID_GREEN),
                fireFightersDataFactory.createRoomMap().get(fireFightersDataFactory.ROOM_ID_RED)))
    }

    @Override
    def setupDataForFindOne_withExisting() {
        crudApiClient = roomManagementApiClient
        entityForFindOne_withExisting = coastGuardDataFactory.createRoomMap().get(coastGuardDataFactory.ROOM_ID_BLUE)
    }

    @Override
    def setupDataForFindOne_withoutExisting() {
        crudApiClient = roomManagementApiClient
        entityIdForFindOne_withoutExisting = coastGuardDataFactory.ROOM_ID_BLUE
    }

    @Override
    def setupDataForFindMultiple_withExisting() {
        crudApiClient = roomManagementApiClient
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
        crudApiClient = roomManagementApiClient
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
        crudApiClient = roomManagementApiClient
        entityForCreate_withoutConflict = coastGuardDataFactory.createRoomMap().get(coastGuardDataFactory.ROOM_ID_BLUE)
    }

    @Override
    def setupDataForCreate_withConflict() {
        crudApiClient = roomManagementApiClient
        entityForCreate_withConflict = coastGuardDataFactory.createRoomMap().get(coastGuardDataFactory.ROOM_ID_BLUE)
    }

    @Override
    def setupDataForDelete_withExisting() {
        crudApiClient = roomManagementApiClient
        entityForDelete_withExisting = coastGuardDataFactory.createRoomMap().get(coastGuardDataFactory.ROOM_ID_BLUE)
    }

    @Override
    def setupDataForDelete_withoutExisting() {
        crudApiClient = roomManagementApiClient
        entityIdForDelete_withoutExisting = coastGuardDataFactory.ROOM_ID_BLUE
    }

    def "Get a set of all meetings in a specific room when there are meetings"() {

        given: "A room with the requested room ID does exist"
        def roomId = coastGuardDataFactory.ROOM_ID_BLUE
        def room = coastGuardDataFactory.createRoomMap().get(roomId)
        roomManagementApiClient.create(room)

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
            meetingManagementApiClient.create(meeting)
        }

        when: "The set of meetings in the room is requested"
        def response = roomManagementApiClient.getMeetingsInRoom(roomId)

        then: "The returned HTTP status code is 200 (OK)"
        response.statusCodeValue == HttpStatus.OK.value()

        and: "The returned set equals the appropriate part of that one that was used to populate the repository"
        response.getBody() == meetingsInRoom

        cleanup:
        roomManagementApiClient.delete(roomId)
        for(def meeting : meetings) {
            meetingManagementApiClient.delete(meeting.getId())
        }
    }

    def "Get a set of all meetings in a specific room when there are no meetings"() {

        given: "A room with the requested room ID does exist"
        def roomId = coastGuardDataFactory.ROOM_ID_BLUE
        def room = coastGuardDataFactory.createRoomMap().get(roomId)
        roomManagementApiClient.create(room)

        and: "There are only meetings in other rooms than the requested one"
        def meetings = new HashSet<IMeeting>(Arrays.asList(
                forestRangersDataFactory.createMeetingMap().get(forestRangersDataFactory.MEETING_ID_BARK_BEETLE),
                fireFightersDataFactory.createMeetingMap().get(fireFightersDataFactory.MEETING_ID_TRUCK)))
        def meetingsInRoom = new HashSet<IMeeting>()
        for(def meeting : meetings) {
            meetingManagementApiClient.create(meeting)
        }

        when: "The set of meetings in the room is requested"
        def response = roomManagementApiClient.getMeetingsInRoom(roomId)

        then: "The returned HTTP status code is 200 (OK)"
        response.statusCodeValue == HttpStatus.OK.value()

        and: "The returned set is empty"
        response.getBody() == meetingsInRoom

        cleanup:
        roomManagementApiClient.delete(roomId)
        for(def meeting : meetings) {
            meetingManagementApiClient.delete(meeting.getId())
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
            meetingManagementApiClient.create(meeting)
        }

        when: "The set of meetings in the room is requested"
        roomManagementApiClient.getMeetingsInRoom(roomId)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 404 (Not found)"
        e.status() == HttpStatus.NOT_FOUND.value()

        cleanup:
        for(def meeting : meetings) {
            meetingManagementApiClient.delete(meeting.getId())
        }
    }

    def "Get the current meeting in a specific room when there is currently a meeting"() {

        given: "A room with the requested room ID does exist"
        def roomId = coastGuardDataFactory.ROOM_ID_BLUE
        def room = coastGuardDataFactory.createRoomMap().get(roomId)
        roomManagementApiClient.create(room)

        and: "There is currently a meeting in the requested room and another room"
        def meetings = new HashSet<IMeeting>(Arrays.asList(
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES),
                astronautsDataFactory.createMeetingMap().get(astronautsDataFactory.MEETING_ID_MARS)))
        def currentMeeting = coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES)
        for(def meeting : meetings) {
            meetingManagementApiClient.create(meeting)
        }

        when: "The current meeting in the room is requested"
        def response = roomManagementApiClient.getCurrentMeeting(roomId)

        then: "The returned HTTP status code is 200 (OK)"
        response.statusCodeValue == HttpStatus.OK.value()

        and: "The meeting equals the appropriate one that was initially put into the repository"
        response.getBody() == currentMeeting

        cleanup:
        roomManagementApiClient.delete(roomId)
        for(def meeting : meetings) {
            meetingManagementApiClient.delete(meeting.getId())
        }
    }

    def "Get the current meeting in a specific room when there is currently no meeting"() {

        given: "A room with the requested room ID does exist"
        def roomId = coastGuardDataFactory.ROOM_ID_BLUE
        def room = coastGuardDataFactory.createRoomMap().get(roomId)
        roomManagementApiClient.create(room)

        and: "There is currently a meeting in another room"
        def meetingId = astronautsDataFactory.MEETING_ID_MARS
        def meeting = astronautsDataFactory.createMeetingMap().get(meetingId)
        meetingManagementApiClient.create(meeting)

        when: "The current meeting in the room is requested"
        roomManagementApiClient.getCurrentMeeting(roomId)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 404 (Not found)"
        e.status() == HttpStatus.NOT_FOUND.value()

        cleanup:
        roomManagementApiClient.delete(roomId)
        meetingManagementApiClient.delete(meetingId)
    }

    def "Get the current meeting in a specific room when a room with that ID does not exist"() {

        given: "A room with the requested room ID does not exist"
        def roomId = forestRangersDataFactory.ROOM_ID_GREEN

        and: "There are currently meetings in several room"
        def meetings = new HashSet<IMeeting>(Arrays.asList(
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES),
                astronautsDataFactory.createMeetingMap().get(astronautsDataFactory.MEETING_ID_MARS)))
        for(def meeting : meetings) {
            meetingManagementApiClient.create(meeting)
        }

        when: "The current meeting in the room is requested"
        roomManagementApiClient.getCurrentMeeting(roomId)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 404 (Not found)"
        e.status() == HttpStatus.NOT_FOUND.value()

        cleanup:
        for(def meeting : meetings) {
            meetingManagementApiClient.delete(meeting.getId())
        }
    }

    def "Extend the duration of the current meeting in a specific room by a valid duration"() {

        given: "A room with the requested room ID does exist"
        def roomId = coastGuardDataFactory.ROOM_ID_BLUE
        def room = coastGuardDataFactory.createRoomMap().get(roomId)
        roomManagementApiClient.create(room)

        and: "There is currently a meeting in the requested room and another room"
        def meetingIdInRoom = coastGuardDataFactory.MEETING_ID_WHALES
        def meetingIdInOtherRoom = astronautsDataFactory.MEETING_ID_MARS
        def meetingInRoom = coastGuardDataFactory.createMeetingMap().get(meetingIdInRoom)
        def meetingInOtherRoom = astronautsDataFactory.createMeetingMap().get(meetingIdInOtherRoom)
        meetingManagementApiClient.create(meetingInRoom)
        meetingManagementApiClient.create(meetingInOtherRoom)

        and: "The extension of the meeting is valid"
        def extensionInMinutes = 1

        when: "The current meeting in the room is extended"
        def response = roomManagementApiClient.extendCurrentMeeting(roomId, extensionInMinutes)

        then: "The returned HTTP status code is 200 (OK)"
        response.statusCodeValue == HttpStatus.OK.value()

        and: "The current meeting in the room is now longer than it was originally"
        def extendedMeeting = roomManagementApiClient.getCurrentMeeting(roomId).getBody()
        extendedMeeting.getEnd() == meetingInRoom.getEnd() + Duration.ofMinutes(extensionInMinutes)

        and: "All other meetings still have their original duration"
        def notExtendedMeeting = meetingManagementApiClient.findOne(meetingIdInOtherRoom).getBody()
        notExtendedMeeting.getEnd() == meetingInOtherRoom.getEnd()

        cleanup:
        roomManagementApiClient.delete(roomId)
        meetingManagementApiClient.delete(meetingIdInRoom)
        meetingManagementApiClient.delete(meetingIdInOtherRoom)
    }

    def "Extend the duration of the current meeting in a specific room so that it would be longer than the maximal duration"() {

        given: "A room with the requested room ID does exist"
        def roomId = coastGuardDataFactory.ROOM_ID_BLUE
        def room = coastGuardDataFactory.createRoomMap().get(roomId)
        roomManagementApiClient.create(room)

        and: "There is currently a meeting in the requested room"
        def meetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def meeting = coastGuardDataFactory.createMeetingMap().get(meetingId)
        meetingManagementApiClient.create(meeting)

        and: "The extension of the meeting is invalid"
        def extensionInMinutes = MeetingManagementService.MAXIMAL_MEETING_DURATION.toMinutes()

        when: "The current meeting in the room is extended"
        roomManagementApiClient.extendCurrentMeeting(roomId, extensionInMinutes)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 422 (Unprocessable entity)"
        e.status() == HttpStatus.UNPROCESSABLE_ENTITY.value()

        cleanup:
        roomManagementApiClient.delete(roomId)
        meetingManagementApiClient.delete(meetingId)
    }

    def "Extend the duration of the current meeting in a specific room so that it would conflict with another meeting"() {

        given: "A room with the requested room ID does exist"
        def roomId = coastGuardDataFactory.ROOM_ID_BLUE
        def room = coastGuardDataFactory.createRoomMap().get(roomId)
        roomManagementApiClient.create(room)

        and: "There is currently a meeting in the requested room alongside with a follow up meeting"
        def currentMeetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def followUpMeetingId = coastGuardDataFactory.MEETING_ID_WHIRLPOOLS
        def currentMeeting = coastGuardDataFactory.createMeetingMap().get(currentMeetingId)
        def followUpMeeting = coastGuardDataFactory.createMeetingMap().get(followUpMeetingId)
        meetingManagementApiClient.create(currentMeeting)
        meetingManagementApiClient.create(followUpMeeting)

        and: "The extension of the meeting would lead to conflicts"
        def extensionInMinutes = Duration.between(currentMeeting.getEnd(), followUpMeeting.getEnd()).toMinutes()

        when: "The current meeting in the room is extended"
        roomManagementApiClient.extendCurrentMeeting(roomId, extensionInMinutes)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 409 (Conflict)"
        e.status() == HttpStatus.CONFLICT.value()

        cleanup:
        roomManagementApiClient.delete(roomId)
        meetingManagementApiClient.delete(currentMeetingId)
        meetingManagementApiClient.delete(followUpMeetingId)
    }

    def "Extend the duration of the current meeting in a specific room when there is currently no meeting"() {

        given: "A room with the requested room ID does exist"
        def roomId = forestRangersDataFactory.ROOM_ID_GREEN
        def room = forestRangersDataFactory.createRoomMap().get(roomId)
        roomManagementApiClient.create(room)

        and: "There are currently meeting in several other rooms"
        def meetingId1 = coastGuardDataFactory.MEETING_ID_WHALES
        def meetingId2 = astronautsDataFactory.MEETING_ID_MARS
        def meeting1 = coastGuardDataFactory.createMeetingMap().get(meetingId1)
        def meeting2 = astronautsDataFactory.createMeetingMap().get(meetingId2)
        meetingManagementApiClient.create(meeting1)
        meetingManagementApiClient.create(meeting2)

        and: "The extension of the meeting is valid"
        def extensionInMinutes = 1

        when: "The current meeting in the room is extended"
        roomManagementApiClient.extendCurrentMeeting(roomId, extensionInMinutes)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 404 (Not found)"
        e.status() == HttpStatus.NOT_FOUND.value()

        cleanup:
        roomManagementApiClient.delete(roomId)
        meetingManagementApiClient.delete(meetingId1)
        meetingManagementApiClient.delete(meetingId2)
    }

    def "Extend the duration of the current meeting in a specific room when a room with that ID does not exist"() {

        given: "A room with the requested room ID does not exist"
        def roomId = forestRangersDataFactory.ROOM_ID_GREEN

        and: "There are currently meeting in several other rooms"
        def meetingId1 = coastGuardDataFactory.MEETING_ID_WHALES
        def meetingId2 = astronautsDataFactory.MEETING_ID_MARS
        def meeting1 = coastGuardDataFactory.createMeetingMap().get(meetingId1)
        def meeting2 = astronautsDataFactory.createMeetingMap().get(meetingId2)
        meetingManagementApiClient.create(meeting1)
        meetingManagementApiClient.create(meeting2)

        and: "The extension of the meeting is valid"
        def extensionInMinutes = 1

        when: "The current meeting in the room is extended"
        roomManagementApiClient.extendCurrentMeeting(roomId, extensionInMinutes)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 404 (Not found)"
        e.status() == HttpStatus.NOT_FOUND.value()

        cleanup:
        meetingManagementApiClient.delete(meetingId1)
        meetingManagementApiClient.delete(meetingId2)
    }

    def "Get the status page of the current meeting in a specific room when there is currently a meeting"() {

        // TODO

        /*given: "A room with the requested room ID does exist"
        def roomId = coastGuardDataFactory.ROOM_ID_BLUE
        def room = coastGuardDataFactory.createRoomMap().get(roomId)
        roomManagementApiClient.create(room)

        and: "There is currently a meeting in the requested room and another room"
        def meetings = Arrays.asList(
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES),
                astronautsDataFactory.createMeetingMap().get(astronautsDataFactory.MEETING_ID_MARS))
        def currentMeeting = coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES)
        for(def meeting : meetings) {
            meetingManagementApiClient.create(meeting)
        }

        when: "The current meeting in the room is requested"
        def response = roomManagementApiClient.getCurrentMeeting(roomId)

        then: "The returned HTTP status code is 200 (OK)"
        response.statusCodeValue == HttpStatus.OK.value()

        and: "The meeting equals the appropriate one that was initially put into the repository"
        response.getBody() == currentMeeting

        cleanup:
        roomManagementApiClient.delete(roomId)
        for(def meeting : meetings) {
            meetingManagementApiClient.delete(meeting.getId())
        }*/
    }

    def "Get the status page of the current meeting in a specific room when there is currently no meeting"() {

        // TODO

        /*given: "A room with the requested room ID does exist"
        def roomId = forestRangersDataFactory.ROOM_ID_GREEN
        def room = forestRangersDataFactory.createRoomMap().get(roomId)
        roomManagementApiClient.create(room)

        and: "There are currently meeting in several other rooms"
        def meetingId1 = coastGuardDataFactory.MEETING_ID_WHALES
        def meetingId2 = astronautsDataFactory.MEETING_ID_MARS
        def meeting1 = coastGuardDataFactory.createMeetingMap().get(meetingId1)
        def meeting2 = astronautsDataFactory.createMeetingMap().get(meetingId2)
        meetingManagementApiClient.create(meeting1)
        meetingManagementApiClient.create(meeting2)

        and: "The extension of the meeting is valid"
        def extensionInMinutes = 1

        when: "The current meeting in the room is extended"
        roomManagementApiClient.extendCurrentMeeting(roomId, extensionInMinutes)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 404 (Not found)"
        e.status() == HttpStatus.NOT_FOUND.value()

        cleanup:
        roomManagementApiClient.delete(roomId)
        meetingManagementApiClient.delete(meetingId1)
        meetingManagementApiClient.delete(meetingId2)*/
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
        meetingManagementApiClient.create(meeting1)
        meetingManagementApiClient.create(meeting2)

        when: "The status page of the current meeting in the room is requested"
        roomManagementApiClient.getCurrentMeetingStatusPage(roomId)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 404 (Not found)"
        e.status() == HttpStatus.NOT_FOUND.value()

        cleanup:
        meetingManagementApiClient.delete(meetingId1)
        meetingManagementApiClient.delete(meetingId2)*/
    }
}
