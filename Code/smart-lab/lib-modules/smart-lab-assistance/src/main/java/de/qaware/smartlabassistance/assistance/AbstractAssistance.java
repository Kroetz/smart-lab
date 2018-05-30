package de.qaware.smartlabassistance.assistance;

import de.qaware.smartlabcommons.api.internal.service.action.IActionService;
import de.qaware.smartlabcommons.data.action.generic.IActionExecutable;
import de.qaware.smartlabcommons.data.assistance.IAssistance;
import de.qaware.smartlabcommons.data.assistance.ITriggerReaction;
import de.qaware.smartlabcommons.data.context.IContext;
import de.qaware.smartlabcommons.data.generic.IResolver;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.exception.InsufficientContextException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Data
@Slf4j
public abstract class AbstractAssistance implements IAssistance {

    protected final String assistanceId;
    protected final Set<String> assistanceAliases;
    protected final IActionService actionService;
    protected final IResolver<String, IActionExecutable> actionResolver;

    public AbstractAssistance(
            String assistanceId,
            Set<String> assistanceAliases,
            IActionService actionService,
            IResolver<String, IActionExecutable> actionResolver) {
        this.assistanceId = assistanceId;
        this.assistanceAliases = assistanceAliases;
        this.actionService = actionService;
        this.actionResolver = actionResolver;
    }

    @Override
    public ITriggerReaction reactionOnTriggerSetUpMeeting(final IContext context) {
        log.info("Reaction on set-up-meeting trigger on assistance \"{}\" of meeting with ID \"{}\" is to do nothing",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        return (assistanceService) -> log.info("Ignoring set-up-meeting trigger for assistance \"{}\" of meeting with ID \"{}\"",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
    }

    @Override
    public ITriggerReaction reactionOnTriggerCleanUpMeeting(final IContext context) {
        log.info("Reaction on clean-up-meeting trigger on assistance \"{}\" of meeting with ID \"{}\" is to do nothing",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        return (assistanceService) -> log.info("Ignoring clean-up-meeting trigger for assistance \"{}\" of meeting with ID \"{}\"",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
    }

    @Override
    public ITriggerReaction reactionOnTriggerStartMeeting(final IContext context) {
        log.info("Reaction on start-meeting trigger on assistance \"{}\" of meeting with ID \"{}\" is to do nothing",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        return (assistanceService) -> log.info("Ignoring start-meeting trigger for assistance \"{}\" of meeting with ID \"{}\"",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
    }

    @Override
    public ITriggerReaction reactionOnTriggerStopMeeting(final IContext context) {
        log.info("Reaction on stop-meeting trigger on assistance \"{}\" of meeting with ID \"{}\" is to do nothing",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        return (assistanceService) -> log.info("Ignoring stop-meeting trigger for assistance \"{}\" of meeting with ID \"{}\"",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
    }

    // TODO: Possible to force inner class for configuration?
}
