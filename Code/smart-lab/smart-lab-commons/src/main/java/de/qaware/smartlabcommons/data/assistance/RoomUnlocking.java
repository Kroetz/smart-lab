package de.qaware.smartlabcommons.data.assistance;

import de.qaware.smartlabcommons.api.service.action.IActionService;
import de.qaware.smartlabcommons.api.service.assistance.IAssistanceService;
import de.qaware.smartlabcommons.data.context.IContext;
import de.qaware.smartlabcommons.data.room.IRoom;
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

    public RoomUnlocking(
            IAssistanceService assistanceService,
            IActionService actionService) {
        super(ASSISTANCE_ID, ASSISTANCE_ALIASES, assistanceService, actionService);
    }

    @Override
    public void triggerSetUpMeeting(IContext context) {
        log.info("Calling assistance service to start assistance \"{}\" in room with ID \"{}\"",
                ASSISTANCE_ID,
                context.getRoom().map(IRoom::getId).orElse("Default ID"));
        this.assistanceService.beginAssistance(this.assistanceId, context);
        log.info("Called assistance service to start assistance \"{}\" in room with ID \"{}\"",
                ASSISTANCE_ID,
                context.getRoom().map(IRoom::getId).orElse("Default ID"));
    }

    @Override
    public void triggerCleanUpMeeting(IContext context) {
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
        // TODO: Implementation
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
