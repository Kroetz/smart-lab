package de.qaware.smartlab.action.configuration;

import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlab.action.actions.info.ComponentScanMarker.class,
        de.qaware.smartlab.action.actions.submittable.ComponentScanMarker.class})
@EnableSmartLabCore
public class ActionSubmittablesConfiguration { }
