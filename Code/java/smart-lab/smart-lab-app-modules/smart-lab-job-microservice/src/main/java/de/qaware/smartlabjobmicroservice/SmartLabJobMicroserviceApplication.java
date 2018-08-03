package de.qaware.smartlabjobmicroservice;

import de.qaware.smartlabjob.annotation.EnableSmartLabJobService;
import de.qaware.smartlabmicroservice.annotation.EnableSmartLabMicroservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableSmartLabMicroservice
@EnableSmartLabJobService
@EnableDiscoveryClient
public class SmartLabJobMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabJobMicroserviceApplication.class, args);
	}
}
