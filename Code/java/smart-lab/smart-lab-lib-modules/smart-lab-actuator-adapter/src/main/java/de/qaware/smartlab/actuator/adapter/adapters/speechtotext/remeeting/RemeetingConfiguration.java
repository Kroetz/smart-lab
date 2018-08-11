package de.qaware.smartlab.actuator.adapter.adapters.speechtotext.remeeting;

import de.qaware.smartlab.core.data.action.speechtotext.ISpeechToTextAdapter;
import de.qaware.smartlab.core.constant.Property;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        prefix = Property.Prefix.SPEECH_TO_TEXT_SERVICE,
        name = Property.Name.SPEECH_TO_TEXT_SERVICE,
        havingValue = Property.Value.SpeechToTextService.REMEETING)
@EnableConfigurationProperties(RemeetingConfiguration.Properties.class)
@EnableFeignClients(basePackageClasses = IRemeetingApiClient.class)
public class RemeetingConfiguration {

    private final Properties properties;
    private final IRemeetingApiClient remeetingApiClient;

    public RemeetingConfiguration(
            Properties properties,
            IRemeetingApiClient remeetingApiClient) {
        this.properties = properties;
        this.remeetingApiClient = remeetingApiClient;
    }

    @Bean
    public ISpeechToTextAdapter speechToTextAdapter() {
        return new RemeetingAdapter(this.remeetingApiClient, this.properties.getApiKey());
    }

    @ConfigurationProperties(prefix = Properties.PREFIX)
    public static class Properties {

        private static final String PREFIX = "smart-lab.actuator.remeeting";

        private String apiKey;

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }
    }
}
