package de.qaware.smartlablocationmanagement.annotation;

import de.qaware.smartlablocationmanagement.configuration.LocationServiceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(LocationServiceConfiguration.class)
public @interface EnableSmartLabLocationService { }
