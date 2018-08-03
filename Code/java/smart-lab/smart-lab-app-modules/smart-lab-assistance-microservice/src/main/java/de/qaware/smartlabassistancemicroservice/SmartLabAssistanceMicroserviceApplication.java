package de.qaware.smartlabassistancemicroservice;

import de.qaware.smartlabassistance.annotation.EnableSmartLabAssistanceService;
import de.qaware.smartlabmicroservice.annotation.EnableSmartLabMicroservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableSmartLabMicroservice
@EnableSmartLabAssistanceService
@EnableDiscoveryClient
public class SmartLabAssistanceMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabAssistanceMicroserviceApplication.class, args);
	}
}
