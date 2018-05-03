package de.qaware.smartlabcommons.data.assistance;

import de.qaware.smartlabcommons.api.service.assistance.IAssistanceService;
import de.qaware.smartlabcommons.data.context.IContext;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.data.workgroup.IWorkgroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class RoomUnlocking extends AbstractAssistance {

    public static final String ROOM_UNLOCKING_ID = "room unlocking";
    public static final Set<String> ROOM_UNLOCKING_ALIASES = Stream.of(
            "room-unlocking",
            "roomUnlocking").collect(Collectors.toSet());

    public RoomUnlocking(IAssistanceService assistanceService) {
        super(ROOM_UNLOCKING_ID, ROOM_UNLOCKING_ALIASES, assistanceService);
    }

    @Override
    public void triggerSetUpMeeting(IContext context) {
        super.triggerSetUpMeeting(context);
        this.assistanceService.beginAssistance(this.assistanceId, context);
        log.info("Unlocked room \"{}\" for members of workgroup \"{}\"",
                context.getRoom().map(IRoom::getName).orElse("Default room name"),
                context.getWorkgroup().map(IWorkgroup::getName).orElse("Default workgroup name"));
    }

    @Override
    public void triggerCleanUpMeeting(IContext context) {
        super.triggerCleanUpMeeting(context);
        this.assistanceService.endAssistance(this.assistanceId, context);
        log.info("Locked room \"{}\" for members of workgroup \"{}\"",
                context.getRoom().map(IRoom::getName).orElse("Default room name"),
                context.getWorkgroup().map(IWorkgroup::getName).orElse("Default workgroup name"));
    }
}
