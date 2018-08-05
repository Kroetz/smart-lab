package de.qaware.smartlabactuatoradapter.actuator.speechtotext.remeeting;

import de.qaware.smartlabactuatoradapter.actuator.externalapi.remeeting.IRemeetingApiClient;
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
        havingValue = Property.Value.SpeechToTextService.REMEETING)
@EnableConfigurationProperties(RemeetingConfiguration.RemeetingProperties.class)
public class RemeetingConfiguration {

    private final RemeetingProperties remeetingProperties;
    private final IRemeetingApiClient remeetingApiClient;

    public RemeetingConfiguration(
            RemeetingProperties remeetingProperties,
            IRemeetingApiClient remeetingApiClient) {
        this.remeetingProperties = remeetingProperties;
        this.remeetingApiClient = remeetingApiClient;
    }

    @Bean
    public ISpeechToTextService speechToTextService() {
        return new RemeetingServiceConnector(this.remeetingApiClient, this.remeetingProperties.getApiKey());
    }

    @Bean
    public String remeetingApiKey() {
        return this.remeetingProperties.getApiKey();
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "smart-lab.actor.remeeting")
    public static class RemeetingProperties {

        private String apiKey;

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }
    }
}
