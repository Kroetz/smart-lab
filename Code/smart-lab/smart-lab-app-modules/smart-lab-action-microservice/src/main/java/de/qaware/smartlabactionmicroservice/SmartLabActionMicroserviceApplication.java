package de.qaware.smartlabactionmicroservice;

import de.qaware.smartlabaction.annotation.EnableSmartLabActionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableSmartLabActionService
@EnableDiscoveryClient
public class SmartLabActionMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabActionMicroserviceApplication.class, args);
	}
}
