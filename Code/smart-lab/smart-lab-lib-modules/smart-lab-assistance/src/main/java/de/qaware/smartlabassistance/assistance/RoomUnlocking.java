package de.qaware.smartlabassistance.assistance;

import de.qaware.smartlabapi.service.action.IActionService;
import de.qaware.smartlabapi.service.assistance.IAssistanceService;
import de.qaware.smartlabaction.action.generic.IActionExecutable;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.context.IContext;
import de.qaware.smartlabcore.data.generic.IResolver;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.exception.InsufficientContextException;
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

    public RoomUnlocking(IResolver<String, IActionExecutable> actionResolver) {
        super(ASSISTANCE_ID, ASSISTANCE_ALIASES, actionResolver);
    }

    @Override
    public void reactOnTriggerSetUpMeeting(IAssistanceService assistanceService, IContext context) {
        log.info("Reaction on set-up-meeting trigger on assistance \"{}\" of meeting with ID \"{}\" is to begin the assistance",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        assistanceService.beginAssistance(this.assistanceId, context);
    }

    @Override
    public void reactOnTriggerCleanUpMeeting(IAssistanceService assistanceService, IContext context) {
        log.info("Reaction on clean-up-meeting trigger on assistance \"{}\" of meeting with ID \"{}\" is to end the assistance",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        assistanceService.endAssistance(this.assistanceId, context);
    }

    @Override
    public void begin(IActionService actionService, IContext context) {
        // TODO: Implementation
    }

    @Override
    public void end(IActionService actionService, IContext context) {
        // TODO: Implementation
    }

    @Override
    public void update(IActionService actionService, IContext context) {
        // TODO: Implementation
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
