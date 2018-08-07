package de.qaware.smartlab.assistance.configuration;

import de.qaware.smartlab.api.annotation.EnableSmartLabApi;
import de.qaware.smartlab.core.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlab.assistance.assistances.triggerable.ComponentScanMarker.class,
        de.qaware.smartlab.assistance.assistances.info.ComponentScanMarker.class,
        de.qaware.smartlab.assistance.context.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
public class AssistanceTriggerablesConfiguration { }
