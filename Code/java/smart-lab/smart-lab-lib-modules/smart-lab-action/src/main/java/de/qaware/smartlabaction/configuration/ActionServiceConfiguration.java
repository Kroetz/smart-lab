package de.qaware.smartlabaction.configuration;


import de.qaware.smartlabaction.annotation.EnableSmartLabActionExecutables;
import de.qaware.smartlabapi.annotation.EnableSmartLabApi;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackageClasses = de.qaware.smartlabaction.service.ComponentScanMarker.class)
@EnableSmartLabCore
@EnableSmartLabApi
@EnableSmartLabActionExecutables
@Import(ActionInfosConfiguration.class)
public class ActionServiceConfiguration { }
