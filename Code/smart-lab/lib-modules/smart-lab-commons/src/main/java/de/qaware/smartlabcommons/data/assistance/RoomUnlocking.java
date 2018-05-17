package de.qaware.smartlabcommons.data.assistance;

import de.qaware.smartlabcommons.data.action.IAssistanceStage;
import de.qaware.smartlabcommons.data.context.IContext;
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
public class RoomUnlocking extends AbstractAssistance {

    public static final String ASSISTANCE_ID = "room unlocking";
    // TODO: Simpler with Java 9 (see https://stackoverflow.com/questions/2041778/how-to-initialize-hashset-values-by-construction)
    public static final Set<String> ASSISTANCE_ALIASES = Stream.of(
            "room-unlocking",
            "roomUnlocking").collect(Collectors.toSet());

    public RoomUnlocking() {
        super(ASSISTANCE_ID, ASSISTANCE_ALIASES);
    }

    @Override
    public ITriggerEffect effectOfTriggerSetUpMeeting(IContext context) {
        log.info("Effect of set-up-meeting trigger on assistance \"{}\" of meeting with ID \"{}\" is to begin the assistance",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        return (assistanceService) -> assistanceService.beginAssistance(this.assistanceId, context);
    }

    @Override
    public ITriggerEffect effectOfTriggerCleanUpMeeting(IContext context) {
        log.info("Effect of clean-up-meeting trigger on assistance \"{}\" of meeting with ID \"{}\" is to end the assistance",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        return (assistanceService) -> assistanceService.endAssistance(this.assistanceId, context);
    }

    @Override
    public IAssistanceStage actionsOfBeginStage(IContext context) {
        // TODO: Implementation
        return (actionService) -> {};
    }

    @Override
    public IAssistanceStage actionsOfEndStage(IContext context) {
        // TODO: Implementation
        return (actionService) -> {};
    }

    @Override
    public IAssistanceStage actionsOfUpdateStage(IContext context) {
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
