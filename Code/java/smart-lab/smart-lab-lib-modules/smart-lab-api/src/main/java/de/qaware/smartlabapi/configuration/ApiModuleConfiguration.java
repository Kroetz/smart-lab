package de.qaware.smartlabapi.configuration;

import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableSmartLabCore
@EnableFeignClients(basePackageClasses = de.qaware.smartlabapi.service.client.ComponentScanMarker.class)
public class ApiModuleConfiguration { }
