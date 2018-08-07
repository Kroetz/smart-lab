package de.qaware.smartlab.assistance.annotation;

import de.qaware.smartlab.assistance.configuration.AssistanceServiceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(AssistanceServiceConfiguration.class)
public @interface EnableSmartLabAssistanceService { }