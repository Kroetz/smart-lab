package de.qaware.smartlabtest.meeting

import de.qaware.smartlabcommons.api.internal.service.meeting.IMeetingManagementService
import de.qaware.smartlabcommons.data.meeting.IMeeting
import de.qaware.smartlabcommons.exception.EntityNotFoundException
import de.qaware.smartlabcommons.exception.MaximalDurationReachedException
import de.qaware.smartlabcommons.exception.MeetingConflictException
import de.qaware.smartlabcommons.exception.MinimalDurationReachedException
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
class MeetingManagementApiIntegrationTest extends CrudApiIntegrationTest<IMeeting> {

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
        crudService = meetingManagementService
        entitiesForFindAll_withExisting = new HashSet<>(Arrays.asList(
                coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES),
                forestRangersDataFactory.createMeetingMap().get(forestRangersDataFactory.MEETING_ID_BARK_BEETLE),
                fireFightersDataFactory.createMeetingMap().get(fireFightersDataFactory.MEETING_ID_TRUCK)))
    }

    @Override
    def setupDataForFindOne_withExisting() {
        crudService = meetingManagementService
        entityForFindOne_withExisting = coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES)
    }

    @Override
    def setupDataForFindOne_withoutExisting() {
        crudService = meetingManagementService
        entityIdForFindOne_withoutExisting = coastGuardDataFactory.MEETING_ID_WHALES
    }

    @Override
    def setupDataForFindMultiple_withExisting() {
        crudService = meetingManagementService
        def meetingId1 = coastGuardDataFactory.MEETING_ID_WHALES
        def meetingId2 = forestRangersDataFactory.MEETING_ID_BARK_BEETLE
        def meetingId3 = fireFightersDataFactory.MEETING_ID_TRUCK
        def meeting1 = coastGuardDataFactory.createMeetingMap().get(meetingId1)
        def meeting2 = forestRangersDataFactory.createMeetingMap().get(meetingId2)
        def meeting3 = fireFightersDataFactory.createMeetingMap().get(meetingId3)
        allEntitiesForFindMultiple_withExisting = [meeting1, meeting2, meeting3]
        requestedEntitiesForFindMultiple_withExisting = [meeting1, meeting2]
    }

    @Override
    def setupDataForFindMultiple_withoutExisting() {
        crudService = meetingManagementService
        def meetingId1 = coastGuardDataFactory.MEETING_ID_WHALES
        def meetingId2 = forestRangersDataFactory.MEETING_ID_BARK_BEETLE
        def meetingId3 = fireFightersDataFactory.MEETING_ID_TRUCK
        def meeting1 = coastGuardDataFactory.createMeetingMap().get(meetingId1)
        def meeting2 = forestRangersDataFactory.createMeetingMap().get(meetingId2)
        def meeting3 = fireFightersDataFactory.createMeetingMap().get(meetingId3)
        allEntitiesForFindMultiple_withoutExisting = [meeting1, meeting2]
        requestedEntitiesForFindMultiple_withoutExisting = [meeting2, meeting3]
    }

    @Override
    def setupDataForCreate_withoutConflict() {
        crudService = meetingManagementService
        entityForCreate_withoutConflict = coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES)
    }

    @Override
    def setupDataForCreate_withConflict() {
        crudService = meetingManagementService
        entityForCreate_withConflict = coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES)
    }

    @Override
    def setupDataForDelete_withExisting() {
        crudService = meetingManagementService
        entityForDelete_withExisting = coastGuardDataFactory.createMeetingMap().get(coastGuardDataFactory.MEETING_ID_WHALES)
    }

    @Override
    def setupDataForDelete_withoutExisting() {
        crudService = meetingManagementService
        entityIdForDelete_withoutExisting = coastGuardDataFactory.MEETING_ID_WHALES
    }

    def "Shorten an existing meeting by a valid duration"() {

        given: "A meeting with the requested meeting ID does exist"
        def meetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def meeting = coastGuardDataFactory.createMeetingMap().get(meetingId)
        meetingManagementService.create(meeting)
        def shortening = (meeting.getDuration() - Duration.ofMinutes(1))

        when: "The meeting is shortened"
        meetingManagementService.shortenMeeting(meetingId, shortening)

        then: "The meeting is now shorter than it was originally"
        def shortenedMeeting = meetingManagementService.findOne(meetingId)
        shortenedMeeting.getEnd() == meeting.getEnd() - shortening

        cleanup:
        meetingManagementService.delete(meetingId)
    }

    def "Shorten an existing meeting so that it would be shorter than the minimal duration"() {

        given: "The meeting to shorten does exist"
        def meetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def meeting = coastGuardDataFactory.createMeetingMap().get(meetingId)
        meetingManagementService.create(meeting)
        def shortening = meeting.getDuration()

        when: "The meeting is shortened beyond the minimum"
        meetingManagementService.shortenMeeting(meetingId, shortening)

        then: "An exception is thrown"
        thrown(MinimalDurationReachedException)

        cleanup:
        meetingManagementService.delete(meetingId)
    }

    def "Shorten a meeting with an ID that does not exist"() {

        given: "The meeting to shorten does not exist"
        def meetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def meeting = coastGuardDataFactory.createMeetingMap().get(meetingId)
        def shortening = (meeting.getDuration() - Duration.ofMinutes(1))

        when: "The meeting is shortened"
        meetingManagementService.shortenMeeting(meetingId, shortening)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)
    }

    def "Extend an existing meeting by a valid duration"() {

        given: "The meeting to shorten does exist"
        def meetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def meeting = coastGuardDataFactory.createMeetingMap().get(meetingId)
        meetingManagementService.create(meeting)
        def extension = Duration.ofMinutes(1)

        when: "The meeting is extended"
        meetingManagementService.extendMeeting(meetingId, extension)

        then: "The meeting is now longer than it was originally"
        def extendedMeeting = meetingManagementService.findOne(meetingId)
        extendedMeeting.getEnd() == meeting.getEnd() + extension

        cleanup:
        meetingManagementService.delete(meetingId)
    }

    def "Extend an existing meeting so that it would be longer than the maximal duration"() {

        given: "The meeting to extend does exist"
        def meetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def meeting = coastGuardDataFactory.createMeetingMap().get(meetingId)
        meetingManagementService.create(meeting)
        def extension = MeetingManagementBusinessLogic.MAXIMAL_MEETING_DURATION

        when: "The meeting is extended beyond the maximum"
        meetingManagementService.extendMeeting(meetingId, extension)

        then: "An exception is thrown"
        thrown(MaximalDurationReachedException)

        cleanup:
        meetingManagementService.delete(meetingId)
    }

    def "Extend an existing meeting so that it would conflict with another meeting"() {

        given: "The meeting to extend does exist alongside with a follow up meeting"
        def meetingToExtendId = coastGuardDataFactory.MEETING_ID_WHALES
        def followUpMeetingId = coastGuardDataFactory.MEETING_ID_WHIRLPOOLS
        def meetingToExtend = coastGuardDataFactory.createMeetingMap().get(meetingToExtendId)
        def followUpMeeting = coastGuardDataFactory.createMeetingMap().get(followUpMeetingId)
        meetingManagementService.create(meetingToExtend)
        meetingManagementService.create(followUpMeeting)
        def extension = Duration.between(meetingToExtend.getEnd(), followUpMeeting.getEnd())

        when: "The meeting is extended"
        meetingManagementService.extendMeeting(meetingToExtendId, extension)

        then: "An exception is thrown"
        thrown(MeetingConflictException)

        cleanup:
        meetingManagementService.delete(meetingToExtendId)
        meetingManagementService.delete(followUpMeetingId)
    }

    def "Extend a meeting with an ID that does not exist"() {

        given: "The meeting to extend does not exist"
        def meetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def extension = Duration.ofMinutes(1)

        when: "The meeting is extended"
        meetingManagementService.extendMeeting(meetingId, extension)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)
    }

    def "Shift an existing meeting by a valid duration"() {

        given: "The meeting to shift does exist"
        def meetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def meeting = coastGuardDataFactory.createMeetingMap().get(meetingId)
        meetingManagementService.create(meeting)
        def shift = Duration.ofMinutes(1)

        when: "The meeting is shifted"
        meetingManagementService.shiftMeeting(meetingId, shift)

        then: "The meeting is now longer than it was originally"
        def shiftedMeeting = meetingManagementService.findOne(meetingId)
        shiftedMeeting.getStart() == meeting.getStart() + shift
        shiftedMeeting.getEnd() == meeting.getEnd() + shift

        cleanup:
        meetingManagementService.delete(meetingId)
    }

    def "Shift an existing meeting so that it would conflict with another meeting"() {

        given: "The meeting to shift does exist alongside with a follow up meeting"
        def meetingToShiftId = coastGuardDataFactory.MEETING_ID_WHALES
        def followUpMeetingId = coastGuardDataFactory.MEETING_ID_WHIRLPOOLS
        def meetingToShift = coastGuardDataFactory.createMeetingMap().get(meetingToShiftId)
        def followUpMeeting = coastGuardDataFactory.createMeetingMap().get(followUpMeetingId)
        meetingManagementService.create(meetingToShift)
        meetingManagementService.create(followUpMeeting)
        def shift = Duration.between(meetingToShift.getEnd(), followUpMeeting.getEnd())

        when: "The meeting is shifted"
        meetingManagementService.shiftMeeting(meetingToShiftId, shift)

        then: "An exception is thrown"
        thrown(MeetingConflictException)

        cleanup:
        meetingManagementService.delete(meetingToShiftId)
        meetingManagementService.delete(followUpMeetingId)
    }

    def "Shift a meeting with an ID that does not exist"() {

        given: "The meeting to shift does not exist"
        def meetingId = coastGuardDataFactory.MEETING_ID_WHALES
        def shift = Duration.ofMinutes(1)

        when: "The meeting is shifted"
        meetingManagementService.shiftMeeting(meetingId, shift)

        then: "An exception is thrown"
        thrown(EntityNotFoundException)
    }
}
