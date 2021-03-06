package de.qaware.smartlab.workgroup.management.microservice;

import de.qaware.smartlab.microservice.annotation.EnableSmartLabMicroservice;
import de.qaware.smartlab.workgroup.management.annotation.EnableSmartLabWorkgroupManagementService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableSmartLabMicroservice
@EnableSmartLabWorkgroupManagementService
@EnableDiscoveryClient
public class WorkgroupManagementMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkgroupManagementMicroserviceApplication.class, args);
	}
}
