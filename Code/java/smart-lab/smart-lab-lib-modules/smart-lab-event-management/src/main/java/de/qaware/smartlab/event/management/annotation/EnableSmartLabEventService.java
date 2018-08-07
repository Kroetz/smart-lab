package de.qaware.smartlab.event.management.annotation;

import de.qaware.smartlab.event.management.configuration.EventServiceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(EventServiceConfiguration.class)
public @interface EnableSmartLabEventService { }
