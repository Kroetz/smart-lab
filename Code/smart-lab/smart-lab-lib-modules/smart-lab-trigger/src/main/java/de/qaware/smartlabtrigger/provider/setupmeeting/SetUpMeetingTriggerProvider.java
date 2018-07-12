package de.qaware.smartlabtrigger.provider.setupmeeting;

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

@Component
@Slf4j
public class SetUpMeetingTriggerProvider extends AbstractTriggerProvider {

    private static final String TRIGGER_NAME = "set up meeting";

    private final IMeetingManagementService meetingManagementService;

    public SetUpMeetingTriggerProvider(
            IMeetingManagementService meetingManagementService,
            ITriggerService triggerService,
            // TODO: String literals
            @Qualifier("setUpTriggerProviderCheckInterval") Duration checkInterval) {
        super(
                checkInterval,
                meeting -> triggerService.setUpCurrentMeetingByRoomId(meeting.getRoomId()),
                TRIGGER_NAME);
        this.meetingManagementService = meetingManagementService;
    }

    @Override
    protected Set<IMeeting> getTriggerCandidates() {
        try {
            return meetingManagementService.findAllCurrent();
        }
        catch(Exception e) {
            log.error("Could not find all meetings that are currently in progress", e);
            return new HashSet<>();
        }
    }
}
