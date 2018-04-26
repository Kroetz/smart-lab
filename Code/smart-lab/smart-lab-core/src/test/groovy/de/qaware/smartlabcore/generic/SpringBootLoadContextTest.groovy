package de.qaware.smartlabcore.generic

import de.qaware.smartlabcore.data.sample.provider.EmptySampleDataProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles(EmptySampleDataProvider.PROFILE_NAME)
class SpringBootLoadContextTest extends Specification {

    @Autowired
    private final ApplicationContext applicationContext

    @Autowired
    private final WebApplicationContext webApplicationContext

    def "Load Spring Boot application context"() {
        expect: "That the application context is loaded"
        applicationContext != null
    }

    def "Load Spring Boot web application context"() {
        expect: "That the web application context is loaded"
        webApplicationContext != null
    }
}
