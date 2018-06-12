package de.qaware.smartlabdevice.configuration;

import de.qaware.smartlabapi.annotation.EnableSmartLabApi;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlabsampledata.ComponentScanMarker.class,
        de.qaware.smartlabdevice.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
public class DeviceModuleConfiguration { }
