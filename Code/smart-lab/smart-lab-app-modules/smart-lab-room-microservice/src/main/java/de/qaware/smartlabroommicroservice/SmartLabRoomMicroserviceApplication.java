package de.qaware.smartlabroommicroservice;

import de.qaware.smartlabroom.annotation.EnableSmartLabRoomService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableSmartLabRoomService
@EnableDiscoveryClient
public class SmartLabRoomMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabRoomMicroserviceApplication.class, args);
	}
}
