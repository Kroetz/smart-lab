package de.qaware.smartlab.api.service.connector.job;

import de.qaware.smartlabcore.data.job.IJobInfo;
import org.springframework.lang.Nullable;

import java.net.URL;
import java.util.Set;

public interface IJobManagementService {

    Set<IJobInfo> findAll();
    IJobInfo findOne(Long jobId);
    IJobInfo recordNewJob(@Nullable URL callbackUrl);
    void markJobAsProcessing(Long jobId);
    void markJobAsFinished(Long jobId);
    void markJobAsFailed(Long jobId, String errorMessage);
}
