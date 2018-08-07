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
import static de.qaware.smartlab.trigger.provider.setupevent.SetUpEventCallbackController.MAPPING_CALLBACK;

@Configuration
@ComponentScan(basePackageClasses = {
        de.qaware.smartlab.trigger.provider.setupevent.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
@EnableConfigurationProperties(SetUpEventTriggerProviderConfiguration.Properties.class)
public class SetUpEventTriggerProviderConfiguration {

    private final SetUpEventTriggerProviderConfiguration.Properties properties;

    public SetUpEventTriggerProviderConfiguration(SetUpEventTriggerProviderConfiguration.Properties properties) {
        this.properties = properties;
    }

    @Bean
    // TODO: String literal
    @Qualifier("setUpTriggerProviderCheckInterval")
    public Duration setUpTriggerProviderCheckInterval() {
        return this.properties.getCheckIntervalInSeconds();
    }

    @Bean
    // TODO: String literal
    @Qualifier("setUpTriggerProviderCallbackUrl")
    public URL setUpTriggerProviderCallbackBaseUrl() throws MalformedURLException {
        return of(this.properties.getCallbackBaseUrl(), MAPPING_CALLBACK);
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "smart-lab.trigger-provider.set-up")
    public static class Properties {

        private static final int DEFAULT_CHECK_INTERVAL_IN_SECONDS = 5;
        private static final String DEFAULT_CALLBACK_BASE_URL = "http://localhost:8080";

        private int checkIntervalInSeconds;
        private String callbackBaseUrl;

        public Properties() {
            this.checkIntervalInSeconds = DEFAULT_CHECK_INTERVAL_IN_SECONDS;
            this.callbackBaseUrl = DEFAULT_CALLBACK_BASE_URL;
        }

        public Duration getCheckIntervalInSeconds() {
            return Duration.ofSeconds(this.checkIntervalInSeconds);
        }

        public void setCheckIntervalInSeconds(int checkIntervalInSeconds) {
            this.checkIntervalInSeconds = checkIntervalInSeconds;
        }

        public URL getCallbackBaseUrl() throws MalformedURLException {
            return new URL(this.callbackBaseUrl);
        }

        public void setCallbackBaseUrl(String callbackBaseUrl) {
            this.callbackBaseUrl = callbackBaseUrl;
        }
    }
}
