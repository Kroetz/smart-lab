package de.qaware.smartlabtriggermicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "de.qaware.smartlabapi.client")
@SpringBootApplication(scanBasePackages={
		"de.qaware.smartlabtrigger",
		"de.qaware.smartlabcommons",
		"de.qaware.smartlabapi",
		"de.qaware.smartlabcore.data",
		"de.qaware.smartlabcore.generic"})
public class SmartLabTriggerMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabTriggerMicroserviceApplication.class, args);
	}
}
