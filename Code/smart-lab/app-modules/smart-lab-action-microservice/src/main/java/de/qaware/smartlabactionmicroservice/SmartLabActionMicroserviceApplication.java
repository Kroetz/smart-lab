package de.qaware.smartlabactionmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = {
		"de.qaware.smartlabapi.client",
		"de.qaware.smartlabcommons.api.external"})
@SpringBootApplication(scanBasePackages={
		"de.qaware.smartlabaction",
		"de.qaware.smartlabcommons",
		"de.qaware.smartlabapi",
		"de.qaware.smartlabcore.generic"})
public class SmartLabActionMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabActionMicroserviceApplication.class, args);
	}
}
