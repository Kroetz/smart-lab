package de.qaware.smartlabtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {
		de.qaware.smartlabcore.ComponentScanMarker.class,
		de.qaware.smartlabapi.ComponentScanMarker.class,
        de.qaware.smartlabsampledata.ComponentScanMarker.class})
public class SmartLabTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabTestApplication.class, args);
	}
}
