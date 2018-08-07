package de.qaware.smartlabworkgroupmanagementmicroservice;

import de.qaware.smartlabmicroservice.annotation.EnableSmartLabMicroservice;
import de.qaware.smartlabworkgroupmanagement.annotation.EnableSmartLabWorkgroupService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableSmartLabMicroservice
@EnableSmartLabWorkgroupService
@EnableDiscoveryClient
public class SmartLabWorkgroupManagementMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabWorkgroupManagementMicroserviceApplication.class, args);
	}
}
