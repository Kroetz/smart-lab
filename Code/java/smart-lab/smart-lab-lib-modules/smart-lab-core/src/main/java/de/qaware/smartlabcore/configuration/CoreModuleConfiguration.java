package de.qaware.smartlabcore.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = de.qaware.smartlabcore.ComponentScanMarker.class)
public class CoreModuleConfiguration { }
