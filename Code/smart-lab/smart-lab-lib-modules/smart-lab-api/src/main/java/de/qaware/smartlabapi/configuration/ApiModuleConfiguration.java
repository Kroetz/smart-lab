package de.qaware.smartlabapi.configuration;

import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = de.qaware.smartlabapi.ComponentScanMarker.class)
@EnableSmartLabCore
@EnableFeignClients(basePackageClasses = de.qaware.smartlabapi.client.ComponentScanMarker.class)
public class ApiModuleConfiguration { }
