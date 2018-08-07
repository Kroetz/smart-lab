package de.qaware.smartlabdelegate.configuration;

import de.qaware.smartlab.action.annotation.EnableSmartLabActionExecutables;
import de.qaware.smartlab.api.annotation.EnableSmartLabApi;
import de.qaware.smartlab.core.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {de.qaware.smartlabdelegate.service.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
@EnableSmartLabActionExecutables
public class DelegateServiceConfiguration { }
