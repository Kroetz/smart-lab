package de.qaware.smartlab.monolith.service.connector.job;

import de.qaware.smartlab.api.service.connector.job.IJobManagementService;
import de.qaware.smartlab.core.configuration.ModularityConfiguration;
import de.qaware.smartlab.core.data.job.IJobInfo;
import de.qaware.smartlab.core.service.url.IServiceBaseUrlGetter;
import de.qaware.smartlab.job.service.controller.JobManagementController;
import de.qaware.smartlab.monolith.service.url.AbstractMonolithicBaseUrlGetter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Set;

import static java.util.Objects.nonNull;

@Component
@ConditionalOnProperty(
        prefix = ModularityConfiguration.Properties.PREFIX,
        name = ModularityConfiguration.Properties.MODULARITY,
        havingValue = ModularityConfiguration.Properties.MONOLITH)
public class JobManagementMonolithicServiceConnector implements IJobManagementService {

    private final JobManagementController jobManagementController;

    public JobManagementMonolithicServiceConnector(JobManagementController jobManagementController) {
        this.jobManagementController = jobManagementController;
    }

    @Override
    public Set<IJobInfo> findAll() {
        return this.jobManagementController.findAll();
    }

    @Override
    public IJobInfo findOne(Long jobId) {
        return this.jobManagementController.findOne(jobId).getBody();
    }

    @Override
    public IJobInfo recordNewJob(@Nullable URL callbackUrl) {
        return this.jobManagementController.recordNewJob(
                nonNull(callbackUrl) ? callbackUrl.toString() : null).getBody();
    }

    @Override
    public void markJobAsProcessing(Long jobId) {
        this.jobManagementController.markJobAsProcessing(jobId);
    }

    @Override
    public void markJobAsFinished(Long jobId) {
        this.jobManagementController.markJobAsFinished(jobId);
    }

    @Override
    public void markJobAsFailed(Long jobId, String errorMessage) {
        this.jobManagementController.markJobAsFailed(jobId, errorMessage);
    }

    @Component
    @Qualifier(IServiceBaseUrlGetter.QUALIFIER_JOB_MANAGEMENT_SERVICE_BASE_URL_GETTER)
    @ConditionalOnProperty(
            prefix = ModularityConfiguration.Properties.PREFIX,
            name = ModularityConfiguration.Properties.MODULARITY,
            havingValue = ModularityConfiguration.Properties.MONOLITH)
    public static class BaseUrlGetter extends AbstractMonolithicBaseUrlGetter {

        public BaseUrlGetter(JobManagementController.BaseUrlController baseUrlController) {
            super(baseUrlController);
        }
    }
}
