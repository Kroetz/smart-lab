package de.qaware.smartlab.integrationtest

import de.qaware.smartlab.integrationtest.actuator.ActuatorManagementApiIntegrationTest
import de.qaware.smartlab.integrationtest.generic.SpringBootLoadContextTest
import de.qaware.smartlab.integrationtest.event.EventManagementApiIntegrationTest
import de.qaware.smartlab.integrationtest.person.PersonManagementApiIntegrationTest
import de.qaware.smartlab.integrationtest.location.LocationManagementApiIntegrationTest
import de.qaware.smartlab.integrationtest.workgroup.WorkgroupManagementApiIntegrationTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite.class)
@Suite.SuiteClasses([
        SpringBootLoadContextTest.class,
        ActuatorManagementApiIntegrationTest.class,
        EventManagementApiIntegrationTest.class,
        PersonManagementApiIntegrationTest.class,
        LocationManagementApiIntegrationTest.class,
        WorkgroupManagementApiIntegrationTest.class])
class IntegrationTestSuite { }
