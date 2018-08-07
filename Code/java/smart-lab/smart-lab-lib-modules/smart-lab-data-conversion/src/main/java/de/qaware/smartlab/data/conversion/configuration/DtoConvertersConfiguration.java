package de.qaware.smartlab.data.conversion.configuration;

import de.qaware.smartlab.actuator.adapter.annotation.EnableSmartLabProjectBaseInfos;
import de.qaware.smartlab.assistance.annotation.EnableSmartLabAssistanceInfos;
import de.qaware.smartlab.core.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = de.qaware.smartlab.data.conversion.converter.ComponentScanMarker.class)
@EnableSmartLabCore
@EnableSmartLabAssistanceInfos
@EnableSmartLabProjectBaseInfos
public class DtoConvertersConfiguration { }
