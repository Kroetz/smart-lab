package de.qaware.smartlabroomconfigprovidermock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"de.qaware.smartlabroomconfigprovidermock", "de.qaware.smartlabcore.data.sample"})
public class SmartLabRoomConfigProviderMockApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLabRoomConfigProviderMockApplication.class, args);
	}
}
