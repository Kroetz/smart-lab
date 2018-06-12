package de.qaware.smartlabaction.configuration;

import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = de.qaware.smartlabaction.action.external.ComponentScanMarker.class)
@ComponentScan(basePackageClasses = {
        de.qaware.smartlabapi.ComponentScanMarker.class,
        de.qaware.smartlabaction.ComponentScanMarker.class})
@EnableSmartLabCore
public class ActionModuleConfiguration { }
