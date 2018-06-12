package de.qaware.smartlabaction.annotation;

import de.qaware.smartlabaction.configuration.ActionInfosConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(ActionInfosConfiguration.class)
public @interface EnableSmartLabActionInfos { }
