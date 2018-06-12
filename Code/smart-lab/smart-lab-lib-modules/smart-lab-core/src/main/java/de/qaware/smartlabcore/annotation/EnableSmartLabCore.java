package de.qaware.smartlabcore.annotation;

import de.qaware.smartlabcore.configuration.CoreModuleConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(CoreModuleConfiguration.class)
public @interface EnableSmartLabCore { }
