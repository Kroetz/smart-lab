package de.qaware.smartlabactuatoradapter.actuator.speechtotext;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SpeechToTextConfiguration.Properties.class)
public class SpeechToTextConfiguration {

    private final Properties properties;

    public SpeechToTextConfiguration(Properties properties) {
        this.properties = properties;
    }

    @Bean
    // TODO: String literal
    @Qualifier("speechToTextService")
    public String speechToTextService() {
        return this.properties.getService();
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "smart-lab.action.speech-to-text")
    public static class Properties {

        private String service;

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }
    }
}
