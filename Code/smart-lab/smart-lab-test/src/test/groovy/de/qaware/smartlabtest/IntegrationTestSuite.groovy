package de.qaware.smartlabtest

import de.qaware.smartlabtest.device.DeviceManagementApiIntegrationTest
import de.qaware.smartlabtest.generic.SpringBootLoadContextTest
import de.qaware.smartlabtest.meeting.MeetingManagementApiIntegrationTest
import de.qaware.smartlabtest.person.PersonManagementApiIntegrationTest
import de.qaware.smartlabtest.room.RoomManagementApiIntegrationTest
import de.qaware.smartlabtest.workgroup.WorkgroupManagementApiIntegrationTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite.class)
@Suite.SuiteClasses([
        SpringBootLoadContextTest.class,
        DeviceManagementApiIntegrationTest.class,
        MeetingManagementApiIntegrationTest.class,
        PersonManagementApiIntegrationTest.class,
        RoomManagementApiIntegrationTest.class,
        WorkgroupManagementApiIntegrationTest.class])
class IntegrationTestSuite { }
