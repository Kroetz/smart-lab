package de.qaware.smartlabassistance.assistance.triggerable.filedisplaying;

import de.qaware.smartlabapi.service.connector.assistance.IAssistanceService;
import de.qaware.smartlabassistance.assistance.info.filedisplaying.FileDisplayingInfo;
import de.qaware.smartlabassistance.assistance.triggerable.generic.AbstractAssistanceTriggerable;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.exception.InsufficientContextException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FileDisplayingTriggerable extends AbstractAssistanceTriggerable {

    public FileDisplayingTriggerable(FileDisplayingInfo fileDisplayingInfo) {
        super(fileDisplayingInfo);
    }

    @Override
    public void reactOnTriggerSetUpMeeting(IAssistanceService assistanceService, IAssistanceContext context) {
        log.info("Reaction on set-up-meeting trigger on assistance \"{}\" of meeting with ID \"{}\" is to begin the assistance",
                this.assistanceInfo.getAssistanceId(),
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        assistanceService.beginAssistance(this.assistanceInfo.getAssistanceId(), context);
    }

    @Override
    public void reactOnTriggerCleanUpMeeting(IAssistanceService assistanceService, IAssistanceContext context) {
        log.info("Reaction on clean-up-meeting trigger on assistance \"{}\" of meeting with ID \"{}\" is to end the assistance",
                this.assistanceInfo.getAssistanceId(),
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        assistanceService.endAssistance(this.assistanceInfo.getAssistanceId(), context);
    }
}
