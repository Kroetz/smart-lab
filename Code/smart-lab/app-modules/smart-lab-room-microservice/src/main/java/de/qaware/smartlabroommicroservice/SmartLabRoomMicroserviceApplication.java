package de.qaware.smartlabroommicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "de.qaware.smartlabcommons.api.internal.client")
@SpringBootApplication(scanBasePackages={
		"de.qaware.smartlabroom",
		"de.qaware.smartlabcommons",
		"de.qaware.smartlabcore.generic",
		"de.qaware.smartlabsampledata"})
public class SmartLabRoomMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabRoomMicroserviceApplication.class, args);
	}
}
