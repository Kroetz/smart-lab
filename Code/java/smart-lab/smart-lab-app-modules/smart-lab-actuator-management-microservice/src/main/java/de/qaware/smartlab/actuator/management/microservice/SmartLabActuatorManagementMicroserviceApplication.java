package de.qaware.smartlab.actuator.management.microservice;

import de.qaware.smartlab.actuator.management.annotation.EnableSmartLabActuatorManagementService;
import de.qaware.smartlab.microservice.annotation.EnableSmartLabMicroservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableSmartLabMicroservice
@EnableSmartLabActuatorManagementService
@EnableDiscoveryClient
public class SmartLabActuatorManagementMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabActuatorManagementMicroserviceApplication.class, args);
	}
}
