package de.qaware.smartlabdelegate.configuration;

import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlabapi.ComponentScanMarker.class,
        de.qaware.smartlabaction.ComponentScanMarker.class,
        de.qaware.smartlabdelegate.ComponentScanMarker.class})
@EnableSmartLabCore
public class DelegateModuleConfiguration {
}
