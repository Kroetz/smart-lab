package de.qaware.smartlabaction.annotation;

import de.qaware.smartlabaction.configuration.ProjectBaseInfosConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(ProjectBaseInfosConfiguration.class)
public @interface EnableSmartLabProjectBaseInfos { }
