package de.qaware.smartlabaction.configuration;

import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = de.qaware.smartlabaction.action.info.ComponentScanMarker.class)
@EnableSmartLabCore
public class ActionInfosConfiguration { }
