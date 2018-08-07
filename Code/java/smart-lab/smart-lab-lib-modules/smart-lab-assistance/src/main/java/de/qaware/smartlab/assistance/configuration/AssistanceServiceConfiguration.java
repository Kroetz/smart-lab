package de.qaware.smartlab.assistance.configuration;

import de.qaware.smartlab.action.annotation.EnableSmartLabActionSubmittables;
import de.qaware.smartlab.api.annotation.EnableSmartLabApi;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlab.assistance.service.ComponentScanMarker.class,
        de.qaware.smartlab.assistance.assistances.info.ComponentScanMarker.class,
        de.qaware.smartlab.assistance.assistances.controllable.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
@EnableSmartLabActionSubmittables
public class AssistanceServiceConfiguration { }
