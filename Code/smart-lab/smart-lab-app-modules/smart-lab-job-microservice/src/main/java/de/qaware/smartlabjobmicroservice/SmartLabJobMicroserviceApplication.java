package de.qaware.smartlabjobmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackageClasses = de.qaware.smartlabjob.ComponentScanMarker.class)
public class SmartLabJobMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabJobMicroserviceApplication.class, args);
	}
}
