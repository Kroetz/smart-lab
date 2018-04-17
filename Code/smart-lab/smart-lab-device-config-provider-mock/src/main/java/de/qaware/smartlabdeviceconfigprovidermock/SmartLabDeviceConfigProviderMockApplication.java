package de.qaware.smartlabdeviceconfigprovidermock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"de.qaware.smartlabdeviceconfigprovidermock", "de.qaware.smartlabcore.data.sample"})
public class SmartLabDeviceConfigProviderMockApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabDeviceConfigProviderMockApplication.class, args);
	}
}
