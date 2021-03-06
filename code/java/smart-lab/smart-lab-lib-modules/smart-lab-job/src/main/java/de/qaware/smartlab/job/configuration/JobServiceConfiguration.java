package de.qaware.smartlab.job.configuration;

import de.qaware.smartlab.api.annotation.EnableSmartLabApi;
import de.qaware.smartlab.core.annotation.EnableSmartLabCore;
import de.qaware.smartlab.job.data.JobInfo;
import de.qaware.smartlab.job.service.repository.IJobManagementRepository;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackageClasses = de.qaware.smartlab.job.service.ComponentScanMarker.class)
@EnableSmartLabCore
@EnableSmartLabApi
@EnableJpaRepositories(basePackageClasses = IJobManagementRepository.class)
@EntityScan(basePackageClasses = JobInfo.class)
public class JobServiceConfiguration {

    @Bean
    public UrlValidator urlValidator() {
        return new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);
    }
}
