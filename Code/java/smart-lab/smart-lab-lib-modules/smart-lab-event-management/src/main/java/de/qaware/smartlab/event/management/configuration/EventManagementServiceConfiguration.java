package de.qaware.smartlab.event.management.configuration;

import de.qaware.smartlab.api.annotation.EnableSmartLabApi;
import de.qaware.smartlab.assistance.annotation.EnableSmartLabAssistanceInfos;
import de.qaware.smartlab.core.annotation.EnableSmartLabCore;
import de.qaware.smartlab.data.conversion.annotation.EnableSmartLabDtoConverters;
import de.qaware.smartlab.data.set.annotation.EnableSmartLabInitialData;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

import static java.time.Duration.ofMinutes;

@Configuration
@ComponentScan(basePackageClasses = de.qaware.smartlab.event.management.service.ComponentScanMarker.class)
@EnableSmartLabCore
@EnableSmartLabApi
@EnableSmartLabDtoConverters
@EnableSmartLabInitialData
@EnableSmartLabAssistanceInfos
@EnableConfigurationProperties(EventManagementServiceConfiguration.Properties.class)
public class EventManagementServiceConfiguration {

    public static final String QUALIFIER_MAX_EVENT_DURATION = "maxEventDuration";

    private final Properties properties;

    public EventManagementServiceConfiguration(Properties properties) {
        this.properties = properties;
    }

    @Bean
    @Qualifier(QUALIFIER_MAX_EVENT_DURATION)
    public Duration maxEventDuration() {
        return this.properties.getMaxEventDurationInMinutes();
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "smart-lab.event-management")
    public static class Properties {

        private static final int DEFAULT_MAX_EVENT_DURATION_IN_MINUTES = 480;

        private int maxEventDurationInMinutes;

        public Properties() {
            this.maxEventDurationInMinutes = DEFAULT_MAX_EVENT_DURATION_IN_MINUTES;
        }

        public Duration getMaxEventDurationInMinutes() {
            return ofMinutes(this.maxEventDurationInMinutes);
        }

        public void setMaxEventDurationInMinutes(int maxEventDurationInMinutes) {
            this.maxEventDurationInMinutes = maxEventDurationInMinutes;
        }
    }
}
