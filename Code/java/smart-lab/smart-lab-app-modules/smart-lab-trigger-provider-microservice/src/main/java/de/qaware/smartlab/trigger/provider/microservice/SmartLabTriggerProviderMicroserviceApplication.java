package de.qaware.smartlab.trigger.provider.microservice;

import de.qaware.smartlab.microservice.annotation.EnableSmartLabMicroservice;
import de.qaware.smartlab.trigger.annotation.EnableSmartLabCleanUpMeetingTriggerProvider;
import de.qaware.smartlab.trigger.annotation.EnableSmartLabSetUpMeetingTriggerProvider;
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
