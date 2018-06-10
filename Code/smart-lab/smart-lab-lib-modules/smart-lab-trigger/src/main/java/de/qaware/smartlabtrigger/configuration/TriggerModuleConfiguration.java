package de.qaware.smartlabtrigger.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlabcore.ComponentScanMarker.class,
        de.qaware.smartlabapi.ComponentScanMarker.class,
        de.qaware.smartlabtrigger.ComponentScanMarker.class,
        de.qaware.smartlabassistance.ComponentScanMarker.class})
@EnableAsync
public class TriggerModuleConfiguration { }
