package de.qaware.smartlab.trigger.provider.cleanupmeeting;

import de.qaware.smartlab.api.service.connector.meeting.IMeetingManagementService;
import de.qaware.smartlab.api.service.connector.trigger.ITriggerService;
import de.qaware.smartlab.core.data.meeting.IMeeting;
import de.qaware.smartlab.trigger.provider.generic.AbstractTriggerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.Duration;
import java.util.Set;

import static de.qaware.smartlab.core.miscellaneous.TimeUtils.isBetween;
import static java.util.stream.Collectors.toSet;

@Component
@Slf4j
public class CleanUpMeetingTriggerProvider extends AbstractTriggerProvider {

    private static final String TRIGGER_NAME = "clean up meeting";

    private final IMeetingManagementService meetingManagementService;
    private final Duration triggerThreshold;

    public CleanUpMeetingTriggerProvider(
            ITriggerService triggerService,
            IMeetingManagementService meetingManagementService,
            // TODO: String literals
            @Qualifier("cleanUpTriggerProviderCheckInterval") Duration checkInterval,
            @Qualifier("cleanUpTriggerProviderTriggerThreshold") Duration triggerThreshold,
            // TODO: String literal
            @Qualifier("cleanUpTriggerProviderCallbackUrl") URL callbackUrl) {
        super(
                checkInterval,
                meeting -> triggerService.cleanUpCurrentMeetingByLocationId(meeting.getLocationId(), callbackUrl),
                TRIGGER_NAME);
        this.meetingManagementService = meetingManagementService;
        this.triggerThreshold = triggerThreshold;
    }

    @Override
    protected Set<IMeeting> findTriggerCandidates() {
        return meetingManagementService.findAllCurrent().stream()
                .filter(this::isAboutToEnd)
                .collect(toSet());
    }

    private boolean isAboutToEnd(IMeeting meeting) {
        return isBetween(
                this.currentCheck,
                meeting.getEnd().minus(this.triggerThreshold),
                meeting.getEnd());
    }
}
