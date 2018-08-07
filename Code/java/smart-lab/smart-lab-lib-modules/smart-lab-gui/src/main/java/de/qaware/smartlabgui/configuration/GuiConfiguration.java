package de.qaware.smartlabgui.configuration;

import de.qaware.smartlab.api.annotation.EnableSmartLabApi;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@ComponentScan(basePackageClasses = {de.qaware.smartlabgui.service.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
@EnableConfigurationProperties(GuiConfiguration.GuiProperties.class)
public class GuiConfiguration {

    private final GuiProperties guiProperties;

    public GuiConfiguration(GuiProperties guiProperties) {
        this.guiProperties = guiProperties;
    }

    @Bean
    // TODO: String literal
    @Qualifier("locationStatusMeetingExtension")
    public Duration locationStatusMeetingExtension() {
        return this.guiProperties.getCurrentMeetingExtensionInMinutes();
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "smart-lab.gui")
    public static class GuiProperties {

        private static final int DEFAULT_CURRENT_MEETING_EXTENSION_IN_MINUTES = 1;

        private int currentMeetingExtensionInMinutes;

        public GuiProperties() {
            this.currentMeetingExtensionInMinutes = DEFAULT_CURRENT_MEETING_EXTENSION_IN_MINUTES;
        }

        public Duration getCurrentMeetingExtensionInMinutes() {
            return Duration.ofMinutes(this.currentMeetingExtensionInMinutes);
        }

        public void setCurrentMeetingExtensionInMinutes(int currentMeetingExtensionInMinutes) {
            this.currentMeetingExtensionInMinutes = currentMeetingExtensionInMinutes;
        }
    }
}
