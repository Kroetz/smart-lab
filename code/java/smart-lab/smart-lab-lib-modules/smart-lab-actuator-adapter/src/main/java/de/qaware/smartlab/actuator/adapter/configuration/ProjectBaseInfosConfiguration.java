package de.qaware.smartlab.actuator.adapter.configuration;

import de.qaware.smartlab.core.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = de.qaware.smartlab.actuator.adapter.adapters.projectbase.info.ComponentScanMarker.class)
@EnableSmartLabCore
public class ProjectBaseInfosConfiguration { }
