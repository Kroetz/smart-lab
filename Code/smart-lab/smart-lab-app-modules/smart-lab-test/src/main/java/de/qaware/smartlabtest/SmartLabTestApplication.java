package de.qaware.smartlabtest;

import de.qaware.smartlabapi.annotation.EnableSmartLabApi;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = de.qaware.smartlabsampledata.ComponentScanMarker.class)
@EnableSmartLabCore
@EnableSmartLabApi
public class SmartLabTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabTestApplication.class, args);
	}
}
