package de.qaware.smartlab.actuator.adapter.configuration;

import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = de.qaware.smartlab.actuator.adapter.adapters.ComponentScanMarker.class)
@EnableSmartLabCore
public class ActuatorAdaptersConfiguration { }
