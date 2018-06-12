package de.qaware.smartlabassistance.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlabcore.ComponentScanMarker.class,
        de.qaware.smartlabapi.ComponentScanMarker.class,
        de.qaware.smartlabassistance.controller.ComponentScanMarker.class,
        de.qaware.smartlabassistance.business.ComponentScanMarker.class,
        de.qaware.smartlabassistance.assistance.info.ComponentScanMarker.class,
        de.qaware.smartlabassistance.assistance.controllable.ComponentScanMarker.class,
        de.qaware.smartlabaction.ComponentScanMarker.class})
public class AssistanceServiceConfiguration { }
