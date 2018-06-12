package de.qaware.smartlabassistance.configuration;

import de.qaware.smartlabapi.annotation.EnableSmartLabApi;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlabassistance.controller.ComponentScanMarker.class,
        de.qaware.smartlabassistance.business.ComponentScanMarker.class,
        de.qaware.smartlabassistance.assistance.info.ComponentScanMarker.class,
        de.qaware.smartlabassistance.assistance.controllable.ComponentScanMarker.class,
        de.qaware.smartlabaction.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
public class AssistanceServiceConfiguration { }
