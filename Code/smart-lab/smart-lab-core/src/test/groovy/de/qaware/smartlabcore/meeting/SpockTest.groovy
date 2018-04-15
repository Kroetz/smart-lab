package de.qaware.smartlabcore.meeting

import de.qaware.smartlabcore.meeting.service.IMeetingManagementService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Unroll

@ContextConfiguration
class SpockTest extends Specification{

    @Autowired
    @Qualifier("mock")
    private final IMeetingManagementService meetingManagementService

    def setup() {
        customerService.dropCustomerCollection()
    }

    @Unroll
    def "create meeting"() {

        setup:
        def coastGuardMeeting = MeetingFactory.getCoastGuardMeeting()

        when:
        meetingManagementService.createMeeting(coastGuardMeeting)

        then:
        meetingManagementService.getMeeting(coastGuardMeeting.getId()) == coastGuardMeeting
    }

}
