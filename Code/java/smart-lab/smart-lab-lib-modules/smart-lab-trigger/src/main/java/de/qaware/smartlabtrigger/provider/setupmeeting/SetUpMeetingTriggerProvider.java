package de.qaware.smartlabtrigger.provider.setupmeeting;

import de.qaware.smartlab.api.service.connector.meeting.IMeetingManagementService;
import de.qaware.smartlab.api.service.connector.trigger.ITriggerService;
import de.qaware.smartlab.core.data.meeting.IMeeting;
import de.qaware.smartlabtrigger.provider.generic.AbstractTriggerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.Duration;
import java.util.Set;

@Component
@Slf4j
public class SetUpMeetingTriggerProvider extends AbstractTriggerProvider {

    private static final String TRIGGER_NAME = "set up meeting";

    private final IMeetingManagementService meetingManagementService;

    public SetUpMeetingTriggerProvider(
            IMeetingManagementService meetingManagementService,
            ITriggerService triggerService,
            // TODO: String literals
            @Qualifier("setUpTriggerProviderCheckInterval") Duration checkInterval,
            // TODO: String literal
            @Qualifier("setUpTriggerProviderCallbackUrl") URL callbackUrl) {
        super(
                checkInterval,
                meeting -> triggerService.setUpCurrentMeetingByLocationId(meeting.getLocationId(), callbackUrl),
                TRIGGER_NAME);
        this.meetingManagementService = meetingManagementService;
    }

    @Override
    protected Set<IMeeting> findTriggerCandidates() {
        return meetingManagementService.findAllCurrent();
    }
}
