package de.qaware.smartlabgui.configuration;

import de.qaware.smartlabapi.annotation.EnableSmartLabApi;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlabgui.controller.ComponentScanMarker.class,
        de.qaware.smartlabgui.business.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
public class GuiConfiguration { }
