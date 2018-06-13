package de.qaware.smartlabdiscoverymicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SmartLabDiscoveryMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabDiscoveryMicroserviceApplication.class, args);
	}
}
