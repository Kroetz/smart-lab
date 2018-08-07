package de.qaware.smartlabpersonmanagementmicroservice;

import de.qaware.smartlabmicroservice.annotation.EnableSmartLabMicroservice;
import de.qaware.smartlabpersonmanagement.annotation.EnableSmartLabPersonService;
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
