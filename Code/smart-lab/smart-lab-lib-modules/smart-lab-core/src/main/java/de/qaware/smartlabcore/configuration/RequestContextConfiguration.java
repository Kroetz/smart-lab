package de.qaware.smartlabcore.configuration;

import de.qaware.smartlabcore.generic.controller.SmartLabRequestContextListener;
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
