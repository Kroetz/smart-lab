package de.qaware.smartlab.integrationtest.generic

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static java.util.Objects.nonNull

@SpringBootTest
class SpringBootLoadContextTest extends Specification {

    @Autowired
    private final ApplicationContext applicationContext

    @Autowired
    private final WebApplicationContext webApplicationContext

    def setupSpec() {
        /*
         * TODO: Detect if the system is started as monolith or as microservices
         * In the latter case some waiting is required until system is up and the discovery service has connected all
         * microservices.
         */
        // TimeUnit.SECONDS.sleep(90);
    }

    def "Load Spring Boot application context"() {
        expect: "That the application context is loaded"
        nonNull(applicationContext)
    }

    def "Load Spring Boot web application context"() {
        expect: "That the web application context is loaded"
        nonNull(webApplicationContext)
    }
}
