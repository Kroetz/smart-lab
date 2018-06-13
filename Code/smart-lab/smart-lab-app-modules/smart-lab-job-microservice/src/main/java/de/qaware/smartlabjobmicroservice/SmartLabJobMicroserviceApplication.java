package de.qaware.smartlabjobmicroservice;

import de.qaware.smartlabjob.annotation.EnableSmartLabJobService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableSmartLabJobService
@EnableDiscoveryClient
public class SmartLabJobMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabJobMicroserviceApplication.class, args);
	}
}
