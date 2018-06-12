package de.qaware.smartlabassistance.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlabcore.ComponentScanMarker.class,
        de.qaware.smartlabapi.ComponentScanMarker.class,
        de.qaware.smartlabassistance.assistance.triggerable.ComponentScanMarker.class,
        de.qaware.smartlabassistance.assistance.info.ComponentScanMarker.class})
public class AssistanceTriggerablesConfiguration { }
