package de.qaware.smartlab.actuator.adapter.adapters.speechtotext.remeeting;

import de.qaware.smartlab.actuator.adapter.adapters.speechtotext.SpeechToTextConfiguration;
import de.qaware.smartlab.core.action.speechtotext.ISpeechToTextAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        prefix = SpeechToTextConfiguration.Properties.PREFIX,
        name = SpeechToTextConfiguration.Properties.PROPERTY_NAME_SERVICE,
        havingValue = RemeetingAdapter.ACTUATOR_TYPE)
@EnableConfigurationProperties(RemeetingConfiguration.Properties.class)
@EnableFeignClients(basePackageClasses = IRemeetingApiClient.class)
public class RemeetingConfiguration {

    private final Properties properties;
    private final IRemeetingApiClient remeetingApiClient;
    private final RemeetingTranscript.Factory transcriptFactory;

    public RemeetingConfiguration(
            Properties properties,
            IRemeetingApiClient remeetingApiClient,
            RemeetingTranscript.Factory transcriptFactory) {
        this.properties = properties;
        this.remeetingApiClient = remeetingApiClient;
        this.transcriptFactory = transcriptFactory;
    }

    @Bean
    public ISpeechToTextAdapter speechToTextAdapter() {
        return new RemeetingAdapter(this.remeetingApiClient, this.properties.getApiKey(), this.transcriptFactory);
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
