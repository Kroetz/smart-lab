package de.qaware.smartlabmeetingmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackageClasses = de.qaware.smartlabmeeting.ComponentScanMarker.class)
public class SmartLabMeetingMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabMeetingMicroserviceApplication.class, args);
	}
}
