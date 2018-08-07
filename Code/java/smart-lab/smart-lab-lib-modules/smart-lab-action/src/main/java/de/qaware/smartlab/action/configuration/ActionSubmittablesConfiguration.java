package de.qaware.smartlab.action.configuration;

import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlab.action.info.ComponentScanMarker.class,
        de.qaware.smartlab.action.submittable.ComponentScanMarker.class})
@EnableSmartLabCore
public class ActionSubmittablesConfiguration { }
