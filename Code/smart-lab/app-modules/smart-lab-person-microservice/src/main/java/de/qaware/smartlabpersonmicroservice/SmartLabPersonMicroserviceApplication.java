package de.qaware.smartlabpersonmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "de.qaware.smartlabapi.client")
@SpringBootApplication(scanBasePackages={
		"de.qaware.smartlabperson",
		"de.qaware.smartlabcommons",
		"de.qaware.smartlabapi",
		"de.qaware.smartlabcore.generic",
		"de.qaware.smartlabsampledata"})
public class SmartLabPersonMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabPersonMicroserviceApplication.class, args);
	}
}
