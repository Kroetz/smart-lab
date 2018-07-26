package de.qaware.smartlabapi.annotation;

import de.qaware.smartlabapi.configuration.ApiModuleConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(ApiModuleConfiguration.class)
public @interface EnableSmartLabApi { }
