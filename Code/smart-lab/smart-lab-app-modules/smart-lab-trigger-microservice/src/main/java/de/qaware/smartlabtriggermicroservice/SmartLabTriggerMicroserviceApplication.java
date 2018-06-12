package de.qaware.smartlabtriggermicroservice;

import de.qaware.smartlabtrigger.annotation.EnableTriggerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableTriggerService
@EnableDiscoveryClient
public class SmartLabTriggerMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabTriggerMicroserviceApplication.class, args);
	}
}
