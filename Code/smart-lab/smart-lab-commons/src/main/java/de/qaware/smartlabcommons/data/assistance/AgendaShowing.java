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
public class AgendaShowing extends AbstractAssistance {

    public static final String AGENDA_SHOWING_ID = "agenda showing";
    public static final Set<String> AGENDA_SHOWING_ALIASES = Stream.of(
            "agenda-showing",
            "agendaShowing").collect(Collectors.toSet());

    public AgendaShowing(IAssistanceService assistanceService) {
        super(AGENDA_SHOWING_ID, AGENDA_SHOWING_ALIASES, assistanceService);
    }

    @Override
    public void triggerSetUpMeeting(IContext context) {
        super.triggerSetUpMeeting(context);
        this.assistanceService.beginAssistance(this.assistanceId, context);
        log.info("Started agenda showing in room \"{}\"", context.getRoom().map(IRoom::getName).orElse("fail"));
    }

    @Override
    public void triggerCleanUpMeeting(IContext context) {
        super.triggerCleanUpMeeting(context);
        this.assistanceService.endAssistance(this.assistanceId, context);
        log.info("Stopped agenda showing in room \"{}\"", context.getRoom().map(IRoom::getName).orElse("fail"));
    }
}
