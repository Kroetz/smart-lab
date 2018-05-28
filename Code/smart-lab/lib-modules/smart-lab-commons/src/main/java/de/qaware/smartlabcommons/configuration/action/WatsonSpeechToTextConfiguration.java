package de.qaware.smartlabcommons.configuration.action;

import de.qaware.smartlabcommons.api.external.watson.speechtotext.WatsonSpeechToTextService;
import de.qaware.smartlabcommons.data.action.web.ISpeechToTextService;
import de.qaware.smartlabcommons.miscellaneous.ProfileNames;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(ProfileNames.WATSON_SPEECH_TO_TEXT)
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
