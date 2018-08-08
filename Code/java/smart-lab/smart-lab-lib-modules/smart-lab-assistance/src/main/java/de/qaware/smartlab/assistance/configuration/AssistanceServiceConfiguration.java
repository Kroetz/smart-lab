package de.qaware.smartlab.assistance.configuration;

import de.qaware.smartlab.api.annotation.EnableSmartLabApi;
import de.qaware.smartlab.assistance.annotation.EnableSmartLabAssistanceControllables;
import de.qaware.smartlab.core.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = de.qaware.smartlab.assistance.service.ComponentScanMarker.class)
@EnableSmartLabCore
@EnableSmartLabApi
@EnableSmartLabAssistanceControllables
public class AssistanceServiceConfiguration { }
