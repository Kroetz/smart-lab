package de.qaware.smartlab.trigger.configuration;

import de.qaware.smartlab.api.annotation.EnableSmartLabApi;
import de.qaware.smartlab.core.annotation.EnableSmartLabCore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static de.qaware.smartlab.core.miscellaneous.UrlUtils.of;
import static de.qaware.smartlab.trigger.provider.cleanupevent.CleanUpEventCallbackController.MAPPING_CALLBACK;
import static java.time.Duration.ofSeconds;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlab.trigger.provider.cleanupevent.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
@EnableConfigurationProperties(CleanUpEventTriggerProviderConfiguration.Properties.class)
public class CleanUpEventTriggerProviderConfiguration {

    private final CleanUpEventTriggerProviderConfiguration.Properties properties;

    public CleanUpEventTriggerProviderConfiguration(CleanUpEventTriggerProviderConfiguration.Properties properties) {
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

    @Bean
    // TODO: String literal
    @Qualifier("cleanUpTriggerProviderCallbackUrl")
    public URL cleanUpTriggerProviderCallbackBaseUrl() throws MalformedURLException {
        return of(this.properties.getCallbackBaseUrl(), MAPPING_CALLBACK);
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "smart-lab.trigger-provider.clean-up")
    public static class Properties {

        private static final int DEFAULT_CHECK_INTERVAL_IN_SECONDS = 5;
        private static final int DEFAULT_TRIGGER_THRESHOLD_IN_SECONDS = 10;
        private static final String DEFAULT_CALLBACK_BASE_URL = "http://localhost:8080";

        private int checkIntervalInSeconds;
        private int triggerThresholdInSeconds;
        private String callbackBaseUrl;

        public Properties() {
            this.checkIntervalInSeconds = DEFAULT_CHECK_INTERVAL_IN_SECONDS;
            this.triggerThresholdInSeconds = DEFAULT_TRIGGER_THRESHOLD_IN_SECONDS;
            this.callbackBaseUrl = DEFAULT_CALLBACK_BASE_URL;
        }

        public Duration getCheckIntervalInSeconds() {
            return ofSeconds(this.checkIntervalInSeconds);
        }

        public void setCheckIntervalInSeconds(int checkIntervalInSeconds) {
            this.checkIntervalInSeconds = checkIntervalInSeconds;
        }

        public Duration getTriggerThresholdInSeconds() {
            return ofSeconds(this.triggerThresholdInSeconds);
        }

        public void setTriggerThresholdInSeconds(int triggerThresholdInSeconds) {
            this.triggerThresholdInSeconds = triggerThresholdInSeconds;
        }

        public URL getCallbackBaseUrl() throws MalformedURLException {
            return new URL(this.callbackBaseUrl);
        }

        public void setCallbackBaseUrl(String callbackBaseUrl) {
            this.callbackBaseUrl = callbackBaseUrl;
        }
    }
}
