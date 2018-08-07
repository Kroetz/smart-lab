package de.qaware.smartlab.assistance.configuration;

import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = de.qaware.smartlab.assistance.assistances.info.ComponentScanMarker.class)
@EnableSmartLabCore
public class AssistanceInfosConfiguration { }
