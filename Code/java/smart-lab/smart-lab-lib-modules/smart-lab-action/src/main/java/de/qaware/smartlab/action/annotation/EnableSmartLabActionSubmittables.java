package de.qaware.smartlab.action.annotation;

import de.qaware.smartlab.action.configuration.ActionSubmittablesConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(ActionSubmittablesConfiguration.class)
public @interface EnableSmartLabActionSubmittables { }
