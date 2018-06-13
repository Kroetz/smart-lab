package de.qaware.smartlabassistance.configuration;

import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = de.qaware.smartlabassistance.assistance.info.ComponentScanMarker.class)
@EnableSmartLabCore
public class AssistanceInfosConfiguration { }
