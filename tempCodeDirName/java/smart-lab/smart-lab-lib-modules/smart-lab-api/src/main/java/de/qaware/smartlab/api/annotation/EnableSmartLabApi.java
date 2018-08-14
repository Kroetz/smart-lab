package de.qaware.smartlab.api.annotation;

import de.qaware.smartlab.api.configuration.ApiModuleConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(ApiModuleConfiguration.class)
public @interface EnableSmartLabApi { }
