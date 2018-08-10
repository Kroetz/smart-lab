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

import static java.time.Duration.ofMinutes;

@Configuration
@ComponentScan(basePackageClasses = de.qaware.smartlab.gui.service.ComponentScanMarker.class)
@EnableSmartLabCore
@EnableSmartLabApi
@EnableConfigurationProperties(GuiConfiguration.Properties.class)
public class GuiConfiguration {

    private final Properties properties;

    public GuiConfiguration(Properties properties) {
        this.properties = properties;
    }

    @Bean
    // TODO: String literal
    @Qualifier("locationStatusEventExtension")
    public Duration locationStatusEventExtension() {
        return this.properties.getCurrentEventExtensionInMinutes();
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "smart-lab.gui")
    public static class Properties {

        private static final int DEFAULT_CURRENT_EVENT_EXTENSION_IN_MINUTES = 1;

        private int currentEventExtensionInMinutes;

        public Properties() {
            this.currentEventExtensionInMinutes = DEFAULT_CURRENT_EVENT_EXTENSION_IN_MINUTES;
        }

        public Duration getCurrentEventExtensionInMinutes() {
            return ofMinutes(this.currentEventExtensionInMinutes);
        }

        public void setCurrentEventExtensionInMinutes(int currentEventExtensionInMinutes) {
            this.currentEventExtensionInMinutes = currentEventExtensionInMinutes;
        }
    }
}
