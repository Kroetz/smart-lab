package de.qaware.smartlab.actuator.adapter.adapters.speechtotext.watson;

import de.qaware.smartlab.actuator.adapter.adapters.speechtotext.SpeechToTextConfiguration;
import de.qaware.smartlab.core.data.action.speechtotext.ISpeechToTextAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        prefix = SpeechToTextConfiguration.Properties.PREFIX,
        name = SpeechToTextConfiguration.Properties.PROPERTY_NAME_SERVICE,
        havingValue = WatsonSpeechToTextAdapter.ACTUATOR_TYPE)
@EnableConfigurationProperties(WatsonSpeechToTextConfiguration.Properties.class)
public class WatsonSpeechToTextConfiguration {

    private final Properties properties;
    private final WatsonSpeechToTextTranscript.Factory transcriptFactory;

    public WatsonSpeechToTextConfiguration(
            Properties properties,
            WatsonSpeechToTextTranscript.Factory transcriptFactory) {
        this.properties = properties;
        this.transcriptFactory = transcriptFactory;
    }

    @Bean
    public ISpeechToTextAdapter speechToTextAdapter() {
        return new WatsonSpeechToTextAdapter(
                this.properties.getUserName(),
                this.properties.getPassword(),
                this.transcriptFactory);
    }

    @ConfigurationProperties(prefix = Properties.PREFIX)
    public static class Properties {

        private static final String PREFIX = "smart-lab.actuator.watson-speech-to-text";

        private String userName;
        private String password;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
