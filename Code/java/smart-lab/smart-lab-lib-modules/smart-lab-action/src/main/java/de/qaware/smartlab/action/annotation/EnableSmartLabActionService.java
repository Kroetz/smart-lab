package de.qaware.smartlab.action.annotation;

import de.qaware.smartlab.action.configuration.ActionServiceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(ActionServiceConfiguration.class)
public @interface EnableSmartLabActionService { }
