package de.qaware.smartlabdevicemicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackageClasses = {
		de.qaware.smartlabcore.ComponentScanMarker.class,
		de.qaware.smartlabapi.ComponentScanMarker.class,
		de.qaware.smartlabdevice.ComponentScanMarker.class,
		de.qaware.smartlabsampledata.ComponentScanMarker.class})
public class SmartLabDeviceMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabDeviceMicroserviceApplication.class, args);
	}
}
