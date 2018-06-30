package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabapi.service.job.IJobManagementService;
import de.qaware.smartlabcore.data.job.IJobInfo;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabjob.controller.JobManagementController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Set;

import static java.util.Objects.nonNull;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class JobManagementMonolithicService implements IJobManagementService {

    private final JobManagementController jobManagementController;

    public JobManagementMonolithicService(JobManagementController jobManagementController) {
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
}
