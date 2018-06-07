package de.qaware.smartlabapi.service.job;

import de.qaware.smartlabcore.data.job.IJobInfo;

import java.net.URL;
import java.util.Set;

public interface IJobManagementService {

    Set<IJobInfo> findAll();
    IJobInfo findOne(Long jobId);
    IJobInfo recordNewJob();
    IJobInfo recordNewJob(URL callbackUrl);
    void markJobAsProcessing(Long jobId);
    void markJobAsFinished(Long jobId);
    void markJobAsFailed(Long jobId, String errorMessage);
}
