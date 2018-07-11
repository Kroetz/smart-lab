package de.qaware.smartlabtrigger.configuration;

import de.qaware.smartlabapi.annotation.EnableSmartLabApi;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlabtrigger.provider.cleanupmeeting.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
@EnableConfigurationProperties(CleanUpMeetingTriggerProviderConfiguration.Properties.class)
public class CleanUpMeetingTriggerProviderConfiguration {

    private final CleanUpMeetingTriggerProviderConfiguration.Properties properties;

    public CleanUpMeetingTriggerProviderConfiguration(CleanUpMeetingTriggerProviderConfiguration.Properties properties) {
        this.properties = properties;
    }

    @Bean
    // TODO: String literal
    @Qualifier("cleanUpTriggerProviderCheckInterval")
    public Duration cleanUpTriggerProviderCheckInterval() {
        return this.properties.getCheckIntervalInSeconds();
    }

    @Bean
    // TODO: String literal
    @Qualifier("cleanUpTriggerProviderTriggerThreshold")
    public Duration cleanUpTriggerProviderTriggerThreshold() {
        return this.properties.getTriggerThresholdInSeconds();
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "trigger.provider.clean-up")
    public static class Properties {

        private static final int DEFAULT_CHECK_INTERVAL_IN_SECONDS = 5;
        private static final int DEFAULT_TRIGGER_THRESHOLD_IN_SECONDS = 10;

        private int checkIntervalInSeconds;
        private int triggerThresholdInSeconds;

        public Properties() {
            this.checkIntervalInSeconds = DEFAULT_CHECK_INTERVAL_IN_SECONDS;
            this.triggerThresholdInSeconds = DEFAULT_TRIGGER_THRESHOLD_IN_SECONDS;
        }

        public Duration getCheckIntervalInSeconds() {
            return Duration.ofSeconds(this.checkIntervalInSeconds);
        }

        public void setCheckIntervalInSeconds(int checkIntervalInSeconds) {
            this.checkIntervalInSeconds = checkIntervalInSeconds;
        }

        public Duration getTriggerThresholdInSeconds() {
            return Duration.ofSeconds(this.triggerThresholdInSeconds);
        }

        public void setTriggerThresholdInSeconds(int triggerThresholdInSeconds) {
            this.triggerThresholdInSeconds = triggerThresholdInSeconds;
        }
    }
}
