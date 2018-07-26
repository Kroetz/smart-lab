package de.qaware.smartlabdelegate.annotation;

import de.qaware.smartlabdelegate.configuration.DelegateServiceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(DelegateServiceConfiguration.class)
public @interface EnableSmartLabDelegateService { }
