package de.qaware.smartlab.assistance.configuration;

import de.qaware.smartlab.core.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = de.qaware.smartlab.assistance.assistances.info.ComponentScanMarker.class)
@EnableSmartLabCore
public class AssistanceInfosConfiguration { }
