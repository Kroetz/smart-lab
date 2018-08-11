package de.qaware.smartlab.actuator.adapter.adapters.speechtotext;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SpeechToTextConfiguration.Properties.class)
public class SpeechToTextConfiguration {

    public static final String QUALIFIER_SPEECH_TO_TEXT_SERVICE_NAME = "speechToTextServiceName";

    private final Properties properties;

    public SpeechToTextConfiguration(Properties properties) {
        this.properties = properties;
    }

    @Bean
    @Qualifier(QUALIFIER_SPEECH_TO_TEXT_SERVICE_NAME)
    public String speechToTextServiceName() {
        return this.properties.getService();
    }

    @ConfigurationProperties(prefix = Properties.PREFIX)
    public static class Properties {

        public static final String PREFIX = "smart-lab.actuator.speech-to-text";
        public static final String PROPERTY_NAME_SERVICE = "service";

        private String service;

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }
    }
}
