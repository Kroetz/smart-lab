package de.qaware.smartlabperson.configuration;

import de.qaware.smartlabapi.annotation.EnableSmartLabApi;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlabsampledata.ComponentScanMarker.class,
        de.qaware.smartlabperson.controller.ComponentScanMarker.class,
        de.qaware.smartlabperson.business.ComponentScanMarker.class,
        de.qaware.smartlabperson.repository.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
public class PersonServiceConfiguration { }
