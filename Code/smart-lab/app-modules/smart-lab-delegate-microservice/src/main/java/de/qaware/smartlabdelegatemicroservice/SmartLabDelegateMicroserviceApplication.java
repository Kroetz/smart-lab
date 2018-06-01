package de.qaware.smartlabdelegatemicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages={
		"de.qaware.smartlabdelegate",
		"de.qaware.smartlabcommons",
		"de.qaware.smartlabcore.generic",
		"de.qaware.smartlabaction"})
public class SmartLabDelegateMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabDelegateMicroserviceApplication.class, args);
	}
}
