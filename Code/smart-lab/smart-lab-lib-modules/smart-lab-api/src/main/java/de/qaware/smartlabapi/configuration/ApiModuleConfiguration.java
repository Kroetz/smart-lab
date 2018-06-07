package de.qaware.smartlabapi.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = de.qaware.smartlabapi.client.ComponentScanMarker.class)
public class ApiModuleConfiguration { }
