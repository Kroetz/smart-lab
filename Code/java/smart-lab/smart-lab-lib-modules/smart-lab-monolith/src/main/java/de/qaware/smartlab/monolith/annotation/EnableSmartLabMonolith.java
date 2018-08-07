package de.qaware.smartlab.monolith.annotation;

import de.qaware.smartlab.monolith.configuration.MonolithModuleConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(MonolithModuleConfiguration.class)
public @interface EnableSmartLabMonolith { }
