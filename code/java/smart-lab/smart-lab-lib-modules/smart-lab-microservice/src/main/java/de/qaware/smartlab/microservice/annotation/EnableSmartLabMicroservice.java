package de.qaware.smartlab.microservice.annotation;

import de.qaware.smartlab.microservice.configuration.MicroserviceModuleConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(MicroserviceModuleConfiguration.class)
public @interface EnableSmartLabMicroservice { }
