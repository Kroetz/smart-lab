package de.qaware.smartlabaction.annotation;

import de.qaware.smartlabaction.configuration.ActionServiceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(ActionServiceConfiguration.class)
public @interface EnableSmartLabActionService { }
