package de.qaware.smartlabassistance.assistance.info.locationunlocking;

import com.google.common.collect.ImmutableMap;
import de.qaware.smartlabassistance.assistance.info.generic.AbstractAssistanceInfo;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.of;

@Component
@Slf4j
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LocationUnlockingInfo extends AbstractAssistanceInfo {

    public static final String ASSISTANCE_ID = "locationUnlocking";
    // TODO: Simpler with Java 9 (see https://stackoverflow.com/questions/2041778/how-to-initialize-hashset-values-by-construction)
    public static final Set<String> ASSISTANCE_ID_ALIASES = of(
            "location-unlocking",
            "location unlocking").collect(toSet());
    public static final String ASSISTANCE_COMMAND = "unlockLocation";
    public static final Set<String> ASSISTANCE_COMMAND_ALIASES = of(
            "unlock-location",
            "unlock location").collect(toSet());

    public LocationUnlockingInfo() {
        super(ASSISTANCE_ID, ASSISTANCE_ID_ALIASES, ASSISTANCE_COMMAND, ASSISTANCE_COMMAND_ALIASES);
    }

    @Override
    public IAssistanceConfiguration createConfiguration(Map<String, String> configProperties) {
        return new Configuration(this, configProperties);
    }

    @Getter
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    @Slf4j
    public static class Configuration extends AbstractAssistanceInfo.AbstractConfiguration {

        private Configuration(LocationUnlockingInfo locationUnlockingInfo, Map<String, String> configProperties) {
            // TODO: process config properties
            super(locationUnlockingInfo);
        }

        @Override
        public Map<String, String> getConfigProperties() {
            return ImmutableMap.<String, String>builder()
                    .build();
        }
    }
}
