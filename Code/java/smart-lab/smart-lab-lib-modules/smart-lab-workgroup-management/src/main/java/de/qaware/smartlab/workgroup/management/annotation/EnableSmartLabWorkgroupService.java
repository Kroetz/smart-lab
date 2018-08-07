package de.qaware.smartlab.workgroup.management.annotation;

import de.qaware.smartlab.workgroup.management.configuration.WorkgroupServiceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(WorkgroupServiceConfiguration.class)
public @interface EnableSmartLabWorkgroupService { }
