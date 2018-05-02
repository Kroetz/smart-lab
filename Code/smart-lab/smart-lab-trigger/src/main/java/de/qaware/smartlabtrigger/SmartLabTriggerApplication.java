package de.qaware.smartlabtrigger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "de.qaware.smartlabcommons.api.client")
@SpringBootApplication(scanBasePackages={
		"de.qaware.smartlabtrigger",
		"de.qaware.smartlabcommons",
		"de.qaware.smartlabcore.generic"})
public class SmartLabTriggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabTriggerApplication.class, args);
	}
}
