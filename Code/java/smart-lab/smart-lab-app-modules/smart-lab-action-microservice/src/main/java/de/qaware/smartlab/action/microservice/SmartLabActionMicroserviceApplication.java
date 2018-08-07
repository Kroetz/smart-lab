package de.qaware.smartlab.action.microservice;

import de.qaware.smartlabaction.annotation.EnableSmartLabActionService;
import de.qaware.smartlabmicroservice.annotation.EnableSmartLabMicroservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableSmartLabMicroservice
@EnableSmartLabActionService
@EnableDiscoveryClient
public class SmartLabActionMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabActionMicroserviceApplication.class, args);
	}
}
