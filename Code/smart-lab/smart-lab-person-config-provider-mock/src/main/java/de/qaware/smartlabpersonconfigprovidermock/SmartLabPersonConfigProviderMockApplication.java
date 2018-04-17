package de.qaware.smartlabpersonconfigprovidermock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"de.qaware.smartlabpersonconfigprovidermock", "de.qaware.smartlabcore.data.sample"})
public class SmartLabPersonConfigProviderMockApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabPersonConfigProviderMockApplication.class, args);
	}
}
