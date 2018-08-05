package de.qaware.smartlabactuatoradapter.configuration;

import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = de.qaware.smartlabactuatoradapter.actuator.projectbase.info.ComponentScanMarker.class)
@EnableSmartLabCore
public class ProjectBaseInfosConfiguration { }