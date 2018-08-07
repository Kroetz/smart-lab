package de.qaware.smartlab.gui.microservice;

import de.qaware.smartlab.gui.annotation.EnableSmartLabGui;
import de.qaware.smartlabmicroservice.annotation.EnableSmartLabMicroservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableSmartLabMicroservice
@EnableSmartLabGui
@EnableDiscoveryClient
public class SmartLabGuiMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartLabGuiMicroserviceApplication.class, args);
    }
}
