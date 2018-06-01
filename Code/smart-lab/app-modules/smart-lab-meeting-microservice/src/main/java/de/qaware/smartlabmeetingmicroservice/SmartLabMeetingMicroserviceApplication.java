package de.qaware.smartlabmeetingmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = de.qaware.smartlabapi.client.ComponentScanMarker.class)
@SpringBootApplication(scanBasePackageClasses = {
		de.qaware.smartlabcore.ComponentScanMarker.class,
		de.qaware.smartlabapi.ComponentScanMarker.class,
		de.qaware.smartlabmeeting.ComponentScanMarker.class,
		de.qaware.smartlabsampledata.ComponentScanMarker.class})
public class SmartLabMeetingMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabMeetingMicroserviceApplication.class, args);
	}
}
