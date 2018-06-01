package de.qaware.smartlabtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "de.qaware.smartlabapi.client")
@SpringBootApplication(scanBasePackages = {
		"de.qaware.smartlabcore",
		"de.qaware.smartlabapi",
        "de.qaware.smartlabsampledata",
    	"de.qaware.smartlabmeeting.business",
		"de.qaware.smartlabmeeting.repository"})
public class SmartLabTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabTestApplication.class, args);
	}
}
