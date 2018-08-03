package de.qaware.smartlabdevice.configuration;

import de.qaware.smartlabapi.annotation.EnableSmartLabApi;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import de.qaware.smartlabdata.annotation.EnableSmartLabInitialData;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {de.qaware.smartlabdevice.service.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
@EnableSmartLabInitialData
public class DeviceServiceConfiguration { }
