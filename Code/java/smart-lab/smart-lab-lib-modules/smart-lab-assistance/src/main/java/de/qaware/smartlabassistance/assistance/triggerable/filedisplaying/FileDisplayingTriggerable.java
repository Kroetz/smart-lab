package de.qaware.smartlabassistance.assistance.triggerable.filedisplaying;

import de.qaware.smartlabapi.service.connector.assistance.IAssistanceService;
import de.qaware.smartlabassistance.assistance.info.filedisplaying.FileDisplayingInfo;
import de.qaware.smartlabassistance.assistance.triggerable.generic.AbstractAssistanceTriggerable;
import de.qaware.smartlabassistance.assistance.triggerable.miscellaneous.triggerreaction.ITriggerReaction;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FileDisplayingTriggerable extends AbstractAssistanceTriggerable {

    private final ITriggerReaction beginAssistanceReaction;
    private final ITriggerReaction endAssistanceReaction;

    public FileDisplayingTriggerable(
            FileDisplayingInfo fileDisplayingInfo,
            ITriggerReaction beginAssistanceReaction,
            ITriggerReaction endAssistanceReaction) {
        super(fileDisplayingInfo);
        this.beginAssistanceReaction = beginAssistanceReaction;
        this.endAssistanceReaction = endAssistanceReaction;
    }

    @Override
    public void reactOnTriggerSetUpMeeting(IAssistanceService assistanceService, IAssistanceContext context) {
        this.beginAssistanceReaction.react(assistanceService, this.assistanceInfo.getAssistanceId(), context);
    }

    @Override
    public void reactOnTriggerCleanUpMeeting(IAssistanceService assistanceService, IAssistanceContext context) {
        this.endAssistanceReaction.react(assistanceService, this.assistanceInfo.getAssistanceId(), context);
    }
}
