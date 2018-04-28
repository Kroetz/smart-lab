package de.qaware.smartlabcore.workgroup

import de.qaware.smartlabcommons.api.client.IMeetingManagementApiClient
import de.qaware.smartlabcommons.api.client.IWorkgroupManagementApiClient
import de.qaware.smartlabcommons.data.meeting.IMeeting
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup
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
class WorkgroupManagementApiIntegrationTest extends CrudApiIntegrationTest<IWorkgroup> {

    @Autowired
    private IWorkgroupManagementApiClient workgroupManagementApiClient

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
        crudApiClient = workgroupManagementApiClient
        entitiesForFindAll_withExisting = new HashSet<>(Arrays.asList(
                coastGuardDataFactory.createWorkgroupMap().get(coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD),
                forestRangersDataFactory.createWorkgroupMap().get(forestRangersDataFactory.WORKGROUP_ID_FOREST_RANGERS),
                fireFightersDataFactory.createWorkgroupMap().get(fireFightersDataFactory.WORKGROUP_ID_FIRE_FIGHTERS)))
    }

    @Override
    def setupDataForFindOne_withExisting() {
        crudApiClient = workgroupManagementApiClient
        entityForFindOne_withExisting = coastGuardDataFactory.createWorkgroupMap().get(coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD)
    }

    @Override
    def setupDataForFindOne_withoutExisting() {
        crudApiClient = workgroupManagementApiClient
        entityIdForFindOne_withoutExisting = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
    }

    @Override
    def setupDataForFindMultiple_withExisting() {
        crudApiClient = workgroupManagementApiClient
        def workgroupId1 = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
        def workgroupId2 = forestRangersDataFactory.WORKGROUP_ID_FOREST_RANGERS
        def workgroupId3 = fireFightersDataFactory.WORKGROUP_ID_FIRE_FIGHTERS
        def workgroup1 = coastGuardDataFactory.createWorkgroupMap().get(workgroupId1)
        def workgroup2 = forestRangersDataFactory.createWorkgroupMap().get(workgroupId2)
        def workgroup3 = fireFightersDataFactory.createWorkgroupMap().get(workgroupId3)
        allEntitiesForFindMultiple_withExisting = [workgroup1, workgroup2, workgroup3]
        requestedEntitiesForFindMultiple_withExisting = [workgroup1, workgroup2]
    }

    @Override
    def setupDataForFindMultiple_withoutExisting() {
        crudApiClient = workgroupManagementApiClient
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
        crudApiClient = workgroupManagementApiClient
        entityForCreate_withoutConflict = coastGuardDataFactory.createWorkgroupMap().get(coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD)
    }

    @Override
    def setupDataForCreate_withConflict() {
        crudApiClient = workgroupManagementApiClient
        entityForCreate_withConflict = coastGuardDataFactory.createWorkgroupMap().get(coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD)
    }

    @Override
    def setupDataForDelete_withExisting() {
        crudApiClient = workgroupManagementApiClient
        entityForDelete_withExisting = coastGuardDataFactory.createWorkgroupMap().get(coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD)
    }

    @Override
    def setupDataForDelete_withoutExisting() {
        crudApiClient = workgroupManagementApiClient
        entityIdForDelete_withoutExisting = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
    }

    def "Get a set of all meetings of a specific workgroup when the workgroup has meetings"() {

        given: "A workgroup with the requested workgroup ID does exist"
        def workgroupId = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
        def workgroup = coastGuardDataFactory.createWorkgroupMap().get(workgroupId)
        workgroupManagementApiClient.create(workgroup)

        and: "The requested workgroup and another workgroup have meetings"
        def meetings = new HashSet<IMeeting>(Arrays.asList(
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES),
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHIRLPOOLS),
                forestRangersDataFactory.createMeetingMap().get(forestRangersDataFactory.MEETING_ID_BARK_BEETLE),
                fireFightersDataFactory.createMeetingMap().get(fireFightersDataFactory.MEETING_ID_TRUCK)))
        def meetingsOfWorkgroup = new HashSet<IMeeting>(Arrays.asList(
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES),
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHIRLPOOLS)))
        for(def meeting : meetings) {
            meetingManagementApiClient.create(meeting)
        }

        when: "The set of meetings of the workgroup is requested"
        def response = workgroupManagementApiClient.getMeetingsOfWorkgroup(workgroupId)

        then: "The returned HTTP status code is 200 (OK)"
        response.statusCodeValue == HttpStatus.OK.value()

        and: "The returned set equals the appropriate part of that one that was used to populate the repository"
        response.getBody() == meetingsOfWorkgroup

        cleanup:
        workgroupManagementApiClient.delete(workgroupId)
        for(def meeting : meetings) {
            meetingManagementApiClient.delete(meeting.getId())
        }
    }

    def "Get a set of all meetings of a specific workgroup when the workgroup has no meetings"() {

        given: "A workgroup with the requested workgroup ID does exist"
        def workgroupId = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
        def workgroup = coastGuardDataFactory.createWorkgroupMap().get(workgroupId)
        workgroupManagementApiClient.create(workgroup)

        and: "Only other workgroups than the requested one have meetings"
        def meetings = new HashSet<IMeeting>(Arrays.asList(
                forestRangersDataFactory.createMeetingMap().get(forestRangersDataFactory.MEETING_ID_BARK_BEETLE),
                fireFightersDataFactory.createMeetingMap().get(fireFightersDataFactory.MEETING_ID_TRUCK)))
        def meetingsOfWorkgroup = new HashSet<IMeeting>()
        for(def meeting : meetings) {
            meetingManagementApiClient.create(meeting)
        }

        when: "The set of meetings of the workgroup is requested"
        def response = workgroupManagementApiClient.getMeetingsOfWorkgroup(workgroupId)

        then: "The returned HTTP status code is 200 (OK)"
        response.statusCodeValue == HttpStatus.OK.value()

        and: "The returned set is empty"
        response.getBody() == meetingsOfWorkgroup

        cleanup:
        workgroupManagementApiClient.delete(workgroupId)
        for(def meeting : meetings) {
            meetingManagementApiClient.delete(meeting.getId())
        }
    }

    def "Get a set of all meetings of a specific workgroup when a workgroup with that ID does not exist"() {

        given: "A workgroup with the requested workgroup ID does not exist"
        def workgroupId = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD

        and: "Several other workgroups have meetings"
        def meetings = new HashSet<IMeeting>(Arrays.asList(
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES),
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHIRLPOOLS),
                forestRangersDataFactory.createMeetingMap().get(forestRangersDataFactory.MEETING_ID_BARK_BEETLE),
                fireFightersDataFactory.createMeetingMap().get(fireFightersDataFactory.MEETING_ID_TRUCK)))
        for(def meeting : meetings) {
            meetingManagementApiClient.create(meeting)
        }

        when: "The set of meetings of the workgroup is requested"
        workgroupManagementApiClient.getMeetingsOfWorkgroup(workgroupId)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 404 (Not found)"
        e.status() == HttpStatus.NOT_FOUND.value()

        cleanup:
        for(def meeting : meetings) {
            meetingManagementApiClient.delete(meeting.getId())
        }
    }

    def "Get the current meeting of a specific workgroup when the workgroup currently has a meeting"() {

        given: "A workgroup with the requested workgroup ID does exist"
        def workgroupId = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
        def workgroup = coastGuardDataFactory.createWorkgroupMap().get(workgroupId)
        workgroupManagementApiClient.create(workgroup)

        and: "The requested workgroup and another workgroup currently have a meeting"
        def meetings = new HashSet<IMeeting>(Arrays.asList(
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES),
                astronautsDataFactory.createMeetingMap().get(astronautsDataFactory.MEETING_ID_MARS)))
        def currentMeeting = coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES)
        for(def meeting : meetings) {
            meetingManagementApiClient.create(meeting)
        }

        when: "The current meeting of the workgroup is requested"
        def response = workgroupManagementApiClient.getCurrentMeeting(workgroupId)

        then: "The returned HTTP status code is 200 (OK)"
        response.statusCodeValue == HttpStatus.OK.value()

        and: "The meeting equals the appropriate one that was initially put into the repository"
        response.getBody() == currentMeeting

        cleanup:
        workgroupManagementApiClient.delete(workgroupId)
        for(def meeting : meetings) {
            meetingManagementApiClient.delete(meeting.getId())
        }
    }

    def "Get the current meeting of a specific workgroup when the workgroup currently has no meeting"() {

        given: "A workgroup with the requested workgroup ID does exist"
        def workgroupId = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
        def workgroup = coastGuardDataFactory.createWorkgroupMap().get(workgroupId)
        workgroupManagementApiClient.create(workgroup)

        and: "Another workgroup currently have a meeting"
        def meetingId = astronautsDataFactory.MEETING_ID_MARS
        def meeting = astronautsDataFactory.createMeetingMap().get(meetingId)
        meetingManagementApiClient.create(meeting)

        when: "The current meeting of the workgroup is requested"
        workgroupManagementApiClient.getCurrentMeeting(workgroupId)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 404 (Not found)"
        e.status() == HttpStatus.NOT_FOUND.value()

        cleanup:
        workgroupManagementApiClient.delete(workgroupId)
        meetingManagementApiClient.delete(meetingId)
    }

    def "Get the current meeting of a specific workgroup when a workgroup with that ID does not exist"() {

        given: "A workgroup with the requested workgroup ID does not exist"
        def workgroupId = forestRangersDataFactory.WORKGROUP_ID_FOREST_RANGERS

        and: "Several other workgroups currently have meetings"
        def meetings = new HashSet<IMeeting>(Arrays.asList(
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES),
                astronautsDataFactory.createMeetingMap().get(astronautsDataFactory.MEETING_ID_MARS)))
        for(def meeting : meetings) {
            meetingManagementApiClient.create(meeting)
        }

        when: "The current meeting of the workgroup is requested"
        workgroupManagementApiClient.getCurrentMeeting(workgroupId)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 404 (Not found)"
        e.status() == HttpStatus.NOT_FOUND.value()

        cleanup:
        for(def meeting : meetings) {
            meetingManagementApiClient.delete(meeting.getId())
        }
    }

    def "Extend the duration of the current meeting of a specific workgroup by a valid duration"() {

        given: "A workgroup with the requested workgroup ID does exist"
        def workgroupId = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
        def workgroup = coastGuardDataFactory.createWorkgroupMap().get(workgroupId)
        workgroupManagementApiClient.create(workgroup)

        and: "The requested workgroup and another workgroup currently have a meeting"
        def meetingIdOfWorkgroup = coastGuardDataFactory.MEETING_ID_WHALES
        def meetingIdOfOtherWorkgroup = astronautsDataFactory.MEETING_ID_MARS
        def meetingOfWorkgroup = coastGuardDataFactory.createMeetingMap().get(meetingIdOfWorkgroup)
        def meetingOfOtherWorkgroup = astronautsDataFactory.createMeetingMap().get(meetingIdOfOtherWorkgroup)
        meetingManagementApiClient.create(meetingOfWorkgroup)
        meetingManagementApiClient.create(meetingOfOtherWorkgroup)

        and: "The extension of the meeting is valid"
        def extensionInMinutes = 1

        when: "The current meeting of the workgroup is extended"
        def response = workgroupManagementApiClient.extendCurrentMeeting(workgroupId, extensionInMinutes)

        then: "The returned HTTP status code is 200 (OK)"
        response.statusCodeValue == HttpStatus.OK.value()

        and: "The current meeting of he workgroup is now longer than it was originally"
        def extendedMeeting = workgroupManagementApiClient.getCurrentMeeting(workgroupId).getBody()
        extendedMeeting.getEnd() == meetingOfWorkgroup.getEnd() + Duration.ofMinutes(extensionInMinutes)

        and: "All other meetings still have their original duration"
        def notExtendedMeeting = meetingManagementApiClient.findOne(meetingIdOfOtherWorkgroup).getBody()
        notExtendedMeeting.getEnd() == meetingOfOtherWorkgroup.getEnd()

        cleanup:
        workgroupManagementApiClient.delete(workgroupId)
        meetingManagementApiClient.delete(meetingIdOfWorkgroup)
        meetingManagementApiClient.delete(meetingIdOfOtherWorkgroup)
    }

    def "Extend the duration of the current meeting of a specific workgroup so that it would be longer than the maximal duration"() {

        given: "A workgroup with the requested workgroup ID does exist"
        def workgroupId = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
        def workgroup = coastGuardDataFactory.createWorkgroupMap().get(workgroupId)
        workgroupManagementApiClient.create(workgroup)

        and: "The requested workgroup currently has a meeting"
        def meetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def meeting = coastGuardDataFactory.createMeetingMap().get(meetingId)
        meetingManagementApiClient.create(meeting)

        and: "The extension of the meeting is invalid"
        def extensionInMinutes = MeetingManagementService.MAXIMAL_MEETING_DURATION.toMinutes()

        when: "The current meeting of the workgroup is extended"
        workgroupManagementApiClient.extendCurrentMeeting(workgroupId, extensionInMinutes)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 422 (Unprocessable entity)"
        e.status() == HttpStatus.UNPROCESSABLE_ENTITY.value()

        cleanup:
        workgroupManagementApiClient.delete(workgroupId)
        meetingManagementApiClient.delete(meetingId)
    }

    def "Extend the duration of the current meeting of a specific workgroup so that it would conflict with another meeting"() {

        given: "A workgroup with the requested workgroup ID does exist"
        def workgroupId = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
        def workgroup = coastGuardDataFactory.createWorkgroupMap().get(workgroupId)
        workgroupManagementApiClient.create(workgroup)

        and: "The requested workgroup currently has a meeting alongside with a follow up meeting"
        def currentMeetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def followUpMeetingId = coastGuardDataFactory.MEETING_ID_WHIRLPOOLS
        def currentMeeting = coastGuardDataFactory.createMeetingMap().get(currentMeetingId)
        def followUpMeeting = coastGuardDataFactory.createMeetingMap().get(followUpMeetingId)
        meetingManagementApiClient.create(currentMeeting)
        meetingManagementApiClient.create(followUpMeeting)

        and: "The extension of the meeting would lead to conflicts"
        def extensionInMinutes = Duration.between(currentMeeting.getEnd(), followUpMeeting.getEnd()).toMinutes()

        when: "The current meeting of the workgroup is extended"
        workgroupManagementApiClient.extendCurrentMeeting(workgroupId, extensionInMinutes)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 409 (Conflict)"
        e.status() == HttpStatus.CONFLICT.value()

        cleanup:
        workgroupManagementApiClient.delete(workgroupId)
        meetingManagementApiClient.delete(currentMeetingId)
        meetingManagementApiClient.delete(followUpMeetingId)
    }

    def "Extend the duration of the current meeting of a specific workgroup when there is currently no meeting"() {

        given: "A workgroup with the requested workgroup ID does exist"
        def workgroupId = forestRangersDataFactory.WORKGROUP_ID_FOREST_RANGERS
        def workgroup = forestRangersDataFactory.createWorkgroupMap().get(workgroupId)
        workgroupManagementApiClient.create(workgroup)

        and: "Several other workgroups currently have a meeting"
        def meetingId1 = coastGuardDataFactory.MEETING_ID_WHALES
        def meetingId2 = astronautsDataFactory.MEETING_ID_MARS
        def meeting1 = coastGuardDataFactory.createMeetingMap().get(meetingId1)
        def meeting2 = astronautsDataFactory.createMeetingMap().get(meetingId2)
        meetingManagementApiClient.create(meeting1)
        meetingManagementApiClient.create(meeting2)

        and: "The extension of the meeting is valid"
        def extensionInMinutes = 1

        when: "The current meeting of the workgroup is extended"
        workgroupManagementApiClient.extendCurrentMeeting(workgroupId, extensionInMinutes)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 404 (Not found)"
        e.status() == HttpStatus.NOT_FOUND.value()

        cleanup:
        workgroupManagementApiClient.delete(workgroupId)
        meetingManagementApiClient.delete(meetingId1)
        meetingManagementApiClient.delete(meetingId2)
    }

    def "Extend the duration of the current meeting of a specific workgroup when a workgroup with that ID does not exist"() {

        given: "A workgroup with the requested workgroup ID does not exist"
        def wokgroupId = forestRangersDataFactory.WORKGROUP_ID_FOREST_RANGERS

        and: "Several other workgroups currently have a meeting"
        def meetingId1 = coastGuardDataFactory.MEETING_ID_WHALES
        def meetingId2 = astronautsDataFactory.MEETING_ID_MARS
        def meeting1 = coastGuardDataFactory.createMeetingMap().get(meetingId1)
        def meeting2 = astronautsDataFactory.createMeetingMap().get(meetingId2)
        meetingManagementApiClient.create(meeting1)
        meetingManagementApiClient.create(meeting2)

        and: "The extension of the meeting is valid"
        def extensionInMinutes = 1

        when: "The current meeting of the workgroup is extended"
        workgroupManagementApiClient.extendCurrentMeeting(wokgroupId, extensionInMinutes)

        then: "A feign exception is thrown"
        def e = thrown(FeignException)

        and: "The returned HTTP status code is 404 (Not found)"
        e.status() == HttpStatus.NOT_FOUND.value()

        cleanup:
        meetingManagementApiClient.delete(meetingId1)
        meetingManagementApiClient.delete(meetingId2)
    }
}
