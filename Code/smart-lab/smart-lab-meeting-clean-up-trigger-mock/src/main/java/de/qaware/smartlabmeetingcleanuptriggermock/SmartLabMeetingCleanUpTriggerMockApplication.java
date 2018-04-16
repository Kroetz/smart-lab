package de.qaware.smartlabmeetingcleanuptriggermock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "de.qaware.smartlabcommons.api")
public class SmartLabMeetingCleanUpTriggerMockApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabMeetingCleanUpTriggerMockApplication.class, args);
	}
}
