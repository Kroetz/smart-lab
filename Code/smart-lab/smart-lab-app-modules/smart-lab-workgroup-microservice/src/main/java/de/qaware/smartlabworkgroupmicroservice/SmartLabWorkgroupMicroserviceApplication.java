package de.qaware.smartlabworkgroupmicroservice;

import de.qaware.smartlabworkgroup.annotation.EnableSmartLabWorkgroupService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableSmartLabWorkgroupService
@EnableDiscoveryClient
public class SmartLabWorkgroupMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabWorkgroupMicroserviceApplication.class, args);
	}
}
