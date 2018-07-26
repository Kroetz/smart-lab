package de.qaware.smartlabaction.annotation;

import de.qaware.smartlabaction.configuration.ActionExecutablesConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(ActionExecutablesConfiguration.class)
public @interface EnableSmartLabActionExecutables { }
