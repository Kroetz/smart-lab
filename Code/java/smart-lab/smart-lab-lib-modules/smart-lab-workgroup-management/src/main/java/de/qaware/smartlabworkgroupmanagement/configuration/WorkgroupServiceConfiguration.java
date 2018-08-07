package de.qaware.smartlabworkgroupmanagement.configuration;

import de.qaware.smartlab.api.annotation.EnableSmartLabApi;
import de.qaware.smartlab.core.annotation.EnableSmartLabCore;
import de.qaware.smartlab.data.conversion.annotation.EnableSmartLabDtoConverters;
import de.qaware.smartlab.data.set.annotation.EnableSmartLabInitialData;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {de.qaware.smartlabworkgroupmanagement.service.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
@EnableSmartLabDtoConverters
@EnableSmartLabInitialData
public class WorkgroupServiceConfiguration { }
