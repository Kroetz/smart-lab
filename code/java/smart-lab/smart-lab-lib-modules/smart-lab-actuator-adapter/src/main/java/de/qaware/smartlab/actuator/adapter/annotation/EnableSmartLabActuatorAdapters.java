package de.qaware.smartlab.actuator.adapter.annotation;

import de.qaware.smartlab.actuator.adapter.configuration.ActuatorAdaptersConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(ActuatorAdaptersConfiguration.class)
public @interface EnableSmartLabActuatorAdapters { }
