package de.qaware.smartlabassistance.annotation;

import de.qaware.smartlabassistance.configuration.AssistanceInfoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(AssistanceInfoConfiguration.class)
public @interface EnableAssistanceInfo { }
