package de.qaware.smartlabpersonmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = de.qaware.smartlabapi.client.ComponentScanMarker.class)
@SpringBootApplication(scanBasePackageClasses = {
		de.qaware.smartlabcore.ComponentScanMarker.class,
		de.qaware.smartlabapi.ComponentScanMarker.class,
		de.qaware.smartlabperson.ComponentScanMarker.class,
		de.qaware.smartlabsampledata.ComponentScanMarker.class})
public class SmartLabPersonMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabPersonMicroserviceApplication.class, args);
	}
}
