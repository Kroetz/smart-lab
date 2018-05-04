package de.qaware.smartlabcommons.data.assistance;

import de.qaware.smartlabcommons.api.service.assistance.IAssistanceService;
import de.qaware.smartlabcommons.data.context.IContext;
import de.qaware.smartlabcommons.data.room.IRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class MinuteTaking extends AbstractAssistance {

    public static final String MINUTE_TAKING_ID = "minute taking";
    // TODO: Easier with Java 9
    public static final Set<String> MINUTE_TAKING_ALIASES = Stream.of(
            "minute-taking",
            "minuteTaking").collect(Collectors.toSet());

    public MinuteTaking(IAssistanceService assistanceService) {
        super(MINUTE_TAKING_ID, MINUTE_TAKING_ALIASES, assistanceService);
    }

    @Override
    public void triggerStartMeeting(IContext context) {
        log.info("Calling assistance service to start assistance \"{}\" in room with ID \"{}\"",
                MINUTE_TAKING_ID,
                context.getRoom().map(IRoom::getId).orElse("Default ID"));
        this.assistanceService.beginAssistance(this.assistanceId, context);
        log.info("Called assistance service to start assistance \"{}\" in room with ID \"{}\"",
                MINUTE_TAKING_ID,
                context.getRoom().map(IRoom::getId).orElse("Default ID"));
    }

    @Override
    public void triggerStopMeeting(IContext context) {
        log.info("Calling assistance service to stop assistance \"{}\" in room with ID \"{}\"",
                MINUTE_TAKING_ID,
                context.getRoom().map(IRoom::getId).orElse("Default ID"));
        this.assistanceService.endAssistance(this.assistanceId, context);
        log.info("Called assistance service to stop assistance \"{}\" in room with ID \"{}\"",
                MINUTE_TAKING_ID,
                context.getRoom().map(IRoom::getId).orElse("Default ID"));
    }
}
