package de.qaware.smartlabdelegatemicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackageClasses = {
		de.qaware.smartlabcore.ComponentScanMarker.class,
		de.qaware.smartlabapi.ComponentScanMarker.class,		// TODO: API should not be needed in the delegate module
		de.qaware.smartlabdelegate.ComponentScanMarker.class,
		de.qaware.smartlabaction.ComponentScanMarker.class})
public class SmartLabDelegateMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabDelegateMicroserviceApplication.class, args);
	}
}
