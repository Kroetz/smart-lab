package de.qaware.smartlabmonolith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {
		de.qaware.smartlabmeeting.ComponentScanMarker.class,
		de.qaware.smartlabroom.ComponentScanMarker.class,
		de.qaware.smartlabdevice.ComponentScanMarker.class,
		de.qaware.smartlabworkgroup.ComponentScanMarker.class,
		de.qaware.smartlabperson.ComponentScanMarker.class,
		de.qaware.smartlabtrigger.ComponentScanMarker.class,
		de.qaware.smartlabassistance.ComponentScanMarker.class,
		de.qaware.smartlabaction.ComponentScanMarker.class,
		de.qaware.smartlabjob.ComponentScanMarker.class,
		de.qaware.smartlabmonolith.ComponentScanMarker.class})
public class SmartLabMonolithApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabMonolithApplication.class, args);
	}
}
