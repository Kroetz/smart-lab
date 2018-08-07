package de.qaware.smartlab.person.management.microservice;

import de.qaware.smartlab.microservice.annotation.EnableSmartLabMicroservice;
import de.qaware.smartlab.person.management.annotation.EnableSmartLabPersonService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableSmartLabMicroservice
@EnableSmartLabPersonService
@EnableDiscoveryClient
public class SmartLabPersonManagementMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabPersonManagementMicroserviceApplication.class, args);
	}
}
