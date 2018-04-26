package de.qaware.smartlabcore

import de.qaware.smartlabcore.device.DeviceManagementApiIntegrationTest
import de.qaware.smartlabcore.generic.SpringBootLoadContextTest
import de.qaware.smartlabcore.meeting.MeetingManagementApiIntegrationTest
import de.qaware.smartlabcore.person.PersonManagementApiIntegrationTest
import de.qaware.smartlabcore.room.RoomManagementApiIntegrationTest
import de.qaware.smartlabcore.workgroup.WorkgroupManagementApiIntegrationTest
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
