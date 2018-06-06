package de.qaware.smartlabtriggermicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackageClasses = {
		de.qaware.smartlabcore.ComponentScanMarker.class,
		de.qaware.smartlabapi.ComponentScanMarker.class,
		de.qaware.smartlabtrigger.ComponentScanMarker.class,
		de.qaware.smartlabassistance.ComponentScanMarker.class})
public class SmartLabTriggerMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabTriggerMicroserviceApplication.class, args);
	}
}
