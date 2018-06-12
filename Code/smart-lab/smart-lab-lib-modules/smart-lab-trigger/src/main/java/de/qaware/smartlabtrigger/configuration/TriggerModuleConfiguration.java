package de.qaware.smartlabtrigger.configuration;

import de.qaware.smartlabassistance.annotation.EnableAssistanceTriggerables;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlabcore.ComponentScanMarker.class,
        de.qaware.smartlabapi.ComponentScanMarker.class,
        de.qaware.smartlabtrigger.ComponentScanMarker.class})
@EnableAssistanceTriggerables
@EnableAsync
public class TriggerModuleConfiguration { }
