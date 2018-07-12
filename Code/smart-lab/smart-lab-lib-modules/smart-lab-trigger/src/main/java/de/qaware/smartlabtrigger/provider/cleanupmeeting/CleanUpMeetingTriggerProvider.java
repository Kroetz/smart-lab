package de.qaware.smartlabtrigger.provider.cleanupmeeting;

import de.qaware.smartlabapi.service.connector.meeting.IMeetingManagementService;
import de.qaware.smartlabapi.service.connector.trigger.ITriggerService;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabtrigger.provider.generic.AbstractTriggerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static de.qaware.smartlabcore.miscellaneous.TimeUtils.isBetween;

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
            @Qualifier("cleanUpTriggerProviderTriggerThreshold") Duration triggerThreshold) {
        super(
                checkInterval,
                meeting -> triggerService.cleanUpCurrentMeetingByRoomId(meeting.getRoomId()),
                TRIGGER_NAME);
        this.meetingManagementService = meetingManagementService;
        this.triggerThreshold = triggerThreshold;
    }

    @Override
    protected Set<IMeeting> getTriggerCandidates() {
        try {
            return meetingManagementService.findAllCurrent().stream()
                    .filter(this::isAboutToEnd)
                    .collect(Collectors.toSet());
        }
        catch(Exception e) {
            log.error("Could not find all meetings that are currently in progress", e);
            return new HashSet<>();
        }
    }

    private boolean isAboutToEnd(IMeeting meeting) {
        return isBetween(
                this.currentCheck,
                meeting.getEnd().minus(this.triggerThreshold),
                meeting.getEnd());
    }
}
