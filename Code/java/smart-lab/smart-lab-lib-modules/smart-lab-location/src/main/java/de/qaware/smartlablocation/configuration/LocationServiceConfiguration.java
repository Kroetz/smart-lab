package de.qaware.smartlablocation.configuration;

import de.qaware.smartlabapi.annotation.EnableSmartLabApi;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import de.qaware.smartlabdataconversion.annotation.EnableSmartLabDtoConverters;
import de.qaware.smartlabdataset.annotation.EnableSmartLabInitialData;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {de.qaware.smartlablocation.service.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
@EnableSmartLabDtoConverters
@EnableSmartLabInitialData
public class LocationServiceConfiguration { }
