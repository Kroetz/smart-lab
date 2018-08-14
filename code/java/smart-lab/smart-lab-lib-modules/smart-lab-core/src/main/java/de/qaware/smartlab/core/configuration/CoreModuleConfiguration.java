package de.qaware.smartlab.core.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = de.qaware.smartlab.core.ComponentScanMarker.class)
public class CoreModuleConfiguration { }
