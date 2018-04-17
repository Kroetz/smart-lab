package de.qaware.smartlabworkgroupconfigprovidermock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"de.qaware.smartlabworkgroupconfigprovidermock", "de.qaware.smartlabcore.data.sample"})
public class SmartLabWorkgroupConfigProviderMockApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabWorkgroupConfigProviderMockApplication.class, args);
	}
}
