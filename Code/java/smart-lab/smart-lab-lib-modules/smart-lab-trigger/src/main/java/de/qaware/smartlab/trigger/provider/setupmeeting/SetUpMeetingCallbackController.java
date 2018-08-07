package de.qaware.smartlab.trigger.provider.setupmeeting;

import de.qaware.smartlab.core.data.job.JobInfo;
import de.qaware.smartlab.trigger.provider.generic.ITriggerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SetUpMeetingCallbackController {

    public static final String MAPPING_CALLBACK = "/smart-lab/api/trigger-provider/set-up-meeting/callback";

    private final ITriggerProvider setUpMeetingTriggerProvider;

    public SetUpMeetingCallbackController(ITriggerProvider setUpMeetingTriggerProvider) {
        this.setUpMeetingTriggerProvider = setUpMeetingTriggerProvider;
    }

    @PostMapping(value = MAPPING_CALLBACK, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void callback(@RequestBody JobInfo jobInfo) {
        log.info("Received callback with job info {}", jobInfo);
        this.setUpMeetingTriggerProvider.updateTriggerJob(jobInfo);
    }
}
