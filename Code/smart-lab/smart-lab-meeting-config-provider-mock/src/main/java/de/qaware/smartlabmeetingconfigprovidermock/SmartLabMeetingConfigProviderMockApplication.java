package de.qaware.smartlabmeetingconfigprovidermock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"de.qaware.smartlabmeetingconfigprovidermock", "de.qaware.smartlabcore.data.sample"})
public class SmartLabMeetingConfigProviderMockApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabMeetingConfigProviderMockApplication.class, args);
	}
}
