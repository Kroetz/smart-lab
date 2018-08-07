package de.qaware.smartlab.trigger.provider.microservice;

import de.qaware.smartlabmicroservice.annotation.EnableSmartLabMicroservice;
import de.qaware.smartlabtrigger.annotation.EnableSmartLabCleanUpMeetingTriggerProvider;
import de.qaware.smartlabtrigger.annotation.EnableSmartLabSetUpMeetingTriggerProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSmartLabMicroservice
@EnableSmartLabSetUpMeetingTriggerProvider
@EnableSmartLabCleanUpMeetingTriggerProvider
public class SmartLabTriggerProviderMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartLabTriggerProviderMicroserviceApplication.class, args);
    }
}
