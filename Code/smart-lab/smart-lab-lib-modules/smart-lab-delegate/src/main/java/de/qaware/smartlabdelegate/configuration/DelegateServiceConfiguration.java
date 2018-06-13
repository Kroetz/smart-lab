package de.qaware.smartlabdelegate.configuration;

import de.qaware.smartlabaction.annotation.EnableSmartLabActionExecutables;
import de.qaware.smartlabapi.annotation.EnableSmartLabApi;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlabdelegate.controller.ComponentScanMarker.class,
        de.qaware.smartlabdelegate.business.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
@EnableSmartLabActionExecutables
public class DelegateServiceConfiguration { }
