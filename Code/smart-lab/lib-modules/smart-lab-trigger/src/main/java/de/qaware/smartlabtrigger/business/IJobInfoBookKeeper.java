package de.qaware.smartlabtrigger.business;

import java.util.Optional;

public interface IJobInfoBookKeeper {

    JobInfo recordNewJob(JobInfo jobInfo);
    void startJobProcessing(Long jobId);
    void finishJobProcessing(Long jobId);
    void markJobAsFailed(Long jobId, String errorMessage);
    Optional<JobInfo> getJobInfo(Long jobId);
}
