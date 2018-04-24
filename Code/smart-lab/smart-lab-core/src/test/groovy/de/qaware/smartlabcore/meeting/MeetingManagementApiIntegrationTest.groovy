package de.qaware.smartlabcore.meeting

import de.qaware.smartlabcommons.api.client.IMeetingManagementApiClient
import de.qaware.smartlabcommons.data.meeting.IMeeting
import de.qaware.smartlabcore.data.sample.factory.AstronautsDataFactory
import de.qaware.smartlabcore.data.sample.factory.CoastGuardDataFactory
import de.qaware.smartlabcore.data.sample.factory.FireFightersDataFactory
import de.qaware.smartlabcore.data.sample.factory.ForestRangersDataFactory
import de.qaware.smartlabcore.data.sample.provider.EmptySampleDataProvider
import de.qaware.smartlabcore.meeting.service.MeetingManagementService
import feign.FeignException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import java.time.Duration

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles(EmptySampleDataProvider.PROFILE_NAME)
class MeetingManagementApiIntegrationTest extends Specification {

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

    def "Get a list of all existing meetings when there are meetings"() {

        given: "There are 3 meetings available in the repository"
        def meetingId1 = coastGuardDataFactory.MEETING_ID_WHALES
        def meetingId2 = forestRangersDataFactory.MEETING_ID_BARK_BEETLE
        def meetingId3 = fireFightersDataFactory.MEETING_ID_TRUCK
        def meetings = new ArrayList<IMeeting>()
        meetings.add(coastGuardDataFactory.createMeetingMap().get(meetingId1))
        meetings.add(forestRangersDataFactory.createMeetingMap().get(meetingId2))
        meetings.add(fireFightersDataFactory.createMeetingMap().get(meetingId3))
        for(def meeting : meetings) {
            meetingManagementApiClient.createMeeting(meeting)
        }

        when: "The list of meetings is requested"
        def meetingsResponse = meetingManagementApiClient.getMeetings()

        then: "The returned list equals the one that was used to populate the repository"
        meetingsResponse == meetings

        cleanup:
        meetingManagementApiClient.deleteMeeting(meetingId1)
        meetingManagementApiClient.deleteMeeting(meetingId2)
        meetingManagementApiClient.deleteMeeting(meetingId3)
    }

    def "Get a list of all existing meetings when there are no meetings"() {

        given: "There are no meetings available in the repository"
        def meetings = new ArrayList<IMeeting>()

        when: "The list of meetings is requested"
        def meetingsResponse = meetingManagementApiClient.getMeetings()

        then: "The returned list equals the one that was used to populate the repository"
        meetingsResponse == meetings
    }

    def "Get an existing meeting"() {

        given: "A meeting with the requested meeting ID does exist"
        def meetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def meeting = coastGuardDataFactory.createMeetingMap().get(meetingId)
        meetingManagementApiClient.createMeeting(meeting)

        when: "The meeting is requested"
        def response = meetingManagementApiClient.getMeeting(meetingId)

        then: "The returned HTTP status code is 200 (OK)"
        response.statusCodeValue == HttpStatus.OK.value()

        and: "The received meeting equals the one that was initially put into the repository"
        response.getBody() == meeting

        cleanup:
        meetingManagementApiClient.deleteMeeting(meetingId)
    }

    def "Get a non existing meeting"() {

        given: "Requested meeting ID does not exist"
        def meetingId = AstronautsDataFactory.MEETING_ID_MARS

        when: "The meeting is requested"
        meetingManagementApiClient.getMeeting(meetingId)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 404 (Not found)"
        e.status() == HttpStatus.NOT_FOUND.value()
    }

    def "Create a meeting with an ID that does not yet exist"() {

        given: "The ID of the meeting to create is not yet taken"
        def meetingId = AstronautsDataFactory.MEETING_ID_MARS
        def meetingToCreate = astronautsDataFactory.createMeetingMap().get(meetingId)

        when: "The meeting is created"
        def creationResponse = meetingManagementApiClient.createMeeting(meetingToCreate)

        then: "The returned HTTP status code is 200 (OK)"
        creationResponse.statusCode == HttpStatus.OK

        when: "The created meeting is requested"
        def getResponse = meetingManagementApiClient.getMeeting(meetingId)

        then: "The meeting from the response equals the one that was initially passed during the creation"
        getResponse.statusCodeValue == HttpStatus.OK.value()
        meetingToCreate == getResponse.getBody()

        cleanup:
        meetingManagementApiClient.deleteMeeting(meetingId)
    }

    def "Create a meeting with an ID that does already exist"() {

        given: "The ID of the meeting to create is already taken"
        def meetingId = CoastGuardDataFactory.MEETING_ID_WHALES
        def meetingToCreate = coastGuardDataFactory.createMeetingMap().get(meetingId)
        meetingManagementApiClient.createMeeting(meetingToCreate)

        when: "The meeting is created"
        meetingManagementApiClient.createMeeting(meetingToCreate)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 409 (Conflict)"
        e.status() == HttpStatus.CONFLICT.value()

        cleanup:
        meetingManagementApiClient.deleteMeeting(meetingId)
    }

    def "Delete a meeting with an ID that does exist"() {

        given: "The ID of the meeting to delete is taken"
        def meetingId = CoastGuardDataFactory.MEETING_ID_WHALES
        def meetingToDelete = coastGuardDataFactory.createMeetingMap().get(meetingId)
        meetingManagementApiClient.createMeeting(meetingToDelete)

        when: "The meeting is deleted"
        def deletionResponse = meetingManagementApiClient.deleteMeeting(meetingId)

        then: "The returned HTTP status code is 200 (OK)"
        deletionResponse.statusCode == HttpStatus.OK

        when: "The deleted meeting is requested"
        meetingManagementApiClient.getMeeting(meetingId)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 404 (Not found)"
        e.status() == HttpStatus.NOT_FOUND.value()
    }

    def "Delete a meeting with an ID that does not exist"() {

        given: "The ID of the meeting to delete is not taken"
        def meetingId = AstronautsDataFactory.MEETING_ID_MARS

        when: "The meeting is deleted"
        meetingManagementApiClient.deleteMeeting(meetingId)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 404 (Not found)"
        e.status() == HttpStatus.NOT_FOUND.value()
    }

    def "Shorten an existing meeting by a valid duration"() {

        given: "A meeting with the requested meeting ID does exist"
        def meetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def meeting = coastGuardDataFactory.createMeetingMap().get(meetingId)
        meetingManagementApiClient.createMeeting(meeting)
        def shorteningInMinutes = (meeting.getDuration() - Duration.ofMinutes(1)).toMinutes()

        when: "The meeting is shortened"
        def response = meetingManagementApiClient.shortenMeeting(meetingId, shorteningInMinutes)

        then: "The returned HTTP status code is 200 (OK)"
        response.statusCodeValue == HttpStatus.OK.value()

        and: "The meeting is now shorter than it was originally"
        def shortenedMeeting = meetingManagementApiClient.getMeeting(meetingId).getBody()
        shortenedMeeting.getEnd() == meeting.getEnd() - Duration.ofMinutes(shorteningInMinutes)

        cleanup:
        meetingManagementApiClient.deleteMeeting(meetingId)
    }

    def "Shorten an existing meeting so that it would be shorter than the minimal duration"() {

        given: "The meeting to shorten does exist"
        def meetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def meeting = coastGuardDataFactory.createMeetingMap().get(meetingId)
        meetingManagementApiClient.createMeeting(meeting)
        def shorteningInMinutes = meeting.getDuration().toMinutes()

        when: "The meeting is shortened beyond the minimum"
        meetingManagementApiClient.shortenMeeting(meetingId, shorteningInMinutes)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 422 (Unprocessable entity)"
        e.status() == HttpStatus.UNPROCESSABLE_ENTITY.value()

        cleanup:
        meetingManagementApiClient.deleteMeeting(meetingId)
    }

    def "Shorten a meeting with an ID that does not exist"() {

        given: "The meeting to shorten does not exist"
        def meetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def meeting = coastGuardDataFactory.createMeetingMap().get(meetingId)
        def shorteningInMinutes = (meeting.getDuration() - Duration.ofMinutes(1)).toMinutes()

        when: "The meeting is shortened"
        meetingManagementApiClient.shortenMeeting(meetingId, shorteningInMinutes)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 404 (Not found)"
        e.status() == HttpStatus.NOT_FOUND.value()
    }

    def "Extend an existing meeting by a valid duration"() {

        given: "The meeting to shorten does exist"
        def meetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def meeting = coastGuardDataFactory.createMeetingMap().get(meetingId)
        meetingManagementApiClient.createMeeting(meeting)
        def extensionInMinutes = 1

        when: "The meeting is extended"
        def response = meetingManagementApiClient.extendMeeting(meetingId, extensionInMinutes)

        then: "The returned HTTP status code is 200 (OK)"
        response.statusCodeValue == HttpStatus.OK.value()

        and: "The meeting is now longer than it was originally"
        def extendedMeeting = meetingManagementApiClient.getMeeting(meetingId).getBody()
        extendedMeeting.getEnd() == meeting.getEnd() + Duration.ofMinutes(extensionInMinutes)

        cleanup:
        meetingManagementApiClient.deleteMeeting(meetingId)
    }

    def "Extend an existing meeting so that it would be longer than the maximal duration"() {

        given: "The meeting to extend does exist"
        def meetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def meeting = coastGuardDataFactory.createMeetingMap().get(meetingId)
        meetingManagementApiClient.createMeeting(meeting)
        def extensionInMinutes = MeetingManagementService.MAXIMAL_MEETING_DURATION.toMinutes()

        when: "The meeting is extended beyond the maximum"
        meetingManagementApiClient.extendMeeting(meetingId, extensionInMinutes)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 422 (Unprocessable entity)"
        e.status() == HttpStatus.UNPROCESSABLE_ENTITY.value()

        cleanup:
        meetingManagementApiClient.deleteMeeting(meetingId)
    }

    def "Extend an existing meeting so that it would conflict with another meeting"() {

        given: "The meeting to extend does exist alongside with a follow up meeting"
        def meetingToExtendId = coastGuardDataFactory.MEETING_ID_WHALES
        def followUpMeetingId = coastGuardDataFactory.MEETING_ID_WHIRLPOOLS
        def meetingToExtend = coastGuardDataFactory.createMeetingMap().get(meetingToExtendId)
        def followUpMeeting = coastGuardDataFactory.createMeetingMap().get(followUpMeetingId)
        meetingManagementApiClient.createMeeting(meetingToExtend)
        meetingManagementApiClient.createMeeting(followUpMeeting)
        def extensionInMinutes = Duration.between(meetingToExtend.getEnd(), followUpMeeting.getEnd()).toMinutes()

        when: "The meeting is extended"
        meetingManagementApiClient.extendMeeting(meetingToExtendId, extensionInMinutes)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 409 (Conflict)"
        e.status() == HttpStatus.CONFLICT.value()

        cleanup:
        meetingManagementApiClient.deleteMeeting(meetingToExtendId)
        meetingManagementApiClient.deleteMeeting(followUpMeetingId)
    }

    def "Extend a meeting with an ID that does not exist"() {

        given: "The meeting to extend does not exist"
        def meetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def extensionInMinutes = 1

        when: "The meeting is extended"
        meetingManagementApiClient.extendMeeting(meetingId, extensionInMinutes)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 404 (Not found)"
        e.status() == HttpStatus.NOT_FOUND.value()
    }

    def "Shift an existing meeting by a valid duration"() {

        given: "The meeting to shift does exist"
        def meetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def meeting = coastGuardDataFactory.createMeetingMap().get(meetingId)
        meetingManagementApiClient.createMeeting(meeting)
        def shiftInMinutes = 1

        when: "The meeting is shifted"
        def response = meetingManagementApiClient.shiftMeeting(meetingId, shiftInMinutes)

        then: "The returned HTTP status code is 200 (OK)"
        response.statusCodeValue == HttpStatus.OK.value()

        and: "The meeting is now longer than it was originally"
        def shiftedMeeting = meetingManagementApiClient.getMeeting(meetingId).getBody()
        shiftedMeeting.getStart() == meeting.getStart() + Duration.ofMinutes(shiftInMinutes)
        shiftedMeeting.getEnd() == meeting.getEnd() + Duration.ofMinutes(shiftInMinutes)

        cleanup:
        meetingManagementApiClient.deleteMeeting(meetingId)
    }

    def "Shift an existing meeting so that it would conflict with another meeting"() {

        given: "The meeting to shift does exist alongside with a follow up meeting"
        def meetingToShiftId = coastGuardDataFactory.MEETING_ID_WHALES
        def followUpMeetingId = coastGuardDataFactory.MEETING_ID_WHIRLPOOLS
        def meetingToShift = coastGuardDataFactory.createMeetingMap().get(meetingToShiftId)
        def followUpMeeting = coastGuardDataFactory.createMeetingMap().get(followUpMeetingId)
        meetingManagementApiClient.createMeeting(meetingToShift)
        meetingManagementApiClient.createMeeting(followUpMeeting)
        def shiftInMinutes = Duration.between(meetingToShift.getEnd(), followUpMeeting.getEnd()).toMinutes()

        when: "The meeting is shifted"
        meetingManagementApiClient.shiftMeeting(meetingToShiftId, shiftInMinutes)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 409 (Conflict)"
        e.status() == HttpStatus.CONFLICT.value()

        cleanup:
        meetingManagementApiClient.deleteMeeting(meetingToShiftId)
        meetingManagementApiClient.deleteMeeting(followUpMeetingId)
    }

    def "Shift a meeting with an ID that does not exist"() {

        given: "The meeting to shift does not exist"
        def meetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def shiftInMinutes = 1

        when: "The meeting is shifted"
        meetingManagementApiClient.shiftMeeting(meetingId, shiftInMinutes)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 404 (Not found)"
        e.status() == HttpStatus.NOT_FOUND.value()
    }
}
