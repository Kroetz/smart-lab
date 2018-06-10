package de.qaware.smartlabdelegate.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlabcore.ComponentScanMarker.class,
        de.qaware.smartlabapi.ComponentScanMarker.class,
        de.qaware.smartlabaction.ComponentScanMarker.class,
        de.qaware.smartlabdelegate.ComponentScanMarker.class})
public class DelegateModuleConfiguration {
}
