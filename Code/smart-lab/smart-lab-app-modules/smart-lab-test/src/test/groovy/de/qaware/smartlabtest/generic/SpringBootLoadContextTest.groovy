package de.qaware.smartlabtest.generic

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
        // TODO: Waiting is only necessary when starting the system as microservices
        // Wait some time until system is up and the discovery service has connected all modules.
        //Thread.sleep(90000)
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
