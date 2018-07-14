package de.qaware.smartlabtrigger.provider.generic;

import de.qaware.smartlabcore.data.job.IJobInfo;

public interface ITriggerProvider {

    void start();
    void stop();
    void updateTriggerJob(IJobInfo newTriggerJobStatus);
}
