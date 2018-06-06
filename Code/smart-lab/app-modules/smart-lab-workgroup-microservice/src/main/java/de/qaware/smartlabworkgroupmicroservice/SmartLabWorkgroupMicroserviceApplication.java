package de.qaware.smartlabworkgroupmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackageClasses = {
		de.qaware.smartlabcore.ComponentScanMarker.class,
		de.qaware.smartlabapi.ComponentScanMarker.class,
		de.qaware.smartlabworkgroup.ComponentScanMarker.class,
		de.qaware.smartlabsampledata.ComponentScanMarker.class})
public class SmartLabWorkgroupMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabWorkgroupMicroserviceApplication.class, args);
	}
}
