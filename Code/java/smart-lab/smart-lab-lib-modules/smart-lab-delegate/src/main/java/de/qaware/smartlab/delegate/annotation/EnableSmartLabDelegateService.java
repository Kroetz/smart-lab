package de.qaware.smartlab.delegate.annotation;

import de.qaware.smartlab.delegate.configuration.DelegateServiceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(DelegateServiceConfiguration.class)
public @interface EnableSmartLabDelegateService { }
