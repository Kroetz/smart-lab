package de.qaware.smartlabpersonmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackageClasses = de.qaware.smartlabperson.ComponentScanMarker.class)
public class SmartLabPersonMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabPersonMicroserviceApplication.class, args);
	}
}
