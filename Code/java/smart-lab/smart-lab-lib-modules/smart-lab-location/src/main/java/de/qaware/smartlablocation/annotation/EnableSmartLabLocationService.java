package de.qaware.smartlablocation.annotation;

import de.qaware.smartlablocation.configuration.LocationServiceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(LocationServiceConfiguration.class)
public @interface EnableSmartLabLocationService { }
