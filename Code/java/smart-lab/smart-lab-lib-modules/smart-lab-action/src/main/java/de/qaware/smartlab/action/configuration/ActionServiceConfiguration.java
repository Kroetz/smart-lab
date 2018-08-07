package de.qaware.smartlab.action.configuration;


import de.qaware.smartlab.action.annotation.EnableSmartLabActionExecutables;
import de.qaware.smartlab.api.annotation.EnableSmartLabApi;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackageClasses = de.qaware.smartlab.action.service.ComponentScanMarker.class)
@EnableSmartLabCore
@EnableSmartLabApi
@EnableSmartLabActionExecutables
@Import(ActionInfosConfiguration.class)
public class ActionServiceConfiguration { }
