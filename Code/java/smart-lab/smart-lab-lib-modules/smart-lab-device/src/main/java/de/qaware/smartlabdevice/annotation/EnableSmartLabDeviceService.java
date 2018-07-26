package de.qaware.smartlabdevice.annotation;

import de.qaware.smartlabdevice.configuration.DeviceServiceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(DeviceServiceConfiguration.class)
public @interface EnableSmartLabDeviceService { }
