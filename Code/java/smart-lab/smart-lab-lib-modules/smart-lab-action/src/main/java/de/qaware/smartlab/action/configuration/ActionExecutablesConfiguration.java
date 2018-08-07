package de.qaware.smartlab.action.configuration;

import de.qaware.smartlabactuatoradapter.annotation.EnableSmartLabActuatorAdapters;
import de.qaware.smartlabapi.annotation.EnableSmartLabApi;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlab.action.info.ComponentScanMarker.class,
        de.qaware.smartlab.action.executable.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
@EnableSmartLabActuatorAdapters
public class ActionExecutablesConfiguration { }
