package de.qaware.smartlabassistance.assistance.info;

import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.data.room.RoomId;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class AgendaShowingInfo extends AbstractAssistanceInfo {

    public static final String ASSISTANCE_ID = "agenda showing";
    // TODO: Simpler with Java 9 (see https://stackoverflow.com/questions/2041778/how-to-initialize-hashset-values-by-construction)
    public static final Set<String> ASSISTANCE_ALIASES = Stream.of(
            "agenda-showing",
            "agendaShowing").collect(Collectors.toSet());

    public AgendaShowingInfo() {
        super(ASSISTANCE_ID, ASSISTANCE_ALIASES);
    }

    // TODO: Which annotation can be removed?
    @Data
    @Slf4j
    public static class Configuration extends AbstractAssistanceInfo.AbstractConfiguration {

        public Configuration(
                MeetingId meetingId,
                RoomId roomId,
                DeviceId deviceId) {
            super(meetingId, roomId, AgendaShowingInfo.ASSISTANCE_ID, deviceId);
        }
    }
}
