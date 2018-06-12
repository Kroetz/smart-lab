package de.qaware.smartlabsampledata.configuration;

import de.qaware.smartlabassistance.annotation.EnableAssistanceInfo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlabcore.ComponentScanMarker.class,
        de.qaware.smartlabapi.ComponentScanMarker.class,
        de.qaware.smartlabsampledata.ComponentScanMarker.class,
        de.qaware.smartlabaction.ComponentScanMarker.class})
@EnableAssistanceInfo
public class SampleDataModuleConfiguration { }
