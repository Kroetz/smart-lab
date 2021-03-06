package de.qaware.smartlab.location.management.microservice;

import de.qaware.smartlab.location.management.annotation.EnableSmartLabLocationManagementService;
import de.qaware.smartlab.microservice.annotation.EnableSmartLabMicroservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableSmartLabMicroservice
@EnableSmartLabLocationManagementService
@EnableDiscoveryClient
public class SmartLabLocationManagementMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabLocationManagementMicroserviceApplication.class, args);
	}
}
