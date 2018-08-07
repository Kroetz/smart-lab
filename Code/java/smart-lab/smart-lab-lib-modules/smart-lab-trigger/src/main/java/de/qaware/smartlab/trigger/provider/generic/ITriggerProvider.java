package de.qaware.smartlab.trigger.provider.generic;

import de.qaware.smartlab.core.data.job.IJobInfo;

public interface ITriggerProvider {

    void start();
    void stop();
    void updateTriggerJob(IJobInfo newTriggerJobStatus);
}
