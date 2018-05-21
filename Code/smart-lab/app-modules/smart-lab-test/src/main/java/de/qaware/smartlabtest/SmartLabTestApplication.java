package de.qaware.smartlabtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
        "de.qaware.smartlabcommons.api",
        "de.qaware.smartlabsampledata",
    	"de.qaware.smartlabmeeting.business",
		"de.qaware.smartlabmeeting.repository"})
@EnableFeignClients(basePackages = "de.qaware.smartlabcommons.api.internal.client")
public class SmartLabTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabTestApplication.class, args);
	}
}
