package de.qaware.smartlabdataconversion.annotation;

import de.qaware.smartlabdataconversion.configuration.DtoConvertersConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(DtoConvertersConfiguration.class)
public @interface EnableSmartLabDtoConverters { }
