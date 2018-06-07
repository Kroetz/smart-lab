package de.qaware.smartlabmonolith.api.service;

import de.qaware.smartlabapi.service.job.IJobManagementService;
import de.qaware.smartlabcore.data.job.IJobInfo;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabjob.controller.JobManagementController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Set;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class JobManagementServiceMonolith implements IJobManagementService {

    private final JobManagementController jobManagementController;

    public JobManagementServiceMonolith(JobManagementController jobManagementController) {
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
    public IJobInfo recordNewJob() {
        return this.jobManagementController.recordNewJob(null).getBody();
    }

    @Override
    public IJobInfo recordNewJob(URL callbackUrl) {
        return this.jobManagementController.recordNewJob(callbackUrl.toString()).getBody();
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
