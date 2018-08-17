package de.qaware.smartlab.monolith.service;

import de.qaware.smartlab.monolith.annotation.EnableSmartLabMonolith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSmartLabMonolith
public class SmartLabMonolithicServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartLabMonolithicServiceApplication.class, args);
    }
}
