package de.qaware.smartlabconfigmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class SmartLabConfigMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartLabConfigMicroserviceApplication.class, args);
    }
}
