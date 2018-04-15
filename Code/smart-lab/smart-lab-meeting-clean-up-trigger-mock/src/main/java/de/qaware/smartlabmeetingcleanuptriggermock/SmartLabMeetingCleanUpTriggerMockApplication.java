package de.qaware.smartlabmeetingcleanuptriggermock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackageClasses = {
		de.qaware.smartlabcore.trigger.controller.ITriggerApiClient.class,
		de.qaware.smartlabcore.meeting.controller.IMeetingManagementApiClient.class})
public class SmartLabMeetingCleanUpTriggerMockApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabMeetingCleanUpTriggerMockApplication.class, args);
	}
}
