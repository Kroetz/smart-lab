package de.qaware.smartlabmeetingmicroservice;

import de.qaware.smartlabmeeting.annotation.EnableSmartLabMeetingService;
import de.qaware.smartlabmicroservice.annotation.EnableSmartLabMicroservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@EnableSmartLabMicroservice
@SpringBootApplication
@EnableSmartLabMeetingService
public class SmartLabMeetingMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabMeetingMicroserviceApplication.class, args);
	}
}
