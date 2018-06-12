package de.qaware.smartlabdelegate.configuration;

import de.qaware.smartlabapi.annotation.EnableSmartLabApi;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlabaction.ComponentScanMarker.class,
        de.qaware.smartlabdelegate.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
public class DelegateModuleConfiguration {
}
