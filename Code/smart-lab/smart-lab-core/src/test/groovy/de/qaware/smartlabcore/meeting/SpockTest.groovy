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
    IMeetingManagementService meetingService


    def setup() {
        customerService.dropCustomerCollection()
    }

    @Unroll
    def "create meeting"() {

        setup:
        def coastGuardMeeting = MeetingFactory.getCoastGuardMeeting()

        when:
        meetingService.createMeeting(coastGuardMeeting)

        then:
        meetingService.getMeeting(coastGuardMeeting.getId()) == coastGuardMeeting
    }

}
