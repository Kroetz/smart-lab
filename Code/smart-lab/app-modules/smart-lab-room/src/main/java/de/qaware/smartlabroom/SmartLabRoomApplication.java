package de.qaware.smartlabroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "de.qaware.smartlabcommons.api.client")
@SpringBootApplication(scanBasePackages={
		"de.qaware.smartlabroom",
		"de.qaware.smartlabcommons",
        "de.qaware.smartlabcore.generic",
		"de.qaware.smartlabsampledata"})
public class SmartLabRoomApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabRoomApplication.class, args);
	}
}
