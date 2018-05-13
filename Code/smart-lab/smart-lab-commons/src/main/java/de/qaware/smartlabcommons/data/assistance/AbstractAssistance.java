package de.qaware.smartlabcommons.data.assistance;

import de.qaware.smartlabcommons.api.service.action.IActionService;
import de.qaware.smartlabcommons.api.service.assistance.IAssistanceService;
import de.qaware.smartlabcommons.data.context.IContext;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Data
@Slf4j
public abstract class AbstractAssistance implements IAssistance {

    protected final String assistanceId;
    protected final Set<String> assistanceAliases;
    protected final IAssistanceService assistanceService;
    protected final IActionService actionService;

    public AbstractAssistance(
            String assistanceId,
            Set<String> assistanceAliases,
            IAssistanceService assistanceService,
            IActionService actionService) {
        this.assistanceId = assistanceId;
        this.assistanceAliases = assistanceAliases;
        this.assistanceService = assistanceService;
        this.actionService = actionService;
    }

    @Override
    public void triggerSetUpMeeting(IContext context) {
        log.info("Ignoring set-up-meeting trigger for assistance \"{}\" of meeting with ID \"{}\"",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElse("Default ID"));
    }

    @Override
    public void triggerCleanUpMeeting(IContext context) {
        log.info("Ignoring clean-up-meeting trigger for assistance \"{}\" of meeting with ID \"{}\"",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElse("Default ID"));
    }

    @Override
    public void triggerStartMeeting(IContext context) {
        log.info("Ignoring start-meeting trigger for assistance \"{}\" of meeting with ID \"{}\"",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElse("Default ID"));
    }

    @Override
    public void triggerStopMeeting(IContext context) {
        log.info("Ignoring stop-meeting trigger for assistance \"{}\" of meeting with ID \"{}\"",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElse("Default ID"));
    }

    // TODO: Possible to force inner class for configuration?
}
