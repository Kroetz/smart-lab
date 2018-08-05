package de.qaware.smartlabactuatoradapter.annotation;

import de.qaware.smartlabactuatoradapter.configuration.ActuatorAdaptersConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(ActuatorAdaptersConfiguration.class)
public @interface EnableSmartLabActuatorAdapters { }