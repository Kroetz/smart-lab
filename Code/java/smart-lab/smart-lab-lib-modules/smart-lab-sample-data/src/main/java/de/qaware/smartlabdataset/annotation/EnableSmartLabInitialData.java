package de.qaware.smartlabdataset.annotation;

import de.qaware.smartlabdataset.configuration.InitialDataConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(InitialDataConfiguration.class)
public @interface EnableSmartLabInitialData { }
