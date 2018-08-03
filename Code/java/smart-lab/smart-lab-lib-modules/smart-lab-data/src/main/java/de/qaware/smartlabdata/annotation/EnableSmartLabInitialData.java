package de.qaware.smartlabdata.annotation;

import de.qaware.smartlabdata.configuration.InitialDataConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(InitialDataConfiguration.class)
public @interface EnableSmartLabInitialData { }
