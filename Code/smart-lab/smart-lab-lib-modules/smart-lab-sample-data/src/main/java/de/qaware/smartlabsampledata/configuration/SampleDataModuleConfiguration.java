package de.qaware.smartlabsampledata.configuration;

import de.qaware.smartlabaction.annotation.EnableSmartLabActionInfos;
import de.qaware.smartlabassistance.annotation.EnableSmartLabAssistanceInfos;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = de.qaware.smartlabsampledata.ComponentScanMarker.class)
@EnableSmartLabCore
@EnableSmartLabAssistanceInfos
@EnableSmartLabActionInfos
public class SampleDataModuleConfiguration { }
