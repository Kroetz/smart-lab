package de.qaware.smartlabdataconversion.configuration;

import de.qaware.smartlab.actuator.adapter.annotation.EnableSmartLabProjectBaseInfos;
import de.qaware.smartlab.assistance.annotation.EnableSmartLabAssistanceInfos;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = de.qaware.smartlabdataconversion.converter.ComponentScanMarker.class)
@EnableSmartLabCore
@EnableSmartLabAssistanceInfos
@EnableSmartLabProjectBaseInfos
public class DtoConvertersConfiguration { }
