package de.qaware.smartlabtest;

import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {
		de.qaware.smartlabapi.ComponentScanMarker.class,
        de.qaware.smartlabsampledata.ComponentScanMarker.class})
@EnableSmartLabCore
public class SmartLabTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabTestApplication.class, args);
	}
}
