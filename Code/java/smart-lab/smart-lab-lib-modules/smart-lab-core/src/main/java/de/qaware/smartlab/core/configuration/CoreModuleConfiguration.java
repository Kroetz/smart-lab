package de.qaware.smartlab.core.configuration;

import de.qaware.smartlab.core.ComponentScanMarker;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = ComponentScanMarker.class)
public class CoreModuleConfiguration { }
