package de.qaware.smartlabactionmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = {
		de.qaware.smartlabapi.client.ComponentScanMarker.class,
		de.qaware.smartlabaction.action.external.ComponentScanMarker.class})
@SpringBootApplication(scanBasePackageClasses = {
		de.qaware.smartlabcore.ComponentScanMarker.class,
		de.qaware.smartlabapi.ComponentScanMarker.class,
		de.qaware.smartlabaction.ComponentScanMarker.class})
public class SmartLabActionMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabActionMicroserviceApplication.class, args);
	}
}
