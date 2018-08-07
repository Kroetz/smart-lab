package de.qaware.smartlab.actuator.management.microservice;

import de.qaware.smartlab.actuator.management.annotation.EnableSmartLabDeviceService;
import de.qaware.smartlabmicroservice.annotation.EnableSmartLabMicroservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableSmartLabMicroservice
@EnableSmartLabDeviceService
@EnableDiscoveryClient
public class SmartLabActuatorManagementMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabActuatorManagementMicroserviceApplication.class, args);
	}
}
