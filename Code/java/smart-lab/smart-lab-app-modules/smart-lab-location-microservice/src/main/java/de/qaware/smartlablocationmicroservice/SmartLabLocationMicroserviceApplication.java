package de.qaware.smartlablocationmicroservice;

import de.qaware.smartlablocationmanagement.annotation.EnableSmartLabLocationService;
import de.qaware.smartlabmicroservice.annotation.EnableSmartLabMicroservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableSmartLabMicroservice
@EnableSmartLabLocationService
@EnableDiscoveryClient
public class SmartLabLocationMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabLocationMicroserviceApplication.class, args);
	}
}
