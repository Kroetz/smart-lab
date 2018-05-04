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
public class RoomUnlocking extends AbstractAssistance {

    public static final String ROOM_UNLOCKING_ID = "room unlocking";
    // TODO: Easier with Java 9
    public static final Set<String> ROOM_UNLOCKING_ALIASES = Stream.of(
            "room-unlocking",
            "roomUnlocking").collect(Collectors.toSet());

    public RoomUnlocking(IAssistanceService assistanceService) {
        super(ROOM_UNLOCKING_ID, ROOM_UNLOCKING_ALIASES, assistanceService);
    }

    @Override
    public void triggerSetUpMeeting(IContext context) {
        log.info("Calling assistance service to start assistance \"{}\" in room with ID \"{}\"",
                ROOM_UNLOCKING_ID,
                context.getRoom().map(IRoom::getId).orElse("Default ID"));
        this.assistanceService.beginAssistance(this.assistanceId, context);
        log.info("Called assistance service to start assistance \"{}\" in room with ID \"{}\"",
                ROOM_UNLOCKING_ID,
                context.getRoom().map(IRoom::getId).orElse("Default ID"));
    }

    @Override
    public void triggerCleanUpMeeting(IContext context) {
        log.info("Calling assistance service to stop assistance \"{}\" in room with ID \"{}\"",
                ROOM_UNLOCKING_ID,
                context.getRoom().map(IRoom::getId).orElse("Default ID"));
        this.assistanceService.endAssistance(this.assistanceId, context);
        log.info("Called assistance service to stop assistance \"{}\" in room with ID \"{}\"",
                ROOM_UNLOCKING_ID,
                context.getRoom().map(IRoom::getId).orElse("Default ID"));
    }
}
