package de.qaware.smartlabcore.meeting

import de.qaware.smartlabcommons.api.client.IMeetingManagementApiClient
import de.qaware.smartlabcommons.data.meeting.IMeeting
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
class MeetingManagementApiIntegrationTest extends CrudApiIntegrationTest<IMeeting> {

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
        crudApiClient = meetingManagementApiClient
        entitiesForFindAll_withExisting = new HashSet<>(Arrays.asList(
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES),
                forestRangersDataFactory.createMeetingMap().get(forestRangersDataFactory.MEETING_ID_BARK_BEETLE),
                fireFightersDataFactory.createMeetingMap().get(fireFightersDataFactory.MEETING_ID_TRUCK)))
    }

    @Override
    def setupDataForFindOne_withExisting() {
        crudApiClient = meetingManagementApiClient
        entityForFindOne_withExisting = coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES)
    }

    @Override
    def setupDataForFindOne_withoutExisting() {
        crudApiClient = meetingManagementApiClient
        entityIdForFindOne_withoutExisting = coastGuardDataFactory.MEETING_ID_WHALES
    }

    @Override
    def setupDataForCreate_withoutConflict() {
        crudApiClient = meetingManagementApiClient
        entityForCreate_withoutConflict = coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES)
    }

    @Override
    def setupDataForCreate_withConflict() {
        crudApiClient = meetingManagementApiClient
        entityForCreate_withConflict = coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES)
    }

    @Override
    def setupDataForDelete_withExisting() {
        crudApiClient = meetingManagementApiClient
        entityForDelete_withExisting = coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES)
    }

    @Override
    def setupDataForDelete_withoutExisting() {
        crudApiClient = meetingManagementApiClient
        entityIdForDelete_withoutExisting = coastGuardDataFactory.MEETING_ID_WHALES
    }

    def "Shorten an existing meeting by a valid duration"() {

        given: "A meeting with the requested meeting ID does exist"
        def meetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def meeting = coastGuardDataFactory.createMeetingMap().get(meetingId)
        meetingManagementApiClient.create(meeting)
        def shorteningInMinutes = (meeting.getDuration() - Duration.ofMinutes(1)).toMinutes()

        when: "The meeting is shortened"
        def response = meetingManagementApiClient.shortenMeeting(meetingId, shorteningInMinutes)

        then: "The returned HTTP status code is 200 (OK)"
        response.statusCodeValue == HttpStatus.OK.value()

        and: "The meeting is now shorter than it was originally"
        def shortenedMeeting = meetingManagementApiClient.findOne(meetingId).getBody()
        shortenedMeeting.getEnd() == meeting.getEnd() - Duration.ofMinutes(shorteningInMinutes)

        cleanup:
        meetingManagementApiClient.delete(meetingId)
    }

    def "Shorten an existing meeting so that it would be shorter than the minimal duration"() {

        given: "The meeting to shorten does exist"
        def meetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def meeting = coastGuardDataFactory.createMeetingMap().get(meetingId)
        meetingManagementApiClient.create(meeting)
        def shorteningInMinutes = meeting.getDuration().toMinutes()

        when: "The meeting is shortened beyond the minimum"
        meetingManagementApiClient.shortenMeeting(meetingId, shorteningInMinutes)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 422 (Unprocessable entity)"
        e.status() == HttpStatus.UNPROCESSABLE_ENTITY.value()

        cleanup:
        meetingManagementApiClient.delete(meetingId)
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
        meetingManagementApiClient.create(meeting)
        def extensionInMinutes = 1

        when: "The meeting is extended"
        def response = meetingManagementApiClient.extendMeeting(meetingId, extensionInMinutes)

        then: "The returned HTTP status code is 200 (OK)"
        response.statusCodeValue == HttpStatus.OK.value()

        and: "The meeting is now longer than it was originally"
        def extendedMeeting = meetingManagementApiClient.findOne(meetingId).getBody()
        extendedMeeting.getEnd() == meeting.getEnd() + Duration.ofMinutes(extensionInMinutes)

        cleanup:
        meetingManagementApiClient.delete(meetingId)
    }

    def "Extend an existing meeting so that it would be longer than the maximal duration"() {

        given: "The meeting to extend does exist"
        def meetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def meeting = coastGuardDataFactory.createMeetingMap().get(meetingId)
        meetingManagementApiClient.create(meeting)
        def extensionInMinutes = MeetingManagementService.MAXIMAL_MEETING_DURATION.toMinutes()

        when: "The meeting is extended beyond the maximum"
        meetingManagementApiClient.extendMeeting(meetingId, extensionInMinutes)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 422 (Unprocessable entity)"
        e.status() == HttpStatus.UNPROCESSABLE_ENTITY.value()

        cleanup:
        meetingManagementApiClient.delete(meetingId)
    }

    def "Extend an existing meeting so that it would conflict with another meeting"() {

        given: "The meeting to extend does exist alongside with a follow up meeting"
        def meetingToExtendId = coastGuardDataFactory.MEETING_ID_WHALES
        def followUpMeetingId = coastGuardDataFactory.MEETING_ID_WHIRLPOOLS
        def meetingToExtend = coastGuardDataFactory.createMeetingMap().get(meetingToExtendId)
        def followUpMeeting = coastGuardDataFactory.createMeetingMap().get(followUpMeetingId)
        meetingManagementApiClient.create(meetingToExtend)
        meetingManagementApiClient.create(followUpMeeting)
        def extensionInMinutes = Duration.between(meetingToExtend.getEnd(), followUpMeeting.getEnd()).toMinutes()

        when: "The meeting is extended"
        meetingManagementApiClient.extendMeeting(meetingToExtendId, extensionInMinutes)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 409 (Conflict)"
        e.status() == HttpStatus.CONFLICT.value()

        cleanup:
        meetingManagementApiClient.delete(meetingToExtendId)
        meetingManagementApiClient.delete(followUpMeetingId)
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
        meetingManagementApiClient.create(meeting)
        def shiftInMinutes = 1

        when: "The meeting is shifted"
        def response = meetingManagementApiClient.shiftMeeting(meetingId, shiftInMinutes)

        then: "The returned HTTP status code is 200 (OK)"
        response.statusCodeValue == HttpStatus.OK.value()

        and: "The meeting is now longer than it was originally"
        def shiftedMeeting = meetingManagementApiClient.findOne(meetingId).getBody()
        shiftedMeeting.getStart() == meeting.getStart() + Duration.ofMinutes(shiftInMinutes)
        shiftedMeeting.getEnd() == meeting.getEnd() + Duration.ofMinutes(shiftInMinutes)

        cleanup:
        meetingManagementApiClient.delete(meetingId)
    }

    def "Shift an existing meeting so that it would conflict with another meeting"() {

        given: "The meeting to shift does exist alongside with a follow up meeting"
        def meetingToShiftId = coastGuardDataFactory.MEETING_ID_WHALES
        def followUpMeetingId = coastGuardDataFactory.MEETING_ID_WHIRLPOOLS
        def meetingToShift = coastGuardDataFactory.createMeetingMap().get(meetingToShiftId)
        def followUpMeeting = coastGuardDataFactory.createMeetingMap().get(followUpMeetingId)
        meetingManagementApiClient.create(meetingToShift)
        meetingManagementApiClient.create(followUpMeeting)
        def shiftInMinutes = Duration.between(meetingToShift.getEnd(), followUpMeeting.getEnd()).toMinutes()

        when: "The meeting is shifted"
        meetingManagementApiClient.shiftMeeting(meetingToShiftId, shiftInMinutes)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 409 (Conflict)"
        e.status() == HttpStatus.CONFLICT.value()

        cleanup:
        meetingManagementApiClient.delete(meetingToShiftId)
        meetingManagementApiClient.delete(followUpMeetingId)
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
