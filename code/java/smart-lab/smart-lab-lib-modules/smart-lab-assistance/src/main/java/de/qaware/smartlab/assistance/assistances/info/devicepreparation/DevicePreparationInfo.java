package de.qaware.smartlab.assistance.assistances.info.devicepreparation;

import com.google.common.collect.ImmutableMap;
import de.qaware.smartlab.assistance.assistances.info.generic.AbstractAssistanceInfo;
import de.qaware.smartlab.core.assistance.IAssistanceConfiguration;
import de.qaware.smartlab.core.data.actuator.ActuatorId;
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
public class DevicePreparationInfo extends AbstractAssistanceInfo {

    public static final String ASSISTANCE_ID = "devicePreparation";
    // TODO: Simpler with Java 9 (see https://stackoverflow.com/questions/2041778/how-to-initialize-hashset-values-by-construction)
    public static final Set<String> ASSISTANCE_ID_ALIASES = of(
            "device-preparation",
            "device preparation").collect(toSet());
    public static final String ASSISTANCE_COMMAND = "prepareDevice";
    public static final Set<String> ASSISTANCE_COMMAND_ALIASES = of(
            "prepare-device",
            "prepare device").collect(toSet());

    public DevicePreparationInfo() {
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

        public static final String CONFIG_PROPERTY_KEY_DEVICE_ID = "deviceId";

        private ActuatorId deviceId;

        private Configuration(DevicePreparationInfo devicePreparationInfo, Map<String, String> configProperties) {
            super(devicePreparationInfo);
            for(String key : configProperties.keySet()) {
                switch (key) {
                    case CONFIG_PROPERTY_KEY_DEVICE_ID:
                        this.deviceId = ActuatorId.of(configProperties.get(key));
                        break;
                    default:
                        log.warn("Ignoring config property {} since it is not relevant for the assistance {}", key, getAssistanceId());
                        break;
                }
            }
        }

        @Override
        public Map<String, String> getConfigProperties() {
            return ImmutableMap.<String, String>builder()
                    .put(CONFIG_PROPERTY_KEY_DEVICE_ID, this.deviceId.getIdValue())
                    .build();
        }
    }
}
