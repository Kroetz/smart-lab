package de.qaware.smartlabmonolith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackageClasses = {
		de.qaware.smartlabapi.client.ComponentScanMarker.class,
		de.qaware.smartlabaction.action.external.ComponentScanMarker.class})
@SpringBootApplication(scanBasePackageClasses = {
		de.qaware.smartlabcore.ComponentScanMarker.class,
		de.qaware.smartlabapi.ComponentScanMarker.class,
		de.qaware.smartlabmeeting.ComponentScanMarker.class,
		de.qaware.smartlabroom.ComponentScanMarker.class,
		de.qaware.smartlabdevice.ComponentScanMarker.class,
		de.qaware.smartlabworkgroup.ComponentScanMarker.class,
		de.qaware.smartlabperson.ComponentScanMarker.class,
		de.qaware.smartlabtrigger.ComponentScanMarker.class,
		de.qaware.smartlabassistance.ComponentScanMarker.class,
		de.qaware.smartlabaction.ComponentScanMarker.class,
		de.qaware.smartlabsampledata.ComponentScanMarker.class,
		de.qaware.smartlabmonolith.ComponentScanMarker.class})
public class SmartLabMonolithApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabMonolithApplication.class, args);
	}
}
