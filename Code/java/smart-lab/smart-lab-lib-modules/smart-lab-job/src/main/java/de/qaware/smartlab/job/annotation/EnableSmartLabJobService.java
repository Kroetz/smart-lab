package de.qaware.smartlab.job.annotation;

import de.qaware.smartlab.job.configuration.JobServiceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(JobServiceConfiguration.class)
public @interface EnableSmartLabJobService { }
