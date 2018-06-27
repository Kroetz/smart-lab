package de.qaware.smartlabmeeting.configuration;

import de.qaware.smartlabapi.annotation.EnableSmartLabApi;
import de.qaware.smartlabassistance.annotation.EnableSmartLabAssistanceInfos;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import de.qaware.smartlabsampledata.annotation.EnableSmartLabSampleData;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlabmeeting.controller.ComponentScanMarker.class,
        de.qaware.smartlabmeeting.business.ComponentScanMarker.class,
        de.qaware.smartlabmeeting.repository.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
@EnableSmartLabSampleData
@EnableSmartLabAssistanceInfos
public class MeetingServiceConfiguration { }
