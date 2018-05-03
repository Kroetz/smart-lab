package de.qaware.smartlabassistance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "de.qaware.smartlabcommons.api.client")
@SpringBootApplication(scanBasePackages={
		"de.qaware.smartlabassistance",
		"de.qaware.smartlabcommons",
		"de.qaware.smartlabcore.generic"})
public class SmartLabAssistanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabAssistanceApplication.class, args);
	}
}
