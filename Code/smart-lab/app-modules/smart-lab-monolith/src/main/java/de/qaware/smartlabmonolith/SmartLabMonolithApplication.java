package de.qaware.smartlabmonolith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages={
		"de.qaware.smartlabmonolith",
		"de.qaware.smartlabcommons",
		"de.qaware.smartlabcore",
		"de.qaware.smartlabmeeting",
		"de.qaware.smartlabroom",
		"de.qaware.smartlabdevice",
		"de.qaware.smartlabworkgroup",
		"de.qaware.smartlabperson",
		"de.qaware.smartlabtrigger",
		"de.qaware.smartlabassistance",
		"de.qaware.smartlabaction",
		"de.qaware.smartlabsampledata"})
@EnableFeignClients(basePackages = "de.qaware.smartlabcommons.api.external")
public class SmartLabMonolithApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabMonolithApplication.class, args);
	}
}
