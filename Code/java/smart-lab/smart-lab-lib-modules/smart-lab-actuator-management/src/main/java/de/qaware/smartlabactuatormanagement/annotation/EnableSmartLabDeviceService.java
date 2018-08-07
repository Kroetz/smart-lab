package de.qaware.smartlabactuatormanagement.annotation;

import de.qaware.smartlabactuatormanagement.configuration.DeviceServiceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(DeviceServiceConfiguration.class)
public @interface EnableSmartLabDeviceService { }
