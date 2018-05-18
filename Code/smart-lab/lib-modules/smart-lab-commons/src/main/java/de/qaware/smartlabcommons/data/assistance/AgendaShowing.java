package de.qaware.smartlabcommons.data.assistance;

import de.qaware.smartlabcommons.data.action.IAction;
import de.qaware.smartlabcommons.data.context.IContext;
import de.qaware.smartlabcommons.data.generic.IResolver;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.exception.InsufficientContextException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class AgendaShowing extends AbstractAssistance {

    public static final String ASSISTANCE_ID = "agenda showing";
    // TODO: Simpler with Java 9 (see https://stackoverflow.com/questions/2041778/how-to-initialize-hashset-values-by-construction)
    public static final Set<String> ASSISTANCE_ALIASES = Stream.of(
            "agenda-showing",
            "agendaShowing").collect(Collectors.toSet());

    public AgendaShowing(IResolver<String, IAction> actionResolver) {
        super(ASSISTANCE_ID, ASSISTANCE_ALIASES, actionResolver);
    }

    @Override
    public ITriggerReaction reactionOnTriggerSetUpMeeting(IContext context) {
        log.info("Reaction on set-up-meeting trigger on assistance \"{}\" of meeting with ID \"{}\" is to begin the assistance",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        return (assistanceService) -> assistanceService.beginAssistance(this.assistanceId, context);
    }

    @Override
    public ITriggerReaction reactionOnTriggerCleanUpMeeting(IContext context) {
        log.info("Reaction on clean-up-meeting trigger on assistance \"{}\" of meeting with ID \"{}\" is to end the assistance",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        return (assistanceService) -> assistanceService.endAssistance(this.assistanceId, context);
    }

    @Override
    public IAssistanceStageExecution executionOfBeginStage(IContext context) {
        // TODO: Implementation
        return (actionService) -> {};
    }

    @Override
    public IAssistanceStageExecution executionOfEndStage(IContext context) {
        // TODO: Implementation
        return (actionService) -> {};
    }

    @Override
    public IAssistanceStageExecution executionOfUpdateStage(IContext context) {
        // TODO: Implementation
        return (actionService) -> {};
    }

    // TODO: Which annotation can be removed?
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Slf4j
    public static class Configuration implements IAssistanceConfiguration {

        private String deviceId;
    }
}
