package de.qaware.smartlabroom.configuration;

import de.qaware.smartlabapi.annotation.EnableSmartLabApi;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlabsampledata.ComponentScanMarker.class,
        de.qaware.smartlabroom.controller.ComponentScanMarker.class,
        de.qaware.smartlabroom.business.ComponentScanMarker.class,
        de.qaware.smartlabroom.repository.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
public class RoomServiceConfiguration { }
