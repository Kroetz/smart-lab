package de.qaware.smartlabdelegatemicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
// TODO: The delegate should not need to scan for feign clients since its only purpose is to control local devices
@EnableFeignClients(basePackages = "de.qaware.smartlabcommons.api")
@SpringBootApplication(scanBasePackages={
		"de.qaware.smartlabdelegate",
		"de.qaware.smartlabcommons",
		"de.qaware.smartlabcore.generic"})
public class SmartLabDelegateMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabDelegateMicroserviceApplication.class, args);
	}
}
