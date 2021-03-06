package de.qaware.smartlab.monolith.configuration;

import de.qaware.smartlab.action.annotation.EnableSmartLabActionService;
import de.qaware.smartlab.actuator.management.annotation.EnableSmartLabActuatorManagementService;
import de.qaware.smartlab.assistance.annotation.EnableSmartLabAssistanceService;
import de.qaware.smartlab.core.configuration.ModularityConfiguration;
import de.qaware.smartlab.core.exception.configuration.ConfigurationException;
import de.qaware.smartlab.data.conversion.annotation.EnableSmartLabDtoConverters;
import de.qaware.smartlab.event.management.annotation.EnableSmartLabEventManagementService;
import de.qaware.smartlab.gui.annotation.EnableSmartLabGui;
import de.qaware.smartlab.job.annotation.EnableSmartLabJobService;
import de.qaware.smartlab.location.management.annotation.EnableSmartLabLocationManagementService;
import de.qaware.smartlab.person.management.annotation.EnableSmartLabPersonManagementService;
import de.qaware.smartlab.trigger.annotation.EnableSmartLabCleanUpEventTriggerProvider;
import de.qaware.smartlab.trigger.annotation.EnableSmartLabSetUpEventTriggerProvider;
import de.qaware.smartlab.trigger.annotation.EnableSmartLabTriggerService;
import de.qaware.smartlab.workgroup.management.annotation.EnableSmartLabWorkgroupManagementService;
import feign.Client;
import feign.okhttp.OkHttpClient;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(
        prefix = ModularityConfiguration.Properties.PREFIX,
        name = ModularityConfiguration.Properties.MODULARITY,
        havingValue = ModularityConfiguration.Properties.MONOLITH)
@ComponentScan(basePackageClasses = de.qaware.smartlab.monolith.ComponentScanMarker.class)
@EnableSmartLabDtoConverters
@EnableSmartLabJobService
@EnableSmartLabPersonManagementService
@EnableSmartLabWorkgroupManagementService
@EnableSmartLabActuatorManagementService
@EnableSmartLabLocationManagementService
@EnableSmartLabEventManagementService
@EnableSmartLabTriggerService
@EnableSmartLabAssistanceService
@EnableSmartLabActionService
@EnableSmartLabGui
@EnableSmartLabSetUpEventTriggerProvider
@EnableSmartLabCleanUpEventTriggerProvider
@EnableConfigurationProperties(value = MonolithModuleConfiguration.Properties.class)
public class MonolithModuleConfiguration {

    public static final String QUALIFIER_URLS_BY_DELEGATE_NAME = "urlsByDelegateName";
    public static final String QUALIFIER_FALLBACK_BASE_URL = "fallbackBaseUrl";

    private final Properties properties;

    public MonolithModuleConfiguration(Properties properties) {
        this.properties = properties;
    }

    @Bean
    @Qualifier(QUALIFIER_URLS_BY_DELEGATE_NAME)
    public Map<String, String> urlsByDelegateName() {
        return this.properties.getDelegateUrls();
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

    /*
     * This URL is only used if the regular method fails to deduce the base URL of services from the request URL that
     * is being processed (e.g. when there is technically no REST call because a request came from within the system).
     */
    @Bean
    @Qualifier(QUALIFIER_FALLBACK_BASE_URL)
    public URL fallbackBaseUrl() throws MalformedURLException {
        return this.properties.getFallbackBaseUrl();
    }

    @ConfigurationProperties(prefix = Properties.PREFIX)
    public static class Properties {

        private static final String PREFIX = "smart-lab.monolith";
        private final String DEFAULT_FALLBACK_BASE_URL = "http://localhost:8080";

        private Map<String, String> delegateUrls;
        private String fallbackBaseUrl;

        public Properties() {
            this.delegateUrls = new HashMap<>();
            this.fallbackBaseUrl = DEFAULT_FALLBACK_BASE_URL;
        }

        public Map<String, String> getDelegateUrls() {
            return this.delegateUrls;
        }

        public void setDelegateUrls(Map<String, String> delegateUrls) {
            final UrlValidator urlValidator = new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);
            if(delegateUrls.values().stream().anyMatch(url -> !urlValidator.isValid(url))) {
                throw new ConfigurationException("The configured delegate URLs must be valid");
            }
            this.delegateUrls = delegateUrls;
        }

        public URL getFallbackBaseUrl() throws MalformedURLException {
            return new URL(this.fallbackBaseUrl);
        }

        public void setFallbackBaseUrl(String fallbackBaseUrl) {
            this.fallbackBaseUrl = fallbackBaseUrl;
        }
    }
}
