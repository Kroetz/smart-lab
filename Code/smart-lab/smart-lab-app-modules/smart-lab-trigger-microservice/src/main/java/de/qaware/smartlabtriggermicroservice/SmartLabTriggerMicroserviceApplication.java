package de.qaware.smartlabtriggermicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackageClasses = de.qaware.smartlabtrigger.ComponentScanMarker.class)
public class SmartLabTriggerMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabTriggerMicroserviceApplication.class, args);
	}
}
