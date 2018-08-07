package de.qaware.smartlab.data.set.annotation;

import de.qaware.smartlab.data.set.configuration.InitialDataConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(InitialDataConfiguration.class)
public @interface EnableSmartLabInitialData { }
