package de.qaware.smartlabsampledata.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlabcore.ComponentScanMarker.class,
        de.qaware.smartlabapi.ComponentScanMarker.class,
        de.qaware.smartlabsampledata.ComponentScanMarker.class,
        de.qaware.smartlabassistance.ComponentScanMarker.class,
        de.qaware.smartlabaction.ComponentScanMarker.class})
public class SampleDataModuleConfiguration { }
