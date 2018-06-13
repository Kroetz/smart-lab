package de.qaware.smartlabsampledata.annotation;

import de.qaware.smartlabsampledata.configuration.SampleDataModuleConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(SampleDataModuleConfiguration.class)
public @interface EnableSmartLabSampleData { }
