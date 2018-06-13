package de.qaware.smartlabdelegatemicroservice;

import de.qaware.smartlabdelegate.annotation.EnableSmartLabDelegateService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableSmartLabDelegateService
@EnableDiscoveryClient
public class SmartLabDelegateMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabDelegateMicroserviceApplication.class, args);
	}
}
