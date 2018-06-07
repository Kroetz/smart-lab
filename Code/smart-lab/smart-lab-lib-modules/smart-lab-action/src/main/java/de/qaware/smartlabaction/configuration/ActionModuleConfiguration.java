package de.qaware.smartlabaction.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackageClasses = de.qaware.smartlabaction.action.external.ComponentScanMarker.class)
public class ActionModuleConfiguration { }
