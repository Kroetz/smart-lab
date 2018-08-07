package de.qaware.smartlab.data.conversion.annotation;

import de.qaware.smartlab.data.conversion.configuration.DtoConvertersConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(DtoConvertersConfiguration.class)
public @interface EnableSmartLabDtoConverters { }
