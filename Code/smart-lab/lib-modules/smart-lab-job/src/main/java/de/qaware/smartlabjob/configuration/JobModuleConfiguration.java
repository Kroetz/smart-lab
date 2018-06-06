package de.qaware.smartlabjob.configuration;

import de.qaware.smartlabcore.data.job.JobInfo;
import de.qaware.smartlabjob.repository.IJobManagementRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = IJobManagementRepository.class)
@EntityScan(basePackageClasses = JobInfo.class)
public class JobModuleConfiguration { }
