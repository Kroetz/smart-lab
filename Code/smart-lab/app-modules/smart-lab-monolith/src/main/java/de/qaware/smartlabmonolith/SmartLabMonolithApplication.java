package de.qaware.smartlabmonolith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackageClasses = de.qaware.smartlabtrigger.business.IJobInfoRepository.class)
@EntityScan(basePackageClasses = de.qaware.smartlabtrigger.business.JobInfo.class)
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
