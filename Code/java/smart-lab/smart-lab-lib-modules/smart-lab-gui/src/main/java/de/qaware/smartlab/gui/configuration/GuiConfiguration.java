package de.qaware.smartlab.gui.configuration;

import de.qaware.smartlab.api.annotation.EnableSmartLabApi;
import de.qaware.smartlab.core.annotation.EnableSmartLabCore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@ComponentScan(basePackageClasses = {de.qaware.smartlab.gui.service.ComponentScanMarker.class})
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
    @Qualifier("locationStatusEventExtension")
    public Duration locationStatusEventExtension() {
        return this.guiProperties.getCurrentEventExtensionInMinutes();
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "smart-lab.gui")
    public static class GuiProperties {

        private static final int DEFAULT_CURRENT_EVENT_EXTENSION_IN_MINUTES = 1;

        private int currentEventExtensionInMinutes;

        public GuiProperties() {
            this.currentEventExtensionInMinutes = DEFAULT_CURRENT_EVENT_EXTENSION_IN_MINUTES;
        }

        public Duration getCurrentEventExtensionInMinutes() {
            return Duration.ofMinutes(this.currentEventExtensionInMinutes);
        }

        public void setCurrentEventExtensionInMinutes(int currentEventExtensionInMinutes) {
            this.currentEventExtensionInMinutes = currentEventExtensionInMinutes;
        }
    }
}
