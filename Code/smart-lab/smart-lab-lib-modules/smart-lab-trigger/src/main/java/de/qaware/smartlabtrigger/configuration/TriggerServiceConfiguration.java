package de.qaware.smartlabtrigger.configuration;

import de.qaware.smartlabapi.annotation.EnableSmartLabApi;
import de.qaware.smartlabassistance.annotation.EnableSmartLabAssistanceTriggerables;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlabtrigger.controller.ComponentScanMarker.class,
        de.qaware.smartlabtrigger.business.ComponentScanMarker.class,
        de.qaware.smartlabtrigger.context.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
@EnableSmartLabAssistanceTriggerables
@EnableAsync
public class TriggerServiceConfiguration { }
