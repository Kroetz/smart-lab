package de.qaware.smartlabaction.action.executable.dataupload.github;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GithubConfiguration.GithubProperties.class)
public class GithubConfiguration {

    private final GithubProperties githubProperties;

    public GithubConfiguration(GithubProperties githubProperties) {
        this.githubProperties = githubProperties;
    }

    @Bean
    public String githubApiKey() {
        return this.githubProperties.getApiKey();
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "github")
    public static class GithubProperties {

        private String apiKey;

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }
    }
}
