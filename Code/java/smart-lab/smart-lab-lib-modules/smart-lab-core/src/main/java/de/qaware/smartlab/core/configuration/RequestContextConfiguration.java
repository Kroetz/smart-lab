package de.qaware.smartlab.core.configuration;

import de.qaware.smartlab.core.service.controller.SmartLabRequestContextListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletRequestListener;

@Configuration
public class RequestContextConfiguration {

    @Bean
    public ServletRequestListener requestContextListener(){
        return new SmartLabRequestContextListener();
    }
}
