package de.qaware.smartlab.actuator.adapter.adapters.projectbase.service.github;

import de.qaware.smartlab.core.miscellaneous.Property;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        prefix = Property.Prefix.GITHUB,
        name = Property.Name.GITHUB,
        havingValue = Property.Value.TRUE)
@EnableConfigurationProperties(GithubConfiguration.Properties.class)
public class GithubConfiguration {

    private final Properties properties;

    public GithubConfiguration(Properties properties) {
        this.properties = properties;
    }

    @Bean
    // TODO: String literals
    @Qualifier("githubApiKey")
    public String githubApiKey() {
        return this.properties.getApiKey();
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "smart-lab.actuator.github")
    public static class Properties {

        private String apiKey;

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }
    }
}
