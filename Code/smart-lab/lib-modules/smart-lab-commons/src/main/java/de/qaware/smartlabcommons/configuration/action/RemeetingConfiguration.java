package de.qaware.smartlabcommons.configuration.action;

import de.qaware.smartlabcommons.api.external.client.IRemeetingApiClient;
import de.qaware.smartlabcommons.api.external.service.RemeetingService;
import de.qaware.smartlabcommons.data.action.web.ISpeechToTextService;
import de.qaware.smartlabcommons.miscellaneous.ProfileNames;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(ProfileNames.REMEETING)
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
        return new RemeetingService(this.remeetingApiClient, this.remeetingProperties.getApiKey());
    }

    @Bean
    public String remeetingApiKey() {
        return this.remeetingProperties.getApiKey();
    }

    @ConfigurationProperties(prefix = "remeeting")
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
