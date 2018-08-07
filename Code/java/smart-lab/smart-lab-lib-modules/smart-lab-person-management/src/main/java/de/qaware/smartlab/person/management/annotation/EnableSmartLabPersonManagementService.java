package de.qaware.smartlab.person.management.annotation;

import de.qaware.smartlab.person.management.configuration.PersonManagementServiceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(PersonManagementServiceConfiguration.class)
public @interface EnableSmartLabPersonManagementService { }
