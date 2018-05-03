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
    public static final Set<String> MINUTE_TAKING_ALIASES = Stream.of(
            "minute-taking",
            "minuteTaking").collect(Collectors.toSet());

    public MinuteTaking(IAssistanceService assistanceService) {
        super(MINUTE_TAKING_ID, MINUTE_TAKING_ALIASES, assistanceService);
    }

    @Override
    public void triggerStartMeeting(IContext context) {
        super.triggerStartMeeting(context);
        this.assistanceService.beginAssistance(this.assistanceId, context);
        log.info("Started minute taking in room \"{}\"", context.getRoom().map(IRoom::getName).orElse("fail"));
    }

    @Override
    public void triggerStopMeeting(IContext context) {
        super.triggerStopMeeting(context);
        this.assistanceService.endAssistance(this.assistanceId, context);
        log.info("Stopped minute taking in room \"{}\"", context.getRoom().map(IRoom::getName).orElse("fail"));
    }
}
