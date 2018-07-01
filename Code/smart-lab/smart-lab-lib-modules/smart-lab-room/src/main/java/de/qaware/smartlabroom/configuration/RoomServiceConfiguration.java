package de.qaware.smartlabroom.configuration;

import de.qaware.smartlabapi.annotation.EnableSmartLabApi;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import de.qaware.smartlabsampledata.annotation.EnableSmartLabSampleData;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlabroom.controller.ComponentScanMarker.class,
        de.qaware.smartlabroom.business.ComponentScanMarker.class,
        de.qaware.smartlabroom.repository.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
@EnableSmartLabSampleData
@EnableConfigurationProperties(RoomServiceConfiguration.RoomServiceProperties.class)
public class RoomServiceConfiguration {

    private final RoomServiceProperties roomServiceProperties;

    public RoomServiceConfiguration(RoomServiceProperties roomServiceProperties) {
        this.roomServiceProperties = roomServiceProperties;
    }

    @Bean
    // TODO: String literal
    @Qualifier("roomStatusMeetingExtension")
    public Duration roomStatusMeetingExtension() {
        return this.roomServiceProperties.getCurrentMeetingExtensionInMinutes();
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "room")
    public static class RoomServiceProperties {

        private static final int DEFAULT_CURRENT_MEETING_EXTENSION_IN_MINUTES = 1;

        private int currentMeetingExtensionInMinutes;

        public RoomServiceProperties() {
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
