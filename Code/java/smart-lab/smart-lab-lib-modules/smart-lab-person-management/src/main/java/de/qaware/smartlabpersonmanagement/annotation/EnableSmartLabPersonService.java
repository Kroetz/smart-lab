package de.qaware.smartlabpersonmanagement.annotation;

import de.qaware.smartlabpersonmanagement.configuration.PersonServiceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(PersonServiceConfiguration.class)
public @interface EnableSmartLabPersonService { }
