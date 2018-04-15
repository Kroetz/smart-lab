package de.qaware.smartlabmeetingsetuptriggermock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "de.qaware.smartlabcommons.api.client")
public class SmartLabMeetingSetUpTriggerMockApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabMeetingSetUpTriggerMockApplication.class, args);
	}
}
