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
        de.qaware.smartlabtrigger.provider.setupmeeting.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
@EnableConfigurationProperties(SetUpMeetingTriggerProviderConfiguration.Properties.class)
public class SetUpMeetingTriggerProviderConfiguration {

    private final SetUpMeetingTriggerProviderConfiguration.Properties properties;

    public SetUpMeetingTriggerProviderConfiguration(SetUpMeetingTriggerProviderConfiguration.Properties properties) {
        this.properties = properties;
    }

    @Bean
    // TODO: String literal
    @Qualifier("setUpTriggerProviderCheckInterval")
    public Duration setUpTriggerProviderCheckInterval() {
        return this.properties.getCheckIntervalInSeconds();
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "trigger.provider.set-up")
    public static class Properties {

        private static final int DEFAULT_CHECK_INTERVAL_IN_SECONDS = 5;

        private int checkIntervalInSeconds;

        public Properties() {
            this.checkIntervalInSeconds = DEFAULT_CHECK_INTERVAL_IN_SECONDS;
        }

        public Duration getCheckIntervalInSeconds() {
            return Duration.ofSeconds(this.checkIntervalInSeconds);
        }

        public void setCheckIntervalInSeconds(int checkIntervalInSeconds) {
            this.checkIntervalInSeconds = checkIntervalInSeconds;
        }
    }
}
