package de.qaware.smartlabaction.configuration;

import de.qaware.smartlabaction.action.windowhandling.configuration.WindowHandlerConfiguration;
import de.qaware.smartlabapi.annotation.EnableSmartLabApi;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlabaction.action.info.ComponentScanMarker.class,
        de.qaware.smartlabaction.action.executable.ComponentScanMarker.class,
        de.qaware.smartlabaction.action.actor.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
@EnableFeignClients(basePackageClasses = de.qaware.smartlabaction.action.externalapi.ComponentScanMarker.class)
@Import(WindowHandlerConfiguration.class)
public class ActionExecutablesConfiguration { }
