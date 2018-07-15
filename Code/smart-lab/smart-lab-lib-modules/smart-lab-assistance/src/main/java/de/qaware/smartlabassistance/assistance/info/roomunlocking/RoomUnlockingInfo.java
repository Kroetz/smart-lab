package de.qaware.smartlabassistance.assistance.info.roomunlocking;

import de.qaware.smartlabassistance.assistance.info.generic.AbstractAssistanceInfo;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RoomUnlockingInfo extends AbstractAssistanceInfo {

    public static final String ASSISTANCE_ID = "roomUnlocking";
    // TODO: Simpler with Java 9 (see https://stackoverflow.com/questions/2041778/how-to-initialize-hashset-values-by-construction)
    public static final Set<String> ASSISTANCE_ID_ALIASES = Stream.of(
            "room-unlocking",
            "room unlocking").collect(Collectors.toSet());
    public static final String ASSISTANCE_COMMAND = "unlockRoom";
    public static final Set<String> ASSISTANCE_COMMAND_ALIASES = Stream.of(
            "unlock-room",
            "unlock room").collect(Collectors.toSet());

    public RoomUnlockingInfo() {
        super(ASSISTANCE_ID, ASSISTANCE_ID_ALIASES, ASSISTANCE_COMMAND, ASSISTANCE_COMMAND_ALIASES);
    }

    @Override
    public IAssistanceConfiguration createConfiguration(Map<String, String> configProperties) {
        return new Configuration(this, configProperties);
    }

    // TODO: Which annotation can be removed?
    @Getter
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    @Slf4j
    public static class Configuration extends AbstractAssistanceInfo.AbstractConfiguration {

        private Configuration(RoomUnlockingInfo roomUnlockingInfo, Map<String, String> configProperties) {
            // TODO: process config properties
            super(roomUnlockingInfo);
        }

        @Override
        public String toConfigLangString() {
            // TODO
            Map<String, String> configProperties = new HashMap<>();
            return toConfigLangString(configProperties);
        }
    }
}
