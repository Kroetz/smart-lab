package de.qaware.smartlabtrigger.configuration;

import de.qaware.smartlab.api.annotation.EnableSmartLabApi;
import de.qaware.smartlab.assistance.annotation.EnableSmartLabAssistanceTriggerables;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@ComponentScan(basePackageClasses = {de.qaware.smartlabtrigger.service.ComponentScanMarker.class})
@EnableSmartLabCore
@EnableSmartLabApi
@EnableSmartLabAssistanceTriggerables
@EnableAsync
public class TriggerServiceConfiguration {

    @Bean
    public UrlValidator urlValidator() {
        return new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);
    }
}
