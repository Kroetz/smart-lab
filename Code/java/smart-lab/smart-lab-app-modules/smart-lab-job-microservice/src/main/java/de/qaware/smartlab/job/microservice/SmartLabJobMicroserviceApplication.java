package de.qaware.smartlab.job.microservice;

import de.qaware.smartlab.job.annotation.EnableSmartLabJobService;
import de.qaware.smartlab.microservice.annotation.EnableSmartLabMicroservice;
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
