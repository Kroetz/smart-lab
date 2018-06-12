package de.qaware.smartlabassistance.configuration;

import de.qaware.smartlabapi.annotation.EnableSmartLabApi;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlabassistance.assistance.triggerable.ComponentScanMarker.class,
        de.qaware.smartlabassistance.assistance.info.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
public class AssistanceTriggerablesConfiguration { }
