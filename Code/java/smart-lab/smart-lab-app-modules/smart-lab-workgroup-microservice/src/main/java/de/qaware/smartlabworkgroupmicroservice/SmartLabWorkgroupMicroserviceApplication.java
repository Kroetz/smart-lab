package de.qaware.smartlabworkgroupmicroservice;

import de.qaware.smartlabmicroservice.annotation.EnableSmartLabMicroservice;
import de.qaware.smartlabworkgroupmanagement.annotation.EnableSmartLabWorkgroupService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableSmartLabMicroservice
@EnableSmartLabWorkgroupService
@EnableDiscoveryClient
public class SmartLabWorkgroupMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabWorkgroupMicroserviceApplication.class, args);
	}
}
