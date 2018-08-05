package de.qaware.smartlabactuatoradapter.configuration;

import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = de.qaware.smartlabactuatoradapter.actuator.ComponentScanMarker.class)
@EnableSmartLabCore
@EnableFeignClients(basePackageClasses = de.qaware.smartlabactuatoradapter.actuator.externalapi.ComponentScanMarker.class)
public class ActuatorAdaptersConfiguration { }
