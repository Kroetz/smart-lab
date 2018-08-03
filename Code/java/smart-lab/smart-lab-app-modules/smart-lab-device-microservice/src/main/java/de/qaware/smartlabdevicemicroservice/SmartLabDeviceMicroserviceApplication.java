package de.qaware.smartlabdevicemicroservice;

import de.qaware.smartlabdevice.annotation.EnableSmartLabDeviceService;
import de.qaware.smartlabmicroservice.annotation.EnableSmartLabMicroservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableSmartLabMicroservice
@EnableSmartLabDeviceService
@EnableDiscoveryClient
public class SmartLabDeviceMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabDeviceMicroserviceApplication.class, args);
	}
}
