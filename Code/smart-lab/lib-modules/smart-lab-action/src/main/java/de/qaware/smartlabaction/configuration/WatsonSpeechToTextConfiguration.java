package de.qaware.smartlabaction.configuration;

import de.qaware.smartlabaction.action.external.watson.speechtotext.WatsonSpeechToTextService;
import de.qaware.smartlabcore.data.action.speechtotext.ISpeechToTextService;
import de.qaware.smartlabcore.miscellaneous.Property;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        prefix = Property.Prefix.SPEECH_TO_TEXT_SERVICE,
        name = Property.Name.SPEECH_TO_TEXT_SERVICE,
        havingValue = Property.Value.SpeechToTextService.WATSON)
@EnableConfigurationProperties(WatsonSpeechToTextConfiguration.WatsonSpeechToTextProperties.class)
public class WatsonSpeechToTextConfiguration {

    private final WatsonSpeechToTextProperties watsonSpeechToTextProperties;

    public WatsonSpeechToTextConfiguration(WatsonSpeechToTextProperties watsonSpeechToTextProperties) {
        this.watsonSpeechToTextProperties = watsonSpeechToTextProperties;
    }

    @Bean
    public ISpeechToTextService speechToTextService() {
        return new WatsonSpeechToTextService(
                this.watsonSpeechToTextProperties.getUserName(),
                this.watsonSpeechToTextProperties.getPassword());
    }

    @Bean
    public String watsonSpeechToTextUserName() {
        return this.watsonSpeechToTextProperties.getUserName();
    }

    @Bean
    public String watsonSpeechToTextPassword() {
        return this.watsonSpeechToTextProperties.getPassword();
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "watson.speech-to-text")
    public static class WatsonSpeechToTextProperties {

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
