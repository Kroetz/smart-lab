package de.qaware.smartlabassistance.assistance;

import de.qaware.smartlabapi.service.assistance.IAssistanceService;
import de.qaware.smartlabaction.action.generic.IActionExecutable;
import de.qaware.smartlabcommons.data.context.IContext;
import de.qaware.smartlabcommons.data.generic.IResolver;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.exception.InsufficientContextException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Data
@Slf4j
public abstract class AbstractAssistance implements IAssistanceTriggerable, IAssistanceExecutable {

    protected final String assistanceId;
    protected final Set<String> assistanceAliases;
    protected final IResolver<String, IActionExecutable> actionResolver;

    public AbstractAssistance(
            String assistanceId,
            Set<String> assistanceAliases,
            IResolver<String, IActionExecutable> actionResolver) {
        this.assistanceId = assistanceId;
        this.assistanceAliases = assistanceAliases;
        this.actionResolver = actionResolver;
    }

    @Override
    public void reactOnTriggerSetUpMeeting(final IAssistanceService assistanceService, final IContext context) {
        log.info("Ignoring set-up-meeting trigger for assistance \"{}\" of meeting with ID \"{}\"",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
    }

    @Override
    public void reactOnTriggerCleanUpMeeting(final IAssistanceService assistanceService, final IContext context) {
        log.info("Ignoring clean-up-meeting trigger for assistance \"{}\" of meeting with ID \"{}\"",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
    }

    @Override
    public void reactOnTriggerStartMeeting(final IAssistanceService assistanceService, final IContext context) {
        log.info("Ignoring start-meeting trigger for assistance \"{}\" of meeting with ID \"{}\"",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
    }

    @Override
    public void reactOnTriggerStopMeeting(final IAssistanceService assistanceService, final IContext context) {
        log.info("Ignoring stop-meeting trigger for assistance \"{}\" of meeting with ID \"{}\"",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
    }

    // TODO: Possible to force inner class for configuration?
}
