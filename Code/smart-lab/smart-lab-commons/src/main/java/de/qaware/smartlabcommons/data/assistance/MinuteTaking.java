package de.qaware.smartlabcommons.data.assistance;

import de.qaware.smartlabcommons.api.service.action.IActionService;
import de.qaware.smartlabcommons.api.service.assistance.IAssistanceService;
import de.qaware.smartlabcommons.data.action.IActionArgs;
import de.qaware.smartlabcommons.data.action.microphone.ActivateMicrophone;
import de.qaware.smartlabcommons.data.context.IContext;
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

    public MinuteTaking(
            IAssistanceService assistanceService,
            IActionService actionService) {
        super(ASSISTANCE_ID, ASSISTANCE_ALIASES, assistanceService, actionService);
    }

    @Override
    public void triggerStartMeeting(IContext context) {
        log.info("Calling assistance service to start assistance \"{}\" in room with ID \"{}\"",
                ASSISTANCE_ID,
                context.getRoom().map(IRoom::getId).orElse("Default ID"));
        this.assistanceService.beginAssistance(this.assistanceId, context);
        log.info("Called assistance service to start assistance \"{}\" in room with ID \"{}\"",
                ASSISTANCE_ID,
                context.getRoom().map(IRoom::getId).orElse("Default ID"));
    }

    @Override
    public void triggerStopMeeting(IContext context) {
        log.info("Calling assistance service to stop assistance \"{}\" in room with ID \"{}\"",
                ASSISTANCE_ID,
                context.getRoom().map(IRoom::getId).orElse("Default ID"));
        this.assistanceService.endAssistance(this.assistanceId, context);
        log.info("Called assistance service to stop assistance \"{}\" in room with ID \"{}\"",
                ASSISTANCE_ID,
                context.getRoom().map(IRoom::getId).orElse("Default ID"));
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
