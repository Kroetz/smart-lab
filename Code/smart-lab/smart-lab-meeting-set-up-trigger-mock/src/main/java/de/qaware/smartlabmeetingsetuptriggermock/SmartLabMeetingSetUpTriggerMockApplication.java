package de.qaware.smartlabmeetingsetuptriggermock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackageClasses = {
		de.qaware.smartlabcore.trigger.controller.ITriggerApiClient.class,
		de.qaware.smartlabcore.meeting.controller.IMeetingManagementApiClient.class})
public class SmartLabMeetingSetUpTriggerMockApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabMeetingSetUpTriggerMockApplication.class, args);
	}
}
