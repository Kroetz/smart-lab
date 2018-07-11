package de.qaware.smartlabtriggerprovidermicroservice;

import de.qaware.smartlabtrigger.annotation.EnableSmartLabCleanUpMeetingTriggerProvider;
import de.qaware.smartlabtrigger.annotation.EnableSmartLabSetUpMeetingTriggerProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSmartLabSetUpMeetingTriggerProvider
@EnableSmartLabCleanUpMeetingTriggerProvider
public class SmartLabTriggerProviderMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartLabTriggerProviderMicroserviceApplication.class, args);
    }
}
