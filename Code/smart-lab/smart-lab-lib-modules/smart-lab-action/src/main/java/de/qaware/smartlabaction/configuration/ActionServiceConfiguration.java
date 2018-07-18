package de.qaware.smartlabaction.configuration;


import de.qaware.smartlabapi.annotation.EnableSmartLabApi;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlabaction.service.ComponentScanMarker.class,
        de.qaware.smartlabaction.action.info.ComponentScanMarker.class,
        de.qaware.smartlabaction.action.executable.ComponentScanMarker.class,
        de.qaware.smartlabaction.action.actor.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
@EnableFeignClients(basePackageClasses = de.qaware.smartlabaction.action.externalapi.ComponentScanMarker.class)
public class ActionServiceConfiguration { }
