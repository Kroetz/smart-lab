package de.qaware.smartlabtrigger.configuration;

import de.qaware.smartlabapi.annotation.EnableSmartLabApi;
import de.qaware.smartlabassistance.annotation.EnableAssistanceTriggerables;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@ComponentScan(basePackageClasses = de.qaware.smartlabtrigger.ComponentScanMarker.class)
@EnableSmartLabCore
@EnableSmartLabApi
@EnableAssistanceTriggerables
@EnableAsync
public class TriggerModuleConfiguration { }
