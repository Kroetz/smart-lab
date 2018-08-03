package de.qaware.smartlabdata.configuration;

import de.qaware.smartlabaction.annotation.EnableSmartLabProjectBaseInfos;
import de.qaware.smartlabassistance.annotation.EnableSmartLabAssistanceInfos;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = de.qaware.smartlabdata.converter.ComponentScanMarker.class)
@EnableSmartLabCore
@EnableSmartLabAssistanceInfos
@EnableSmartLabProjectBaseInfos
public class DtoConvertersConfiguration { }
