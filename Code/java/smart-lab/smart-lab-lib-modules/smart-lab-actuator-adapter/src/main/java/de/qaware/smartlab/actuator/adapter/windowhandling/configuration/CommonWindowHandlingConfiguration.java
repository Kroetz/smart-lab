package de.qaware.smartlab.actuator.adapter.windowhandling.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static java.time.Duration.ofSeconds;

@Configuration
@Slf4j
@EnableConfigurationProperties(CommonWindowHandlingConfiguration.Properties.class)
public class CommonWindowHandlingConfiguration {

    public static final String QUALIFIER_LOCAL_DISPLAYS_BY_SMART_LAB_DISPLAY_IDS = "localDisplaysBySmartLabDisplayIds";
    public static final String QUALIFIER_FIND_WINDOW_TIMEOUT = "findWindowTimeout";

    private final Properties properties;

    public CommonWindowHandlingConfiguration(Properties properties) {
        this.properties = properties;
    }

    @Bean
    @Qualifier(QUALIFIER_LOCAL_DISPLAYS_BY_SMART_LAB_DISPLAY_IDS)
    public Map<String, String> localDisplaysBySmartLabDisplayIds() {
        return this.properties.getDisplays();
    }

    @Bean
    @Qualifier(QUALIFIER_FIND_WINDOW_TIMEOUT)
    public Duration findWindowTimeout() {
        return this.properties.getFindWindowTimeoutInSeconds();
    }

    @ConfigurationProperties(prefix = Properties.PREFIX, ignoreInvalidFields = true)
    public static class Properties {

        private static final String PREFIX = "smart-lab.delegate.window-handling";
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
            return ofSeconds(this.findWindowTimeoutInSeconds);
        }

        public void setFindWindowTimeoutInSeconds(int findWindowTimeoutInSeconds) {
            this.findWindowTimeoutInSeconds = findWindowTimeoutInSeconds;
        }
    }
}
