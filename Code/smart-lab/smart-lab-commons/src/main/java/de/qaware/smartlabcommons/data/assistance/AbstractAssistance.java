package de.qaware.smartlabcommons.data.assistance;

import de.qaware.smartlabcommons.api.service.action.IActionService;
import de.qaware.smartlabcommons.data.context.IContext;
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

    public AbstractAssistance(
            String assistanceId,
            Set<String> assistanceAliases,
            IActionService actionService) {
        this.assistanceId = assistanceId;
        this.assistanceAliases = assistanceAliases;
        this.actionService = actionService;
    }

    @Override
    public ITriggerEffect effectOfTriggerSetUpMeeting(final IContext context) {
        log.info("Effect of set-up-meeting trigger on assistance \"{}\" of meeting with ID \"{}\" is to do nothing",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        return (assistanceService) -> {
            log.info("Ignoring set-up-meeting trigger for assistance \"{}\" of meeting with ID \"{}\"",
                    this.assistanceId,
                    context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        };
    }

    @Override
    public ITriggerEffect effectOfTriggerCleanUpMeeting(final IContext context) {
        log.info("Effect of clean-up-meeting trigger on assistance \"{}\" of meeting with ID \"{}\" is to do nothing",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        return (assistanceService) -> {
            log.info("Ignoring clean-up-meeting trigger for assistance \"{}\" of meeting with ID \"{}\"",
                    this.assistanceId,
                    context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        };
    }

    @Override
    public ITriggerEffect effectOfTriggerStartMeeting(final IContext context) {
        log.info("Effect of start-meeting trigger on assistance \"{}\" of meeting with ID \"{}\" is to do nothing",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        return (assistanceService) -> {
            log.info("Ignoring start-meeting trigger for assistance \"{}\" of meeting with ID \"{}\"",
                    this.assistanceId,
                    context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        };
    }

    @Override
    public ITriggerEffect effectOfTriggerStopMeeting(final IContext context) {
        log.info("Effect of stop-meeting trigger on assistance \"{}\" of meeting with ID \"{}\" is to do nothing",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        return (assistanceService) -> {
            log.info("Ignoring stop-meeting trigger for assistance \"{}\" of meeting with ID \"{}\"",
                    this.assistanceId,
                    context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        };
    }

    // TODO: Possible to force inner class for configuration?
}
