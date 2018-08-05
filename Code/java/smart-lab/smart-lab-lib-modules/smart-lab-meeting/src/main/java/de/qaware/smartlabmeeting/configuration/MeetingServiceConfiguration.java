package de.qaware.smartlabmeeting.configuration;

import de.qaware.smartlabapi.annotation.EnableSmartLabApi;
import de.qaware.smartlabassistance.annotation.EnableSmartLabAssistanceInfos;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import de.qaware.smartlabdataconversion.annotation.EnableSmartLabDtoConverters;
import de.qaware.smartlabdataset.annotation.EnableSmartLabInitialData;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@ComponentScan(basePackageClasses = {de.qaware.smartlabmeeting.service.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
@EnableSmartLabDtoConverters
@EnableSmartLabInitialData
@EnableSmartLabAssistanceInfos
@EnableConfigurationProperties(MeetingServiceConfiguration.Properties.class)
public class MeetingServiceConfiguration {

    private final Properties properties;

    public MeetingServiceConfiguration(Properties properties) {
        this.properties = properties;
    }

    @Bean
    // TODO: String literal
    @Qualifier("maxMeetingDuration")
    public Duration maxMeetingDuration() {
        return this.properties.getMaxMeetingDurationInMinutes();
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "smart-lab.meeting-management")
    public static class Properties {

        private static final int DEFAULT_MAX_MEETING_DURATION_IN_MINUTES = 480;

        private int maxMeetingDurationInMinutes;

        public Properties() {
            this.maxMeetingDurationInMinutes = DEFAULT_MAX_MEETING_DURATION_IN_MINUTES;
        }

        public Duration getMaxMeetingDurationInMinutes() {
            return Duration.ofMinutes(this.maxMeetingDurationInMinutes);
        }

        public void setMaxMeetingDurationInMinutes(int maxMeetingDurationInMinutes) {
            this.maxMeetingDurationInMinutes = maxMeetingDurationInMinutes;
        }
    }
}
