package de.qaware.smartlab.location.management.annotation;

import de.qaware.smartlab.location.management.configuration.LocationManagementServiceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(LocationManagementServiceConfiguration.class)
public @interface EnableSmartLabLocationManagementService { }
