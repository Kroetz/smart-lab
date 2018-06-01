package de.qaware.smartlabtest.workgroup

import de.qaware.smartlabapi.service.meeting.IMeetingManagementService
import de.qaware.smartlabapi.service.workgroup.IWorkgroupManagementService
import de.qaware.smartlabcore.data.meeting.IMeeting
import de.qaware.smartlabcore.data.workgroup.IWorkgroup
import de.qaware.smartlabcore.exception.EntityNotFoundException
import de.qaware.smartlabcore.exception.MaximalDurationReachedException
import de.qaware.smartlabcore.exception.MeetingConflictException
import de.qaware.smartlabmeeting.business.MeetingManagementBusinessLogic
import de.qaware.smartlabsampledata.factory.AstronautsDataFactory
import de.qaware.smartlabsampledata.factory.CoastGuardDataFactory
import de.qaware.smartlabsampledata.factory.FireFightersDataFactory
import de.qaware.smartlabsampledata.factory.ForestRangersDataFactory
import de.qaware.smartlabtest.generic.CrudApiIntegrationTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import java.time.Duration

@SpringBootTest
class WorkgroupManagementApiIntegrationTest extends CrudApiIntegrationTest<IWorkgroup> {

    @Autowired
    private IWorkgroupManagementService workgroupManagementService

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
        crudService = workgroupManagementService
        entitiesForFindAll_withExisting = new HashSet<>(Arrays.asList(
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
        requestedEntitiesForFindMultiple_withExisting = [workgroup1, workgroup2]
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

    def "Get a set of all meetings of a specific workgroup when the workgroup has meetings"() {

        given: "A workgroup with the requested workgroup ID does exist"
        def workgroupId = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
        def workgroup = coastGuardDataFactory.createWorkgroupMap().get(workgroupId)
        workgroupManagementService.create(workgroup)

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
            meetingManagementService.create(meeting)
        }

        when: "The set of meetings of the workgroup is requested"
        def foundMeetings = workgroupManagementService.getMeetingsOfWorkgroup(workgroupId)

        then: "The returned set equals the appropriate part of that one that was used to populate the repository"
        foundMeetings == meetingsOfWorkgroup

        cleanup:
        workgroupManagementService.delete(workgroupId)
        for(def meeting : meetings) {
            meetingManagementService.delete(meeting.getId())
        }
    }

    def "Get a set of all meetings of a specific workgroup when the workgroup has no meetings"() {

        given: "A workgroup with the requested workgroup ID does exist"
        def workgroupId = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
        def workgroup = coastGuardDataFactory.createWorkgroupMap().get(workgroupId)
        workgroupManagementService.create(workgroup)

        and: "Only other workgroups than the requested one have meetings"
        def meetings = new HashSet<IMeeting>(Arrays.asList(
                forestRangersDataFactory.createMeetingMap().get(forestRangersDataFactory.MEETING_ID_BARK_BEETLE),
                fireFightersDataFactory.createMeetingMap().get(fireFightersDataFactory.MEETING_ID_TRUCK)))
        def meetingsOfWorkgroup = new HashSet<IMeeting>()
        for(def meeting : meetings) {
            meetingManagementService.create(meeting)
        }

        when: "The set of meetings of the workgroup is requested"
        def foundMeetings = workgroupManagementService.getMeetingsOfWorkgroup(workgroupId)

        then: "The returned set is empty"
        foundMeetings == meetingsOfWorkgroup

        cleanup:
        workgroupManagementService.delete(workgroupId)
        for(def meeting : meetings) {
            meetingManagementService.delete(meeting.getId())
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
            meetingManagementService.create(meeting)
        }

        when: "The set of meetings of the workgroup is requested"
        workgroupManagementService.getMeetingsOfWorkgroup(workgroupId)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)

        cleanup:
        for(def meeting : meetings) {
            meetingManagementService.delete(meeting.getId())
        }
    }

    def "Get the current meeting of a specific workgroup when the workgroup currently has a meeting"() {

        given: "A workgroup with the requested workgroup ID does exist"
        def workgroupId = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
        def workgroup = coastGuardDataFactory.createWorkgroupMap().get(workgroupId)
        workgroupManagementService.create(workgroup)

        and: "The requested workgroup and another workgroup currently have a meeting"
        def meetings = new HashSet<IMeeting>(Arrays.asList(
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES),
                astronautsDataFactory.createMeetingMap().get(astronautsDataFactory.MEETING_ID_MARS)))
        def currentMeeting = coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES)
        for(def meeting : meetings) {
            meetingManagementService.create(meeting)
        }

        when: "The current meeting of the workgroup is requested"
        def foundMeeting = workgroupManagementService.getCurrentMeeting(workgroupId)

        then: "The meeting equals the appropriate one that was initially put into the repository"
        foundMeeting == currentMeeting

        cleanup:
        workgroupManagementService.delete(workgroupId)
        for(def meeting : meetings) {
            meetingManagementService.delete(meeting.getId())
        }
    }

    def "Get the current meeting of a specific workgroup when the workgroup currently has no meeting"() {

        given: "A workgroup with the requested workgroup ID does exist"
        def workgroupId = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
        def workgroup = coastGuardDataFactory.createWorkgroupMap().get(workgroupId)
        workgroupManagementService.create(workgroup)

        and: "Another workgroup currently have a meeting"
        def meetingId = astronautsDataFactory.MEETING_ID_MARS
        def meeting = astronautsDataFactory.createMeetingMap().get(meetingId)
        meetingManagementService.create(meeting)

        when: "The current meeting of the workgroup is requested"
        workgroupManagementService.getCurrentMeeting(workgroupId)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)

        cleanup:
        workgroupManagementService.delete(workgroupId)
        meetingManagementService.delete(meetingId)
    }

    def "Get the current meeting of a specific workgroup when a workgroup with that ID does not exist"() {

        given: "A workgroup with the requested workgroup ID does not exist"
        def workgroupId = forestRangersDataFactory.WORKGROUP_ID_FOREST_RANGERS

        and: "Several other workgroups currently have meetings"
        def meetings = new HashSet<IMeeting>(Arrays.asList(
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES),
                astronautsDataFactory.createMeetingMap().get(astronautsDataFactory.MEETING_ID_MARS)))
        for(def meeting : meetings) {
            meetingManagementService.create(meeting)
        }

        when: "The current meeting of the workgroup is requested"
        workgroupManagementService.getCurrentMeeting(workgroupId)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)

        cleanup:
        for(def meeting : meetings) {
            meetingManagementService.delete(meeting.getId())
        }
    }

    def "Extend the duration of the current meeting of a specific workgroup by a valid duration"() {

        given: "A workgroup with the requested workgroup ID does exist"
        def workgroupId = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
        def workgroup = coastGuardDataFactory.createWorkgroupMap().get(workgroupId)
        workgroupManagementService.create(workgroup)

        and: "The requested workgroup and another workgroup currently have a meeting"
        def meetingIdOfWorkgroup = coastGuardDataFactory.MEETING_ID_WHALES
        def meetingIdOfOtherWorkgroup = astronautsDataFactory.MEETING_ID_MARS
        def meetingOfWorkgroup = coastGuardDataFactory.createMeetingMap().get(meetingIdOfWorkgroup)
        def meetingOfOtherWorkgroup = astronautsDataFactory.createMeetingMap().get(meetingIdOfOtherWorkgroup)
        meetingManagementService.create(meetingOfWorkgroup)
        meetingManagementService.create(meetingOfOtherWorkgroup)

        and: "The extension of the meeting is valid"
        def extension = Duration.ofMinutes(1)

        when: "The current meeting of the workgroup is extended"
        workgroupManagementService.extendCurrentMeeting(workgroupId, extension)

        then: "The current meeting of he workgroup is now longer than it was originally"
        def extendedMeeting = workgroupManagementService.getCurrentMeeting(workgroupId)
        extendedMeeting.getEnd() == meetingOfWorkgroup.getEnd() + extension

        and: "All other meetings still have their original duration"
        def notExtendedMeeting = meetingManagementService.findOne(meetingIdOfOtherWorkgroup)
        notExtendedMeeting.getEnd() == meetingOfOtherWorkgroup.getEnd()

        cleanup:
        workgroupManagementService.delete(workgroupId)
        meetingManagementService.delete(meetingIdOfWorkgroup)
        meetingManagementService.delete(meetingIdOfOtherWorkgroup)
    }

    def "Extend the duration of the current meeting of a specific workgroup so that it would be longer than the maximal duration"() {

        given: "A workgroup with the requested workgroup ID does exist"
        def workgroupId = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
        def workgroup = coastGuardDataFactory.createWorkgroupMap().get(workgroupId)
        workgroupManagementService.create(workgroup)

        and: "The requested workgroup currently has a meeting"
        def meetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def meeting = coastGuardDataFactory.createMeetingMap().get(meetingId)
        meetingManagementService.create(meeting)

        and: "The extension of the meeting is invalid"
        def extension = MeetingManagementBusinessLogic.MAXIMAL_MEETING_DURATION

        when: "The current meeting of the workgroup is extended"
        workgroupManagementService.extendCurrentMeeting(workgroupId, extension)

        then: "An exception is thrown"
        thrown(MaximalDurationReachedException)

        cleanup:
        workgroupManagementService.delete(workgroupId)
        meetingManagementService.delete(meetingId)
    }

    def "Extend the duration of the current meeting of a specific workgroup so that it would conflict with another meeting"() {

        given: "A workgroup with the requested workgroup ID does exist"
        def workgroupId = coastGuardDataFactory.WORKGROUP_ID_COAST_GUARD
        def workgroup = coastGuardDataFactory.createWorkgroupMap().get(workgroupId)
        workgroupManagementService.create(workgroup)

        and: "The requested workgroup currently has a meeting alongside with a follow up meeting"
        def currentMeetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def followUpMeetingId = coastGuardDataFactory.MEETING_ID_WHIRLPOOLS
        def currentMeeting = coastGuardDataFactory.createMeetingMap().get(currentMeetingId)
        def followUpMeeting = coastGuardDataFactory.createMeetingMap().get(followUpMeetingId)
        meetingManagementService.create(currentMeeting)
        meetingManagementService.create(followUpMeeting)

        and: "The extension of the meeting would lead to conflicts"
        def extension = Duration.between(currentMeeting.getEnd(), followUpMeeting.getEnd())

        when: "The current meeting of the workgroup is extended"
        workgroupManagementService.extendCurrentMeeting(workgroupId, extension)

        then: "An exception is thrown"
        thrown(MeetingConflictException)

        cleanup:
        workgroupManagementService.delete(workgroupId)
        meetingManagementService.delete(currentMeetingId)
        meetingManagementService.delete(followUpMeetingId)
    }

    def "Extend the duration of the current meeting of a specific workgroup when there is currently no meeting"() {

        given: "A workgroup with the requested workgroup ID does exist"
        def workgroupId = forestRangersDataFactory.WORKGROUP_ID_FOREST_RANGERS
        def workgroup = forestRangersDataFactory.createWorkgroupMap().get(workgroupId)
        workgroupManagementService.create(workgroup)

        and: "Several other workgroups currently have a meeting"
        def meetingId1 = coastGuardDataFactory.MEETING_ID_WHALES
        def meetingId2 = astronautsDataFactory.MEETING_ID_MARS
        def meeting1 = coastGuardDataFactory.createMeetingMap().get(meetingId1)
        def meeting2 = astronautsDataFactory.createMeetingMap().get(meetingId2)
        meetingManagementService.create(meeting1)
        meetingManagementService.create(meeting2)

        and: "The extension of the meeting is valid"
        def extension = Duration.ofMinutes(1)

        when: "The current meeting of the workgroup is extended"
        workgroupManagementService.extendCurrentMeeting(workgroupId, extension)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)

        cleanup:
        workgroupManagementService.delete(workgroupId)
        meetingManagementService.delete(meetingId1)
        meetingManagementService.delete(meetingId2)
    }

    def "Extend the duration of the current meeting of a specific workgroup when a workgroup with that ID does not exist"() {

        given: "A workgroup with the requested workgroup ID does not exist"
        def wokgroupId = forestRangersDataFactory.WORKGROUP_ID_FOREST_RANGERS

        and: "Several other workgroups currently have a meeting"
        def meetingId1 = coastGuardDataFactory.MEETING_ID_WHALES
        def meetingId2 = astronautsDataFactory.MEETING_ID_MARS
        def meeting1 = coastGuardDataFactory.createMeetingMap().get(meetingId1)
        def meeting2 = astronautsDataFactory.createMeetingMap().get(meetingId2)
        meetingManagementService.create(meeting1)
        meetingManagementService.create(meeting2)

        and: "The extension of the meeting is valid"
        def extension = Duration.ofMinutes(1)

        when: "The current meeting of the workgroup is extended"
        workgroupManagementService.extendCurrentMeeting(wokgroupId, extension)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)

        cleanup:
        meetingManagementService.delete(meetingId1)
        meetingManagementService.delete(meetingId2)
    }
}
