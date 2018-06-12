package de.qaware.smartlabaction.configuration;


import de.qaware.smartlabapi.annotation.EnableSmartLabApi;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlabaction.controller.ComponentScanMarker.class,
        de.qaware.smartlabaction.business.ComponentScanMarker.class,
        de.qaware.smartlabaction.action.info.ComponentScanMarker.class,
        de.qaware.smartlabaction.action.executable.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
@EnableFeignClients(basePackageClasses = de.qaware.smartlabaction.action.executable.external.ComponentScanMarker.class)
public class ActionServiceConfiguration { }
