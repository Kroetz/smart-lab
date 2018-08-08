package de.qaware.smartlab.action.configuration;

import de.qaware.smartlab.action.annotation.EnableSmartLabActionInfos;
import de.qaware.smartlab.core.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = de.qaware.smartlab.action.actions.submittable.ComponentScanMarker.class)
@EnableSmartLabCore
@EnableSmartLabActionInfos
public class ActionSubmittablesConfiguration { }
