package de.qaware.smartlabperson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "de.qaware.smartlabcommons.api.client")
@SpringBootApplication(scanBasePackages={
		"de.qaware.smartlabperson",
		"de.qaware.smartlabcommons",
		"de.qaware.smartlabcore.generic",
		"de.qaware.smartlabsampledata"})
public class SmartLabPersonApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabPersonApplication.class, args);
	}
}
