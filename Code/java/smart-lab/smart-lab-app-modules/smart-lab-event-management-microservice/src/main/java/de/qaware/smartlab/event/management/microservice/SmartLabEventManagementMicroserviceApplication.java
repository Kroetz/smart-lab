package de.qaware.smartlab.event.management.microservice;

import de.qaware.smartlab.event.management.annotation.EnableSmartLabEventService;
import de.qaware.smartlab.microservice.annotation.EnableSmartLabMicroservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@EnableSmartLabMicroservice
@SpringBootApplication
@EnableSmartLabEventService
public class SmartLabEventManagementMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabEventManagementMicroserviceApplication.class, args);
	}
}
