package de.qaware.smartlab.action.configuration;

import de.qaware.smartlab.action.annotation.EnableSmartLabActionInfos;
import de.qaware.smartlab.actuator.adapter.annotation.EnableSmartLabActuatorAdapters;
import de.qaware.smartlab.api.annotation.EnableSmartLabApi;
import de.qaware.smartlab.core.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = de.qaware.smartlab.action.actions.executable.ComponentScanMarker.class)
@EnableSmartLabCore
@EnableSmartLabApi
@EnableSmartLabActionInfos
@EnableSmartLabActuatorAdapters
public class ActionExecutablesConfiguration { }
