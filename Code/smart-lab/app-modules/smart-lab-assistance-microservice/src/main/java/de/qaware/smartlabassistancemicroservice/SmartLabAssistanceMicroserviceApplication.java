package de.qaware.smartlabassistancemicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "de.qaware.smartlabapi.client")
@SpringBootApplication(scanBasePackages={
		"de.qaware.smartlabcore",
		"de.qaware.smartlabapi",
		"de.qaware.smartlabassistance",
		"de.qaware.smartlabaction"})
public class SmartLabAssistanceMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabAssistanceMicroserviceApplication.class, args);
	}
}
