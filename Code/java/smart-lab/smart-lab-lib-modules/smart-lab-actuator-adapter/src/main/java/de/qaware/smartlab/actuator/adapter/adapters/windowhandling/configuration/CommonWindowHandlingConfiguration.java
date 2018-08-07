package de.qaware.smartlab.actuator.adapter.adapters.windowhandling.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
@EnableConfigurationProperties(CommonWindowHandlingConfiguration.Properties.class)
public class CommonWindowHandlingConfiguration {

    private final Properties properties;

    public CommonWindowHandlingConfiguration(Properties properties) {
        this.properties = properties;
    }

    @Bean
    // TODO: String literal
    @Qualifier("localDisplaysBySmartLabDisplayIds")
    public Map<String, String> localDisplaysBySmartLabDisplayIds() {
        return this.properties.getDisplays();
    }

    @Bean
    // TODO: String literals
    @Qualifier("findWindowTimeout")
    public Duration findWindowTimeout() {
        return this.properties.getFindWindowTimeoutInSeconds();
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "smart-lab.delegate.window-handling", ignoreInvalidFields = true)
    public static class Properties {

        private static final int DEFAULT_FIND_WINDOW_TIMEOUT_IN_SECONDS = 5;

        private Map<String, String> displays;
        private int findWindowTimeoutInSeconds;

        public Properties() {
            this.displays = new HashMap<>();
            this.findWindowTimeoutInSeconds = DEFAULT_FIND_WINDOW_TIMEOUT_IN_SECONDS;
        }

        public Map<String, String> getDisplays() {
            return this.displays;
        }

        public void setDisplays(Map<String, String> displays) {
            this.displays = displays;
        }

        public Duration getFindWindowTimeoutInSeconds() {
            return Duration.ofSeconds(this.findWindowTimeoutInSeconds);
        }

        public void setFindWindowTimeoutInSeconds(int findWindowTimeoutInSeconds) {
            this.findWindowTimeoutInSeconds = findWindowTimeoutInSeconds;
        }
    }
}
