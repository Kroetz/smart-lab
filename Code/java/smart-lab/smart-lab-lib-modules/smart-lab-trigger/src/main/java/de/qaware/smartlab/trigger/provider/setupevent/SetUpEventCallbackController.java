package de.qaware.smartlab.trigger.provider.setupevent;

import de.qaware.smartlab.job.data.JobInfo;
import de.qaware.smartlab.trigger.provider.generic.ITriggerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SetUpEventCallbackController {

    public static final String MAPPING_CALLBACK = "/smart-lab/api/trigger-provider/set-up-event/callback";

    private final ITriggerProvider setUpEventTriggerProvider;

    public SetUpEventCallbackController(ITriggerProvider setUpEventTriggerProvider) {
        this.setUpEventTriggerProvider = setUpEventTriggerProvider;
    }

    @PostMapping(value = MAPPING_CALLBACK, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void callback(@RequestBody JobInfo jobInfo) {
        log.info("Received callback with job info {}", jobInfo);
        this.setUpEventTriggerProvider.updateTriggerJob(jobInfo);
    }
}
