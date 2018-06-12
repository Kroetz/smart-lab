package de.qaware.smartlabmeeting.configuration;

import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlabapi.ComponentScanMarker.class,
        de.qaware.smartlabsampledata.ComponentScanMarker.class,
        de.qaware.smartlabmeeting.ComponentScanMarker.class})
@EnableSmartLabCore
public class MeetingModuleConfiguration { }
