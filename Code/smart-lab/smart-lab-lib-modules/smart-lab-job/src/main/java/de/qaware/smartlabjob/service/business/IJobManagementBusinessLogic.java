package de.qaware.smartlabjob.service.business;

import de.qaware.smartlabcore.data.job.IJobInfo;

import java.net.URL;
import java.util.Optional;
import java.util.Set;

public interface IJobManagementBusinessLogic {

    Set<IJobInfo> findAll();
    Optional<IJobInfo> findOne(Long jobId);
    IJobInfo recordNewJob();
    IJobInfo recordNewJob(URL callbackUrl);
    void markJobAsProcessing(Long jobId);
    void markJobAsFinished(Long jobId);
    void markJobAsFailed(Long jobId, String errorMessage);
}
