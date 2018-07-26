package de.qaware.smartlabjob.annotation;

import de.qaware.smartlabjob.configuration.JobServiceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(JobServiceConfiguration.class)
public @interface EnableSmartLabJobService { }
