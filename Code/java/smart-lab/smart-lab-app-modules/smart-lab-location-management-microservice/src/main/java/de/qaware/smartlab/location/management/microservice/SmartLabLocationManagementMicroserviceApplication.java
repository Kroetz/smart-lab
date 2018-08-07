package de.qaware.smartlab.location.management.microservice;

import de.qaware.smartlablocationmanagement.annotation.EnableSmartLabLocationService;
import de.qaware.smartlabmicroservice.annotation.EnableSmartLabMicroservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableSmartLabMicroservice
@EnableSmartLabLocationService
@EnableDiscoveryClient
public class SmartLabLocationManagementMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabLocationManagementMicroserviceApplication.class, args);
	}
}
