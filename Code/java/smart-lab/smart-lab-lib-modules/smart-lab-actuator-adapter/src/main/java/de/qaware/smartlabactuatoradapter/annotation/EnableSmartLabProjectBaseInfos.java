package de.qaware.smartlabactuatoradapter.annotation;

import de.qaware.smartlabactuatoradapter.configuration.ProjectBaseInfosConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(ProjectBaseInfosConfiguration.class)
public @interface EnableSmartLabProjectBaseInfos { }
