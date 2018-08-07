package de.qaware.smartlab.trigger.microservice;

import de.qaware.smartlab.microservice.annotation.EnableSmartLabMicroservice;
import de.qaware.smartlabtrigger.annotation.EnableSmartLabTriggerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableSmartLabMicroservice
@EnableSmartLabTriggerService
@EnableDiscoveryClient
public class SmartLabTriggerMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabTriggerMicroserviceApplication.class, args);
	}
}
