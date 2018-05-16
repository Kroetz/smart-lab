package de.qaware.smartlabcommons.data.assistance;

import de.qaware.smartlabcommons.api.service.action.IActionService;
import de.qaware.smartlabcommons.data.action.IActionArgs;
import de.qaware.smartlabcommons.data.action.microphone.ActivateMicrophone;
import de.qaware.smartlabcommons.data.context.IContext;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;
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
public class MinuteTaking extends AbstractAssistance {

    public static final String ASSISTANCE_ID = "minute taking";
    // TODO: Simpler with Java 9 (see https://stackoverflow.com/questions/2041778/how-to-initialize-hashset-values-by-construction)
    public static final Set<String> ASSISTANCE_ALIASES = Stream.of(
            "minute-taking",
            "minuteTaking").collect(Collectors.toSet());

    public MinuteTaking(IActionService actionService) {
        super(ASSISTANCE_ID, ASSISTANCE_ALIASES, actionService);
    }

    @Override
    public ITriggerEffect effectOfTriggerStartMeeting(final IContext context) {
        log.info("Effect of start-meeting trigger on assistance \"{}\" of meeting with ID \"{}\" is to begin the assistance",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        return (assistanceService) -> assistanceService.beginAssistance(this.assistanceId, context);
    }

    @Override
    public ITriggerEffect effectOfTriggerStopMeeting(final IContext context) {
        log.info("Effect of stop-meeting trigger on assistance \"{}\" of meeting with ID \"{}\" is to end the assistance",
                this.assistanceId,
                context.getMeeting().map(IMeeting::getId).orElseThrow(InsufficientContextException::new));
        return (assistanceService) -> assistanceService.endAssistance(this.assistanceId, context);
    }

    @Override
    public void begin(IContext context) {
        IActionArgs actionArgs = ActivateMicrophone.ActionArgs.of(
                context.getRoom().map(IRoom::getId).orElseThrow(InsufficientContextException::new),
                context.getAssistanceConfiguration().map(IAssistanceConfiguration::getDeviceId).orElseThrow(InsufficientContextException::new),
                false);     // TODO: Get rid of "executeLocally" since assistance should not decide that.
        this.actionService.executeAction(ActivateMicrophone.ACTION_ID, actionArgs);
    }

    @Override
    public void end(IContext context) {
        // TODO: Implementation
    }

    @Override
    public void update(IContext context) {
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
