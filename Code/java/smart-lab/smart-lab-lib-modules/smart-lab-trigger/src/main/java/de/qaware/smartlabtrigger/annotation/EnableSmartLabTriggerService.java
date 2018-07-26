package de.qaware.smartlabtrigger.annotation;

import de.qaware.smartlabtrigger.configuration.TriggerServiceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(TriggerServiceConfiguration.class)
public @interface EnableSmartLabTriggerService { }
