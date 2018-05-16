package de.qaware.smartlabdiscoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class SmartLabDiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabDiscoveryServiceApplication.class, args);
	}
}
