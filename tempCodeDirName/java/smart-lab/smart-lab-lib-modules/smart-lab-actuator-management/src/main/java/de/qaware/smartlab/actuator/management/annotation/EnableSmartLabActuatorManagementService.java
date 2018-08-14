package de.qaware.smartlab.actuator.management.annotation;

import de.qaware.smartlab.actuator.management.configuration.ActuatorManagementServiceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(ActuatorManagementServiceConfiguration.class)
public @interface EnableSmartLabActuatorManagementService { }
