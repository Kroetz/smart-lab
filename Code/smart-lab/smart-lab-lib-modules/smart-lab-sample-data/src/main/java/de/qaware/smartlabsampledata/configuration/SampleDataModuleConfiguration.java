package de.qaware.smartlabsampledata.configuration;

import de.qaware.smartlabassistance.annotation.EnableAssistanceInfo;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlabapi.ComponentScanMarker.class,
        de.qaware.smartlabsampledata.ComponentScanMarker.class,
        de.qaware.smartlabaction.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableAssistanceInfo
public class SampleDataModuleConfiguration { }
