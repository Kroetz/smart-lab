package de.qaware.smartlabcore.configuration;

import de.qaware.smartlabcore.exception.ConfigurationException;
import de.qaware.smartlabcore.miscellaneous.Property;
import feign.Client;
import feign.okhttp.OkHttpClient;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
@EnableConfigurationProperties(MonolithConfiguration.MonolithProperties.class)
class MonolithConfiguration {

    private MonolithProperties monolithProperties;

    public MonolithConfiguration(MonolithProperties monolithProperties) {
        this.monolithProperties = monolithProperties;
    }

    @Bean
    // TODO: String literal
    @Qualifier("urlsByDelegateName")
    public Map<String, String> urlsByDelegateName() {
        return this.monolithProperties.getUrls();
    }

    @Bean
    public Client client() {
        /*
        This client is needed when manually creating Feign clients (via Feign.builder()) with specific base URLs.
        The default client from spring boot (ribbon) requires you to pass a ribbon client name as the host part of
        the URL which it then tries to resolve to an hostname. This is only desirable when using a discovery service
        and will fail if such a service does not exist. Feign clients that are created via Spring Boot's annotation
        @FeignClient will work correctly though when given a specific base URL.

        For details see https://github.com/OpenFeign/feign#okhttp
         */
        return new OkHttpClient();
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "delegate")
    public static class MonolithProperties {

        private Map<String, String> urls = new HashMap<>();

        public Map<String, String> getUrls() {
            return this.urls;
        }

        public void setUrls(Map<String, String> urls) {
            final UrlValidator urlValidator = new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);
            if(urls.values().stream().anyMatch(url -> !urlValidator.isValid(url))) {
                throw new ConfigurationException("The configured delegate URLs must be valid");
            }
            this.urls = urls;
        }
    }
}