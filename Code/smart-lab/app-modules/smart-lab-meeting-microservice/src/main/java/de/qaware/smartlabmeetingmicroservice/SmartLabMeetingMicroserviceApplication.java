package de.qaware.smartlabmeetingmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "de.qaware.smartlabapi.client")
@SpringBootApplication(scanBasePackages={
		"de.qaware.smartlabcore.generic",
		"de.qaware.smartlabapi",
		"de.qaware.smartlabmeeting",
		"de.qaware.smartlabsampledata"})
public class SmartLabMeetingMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabMeetingMicroserviceApplication.class, args);
	}
}
